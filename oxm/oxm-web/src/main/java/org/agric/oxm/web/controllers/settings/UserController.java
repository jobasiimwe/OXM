package org.agric.oxm.web.controllers.settings;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.enums.Condition;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.enums.HouseHoldCategory;
import org.agric.oxm.model.enums.UserStatus;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.server.service.RoleService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.server.sms.Sender;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.agric.oxm.web.controllers.ApplicationController;
import org.agric.oxm.web.forms.GenericCommand;
import org.agric.oxm.web.forms.GenericCommandValue;
import org.agric.oxm.web.utils.UserExcelExportUtil;
import org.agric.oxm.web.utils.UserPdfExportUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.pdf.PdfWriter;

//import com.itextpdf.text.List;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private Adminservice adminService;

	@Autowired
	private ProducerOrgService producerOrgService;

	@Autowired
	private ApplicationController applicationController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Search parameter field names
	 */
	public static final String NAME_OR_USERNAME = "name-or-username";
	public static final String PRODUCER_ORG = "porg";
	public static final String ROLE = "role";
	public static final String GENDER = "gender";

	public static final String DISTRICT = "district";
	public static final String COUNTY = "county";
	public static final String SUBCOUNTY = "subCounty";
	public static final String PARISH = "parish";
	public static final String VILLAGE = "village";

	public static final String HOUSE_HOLD_CATEGORY = "householdcategory";
	public static final String LAND_SIZE1 = "landsize1";
	public static final String LAND_SIZE2 = "landsize2";

	public static final String LAND_SIZE_CONDITION1 = "landsizecondition1";
	public static final String LAND_SIZE_CONDITION2 = "landsizecondition2";

	public static final String COMMITTEE = "committee";
	public static final String COMMITTEE_MEMBER = "committeemember";

	public static final String SMS_TEXT = "smstext";

	private static final String COMMAND_NAME = "usersearch";

	public void prepareSearchCommand(ModelMap modelMap,
			UserSearchParameters params) {
		GenericCommand searchCommand = new GenericCommand();

		if (StringUtils.isNotEmpty(params.getNameOrUserName()))
			searchCommand.getPropertiesMap().put(NAME_OR_USERNAME,
					new GenericCommandValue(params.getNameOrUserName()));

		if (params.getProducerOrg() != null) {
			searchCommand.getPropertiesMap().put(PRODUCER_ORG,
					new GenericCommandValue(params.getProducerOrg().getId()));
		}

		if (params.getRole() != null) {
			searchCommand.getPropertiesMap().put(ROLE,
					new GenericCommandValue(params.getRole().getId()));
		}

		if (null != params.getGender())
			searchCommand.getPropertiesMap().put(GENDER,
					new GenericCommandValue(params.getGender().toString()));

		if (null != params.getHouseHoldCategory())
			searchCommand.getPropertiesMap().put(
					HOUSE_HOLD_CATEGORY,
					new GenericCommandValue(params.getHouseHoldCategory()
							.toString()));

		if (null != params.getLandSize1())
			searchCommand.getPropertiesMap().put(LAND_SIZE1,
					new GenericCommandValue(params.getLandSize1().toString()));

		if (null != params.getLandSize1())
			searchCommand.getPropertiesMap().put(LAND_SIZE2,
					new GenericCommandValue(params.getLandSize2().toString()));

		if (null != params.getLandSizeCondition1())
			searchCommand.getPropertiesMap().put(
					LAND_SIZE_CONDITION1,
					new GenericCommandValue(params.getLandSizeCondition1()
							.toString()));
		if (null != params.getLandSizeCondition2())
			searchCommand.getPropertiesMap().put(
					LAND_SIZE_CONDITION2,
					new GenericCommandValue(params.getLandSizeCondition2()
							.toString()));

		if (StringUtils.isNotBlank(params.getCommitteeMemberId())) {
			searchCommand.getPropertiesMap().put(COMMITTEE_MEMBER,
					new GenericCommandValue(params.getCommitteeMemberId()));
		}

		if (StringUtils.isNotBlank(params.getCommitteeId())) {
			searchCommand.getPropertiesMap().put(COMMITTEE,
					new GenericCommandValue(params.getCommitteeId()));
		}

		if (null != params.getDistrict()) {
			searchCommand.getPropertiesMap().put(DISTRICT,
					new GenericCommandValue(params.getDistrict().getId()));
			modelMap.put("pDistrict", params.getDistrict().getId());
		}

		if (null != params.getCounty()) {
			searchCommand.getPropertiesMap().put(COUNTY,
					new GenericCommandValue(params.getCounty().getId()));
			modelMap.put("pCounty", params.getCounty().getId());
		}

		if (null != params.getSubCounty()) {
			searchCommand.getPropertiesMap().put(SUBCOUNTY,
					new GenericCommandValue(params.getSubCounty().getId()));
			modelMap.put("pSubCounty", params.getSubCounty().getId());
		}

		if (null != params.getParish()) {
			searchCommand.getPropertiesMap().put(PARISH,
					new GenericCommandValue(params.getParish().getId()));
			modelMap.put("pParish", params.getParish().getId());
		}

		if (null != params.getVillage()) {
			searchCommand.getPropertiesMap().put(VILLAGE,
					new GenericCommandValue(params.getVillage().getId()));
			modelMap.put("pVillage", params.getVillage().getId());
		}

		if (StringUtils.isNotBlank(params.getSmsText())) {
			searchCommand.getPropertiesMap().put(SMS_TEXT,
					new GenericCommandValue(params.getSmsText()));
		}

		modelMap.put(COMMAND_NAME, searchCommand);

		modelMap.put("pOrgs", producerOrgService.getProducerOrganisations());
		modelMap.put("roles", roleService.getRoles());
		modelMap.put("genders", Gender.values());
		modelMap.put("householdcatrgories", HouseHoldCategory.values());
		modelMap.put("conditions", Condition.values());
	}

	@Secured({ PermissionConstants.VIEW_USER })
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView viewUsersHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		prepareSearchModel(new UserSearchParameters(), false, false, 1,
				modelMap, false);

		modelMap.put(WebConstants.CONTENT_HEADER, "List of Users");
		return new ModelAndView("viewUser", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_USER })
	@RequestMapping(value = "search/load", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		UserSearchParameters params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, true, true, pageNo, modelMap, false);

		// testSms();

		return new ModelAndView("viewUser", modelMap);
	}

	public UserSearchParameters extractParams(GenericCommand searchCommand) {
		UserSearchParameters params = new UserSearchParameters();

		if (StringUtils.isNotBlank(searchCommand.getValue(NAME_OR_USERNAME))) {
			params.setNameOrUserName(searchCommand.getValue(NAME_OR_USERNAME));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(PRODUCER_ORG))) {
			params.setProducerOrg(producerOrgService
					.getProducerOrganisationById(searchCommand
							.getValue(PRODUCER_ORG)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(COMMITTEE))) {
			params.setCommitteeId(searchCommand.getValue(COMMITTEE));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(COMMITTEE_MEMBER))) {
			params.setCommitteeMemberId(searchCommand
					.getValue(COMMITTEE_MEMBER));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(ROLE))) {
			params.setRole(roleService.getRoleById(searchCommand.getValue(ROLE)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(GENDER))) {
			params.setGender(Gender.valueOf(searchCommand.getValue(GENDER)));
		}
		if (StringUtils.isNotBlank(searchCommand.getValue(HOUSE_HOLD_CATEGORY))) {
			params.setHouseHoldCategory(HouseHoldCategory.valueOf(searchCommand
					.getValue(HOUSE_HOLD_CATEGORY)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(LAND_SIZE1))) {
			params.setLandSize1(Double.parseDouble(searchCommand
					.getValue(LAND_SIZE1)));
		}
		if (StringUtils.isNotBlank(searchCommand.getValue(LAND_SIZE2))) {
			params.setLandSize2(Double.parseDouble(searchCommand
					.getValue(LAND_SIZE2)));
		}

		if (StringUtils
				.isNotBlank(searchCommand.getValue(LAND_SIZE_CONDITION1))) {
			params.setLandSizeCondition1(Condition.valueOf(searchCommand
					.getValue(LAND_SIZE_CONDITION1)));
		}
		if (StringUtils
				.isNotBlank(searchCommand.getValue(LAND_SIZE_CONDITION2))) {
			params.setLandSizeCondition2(Condition.valueOf(searchCommand
					.getValue(LAND_SIZE_CONDITION2)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(DISTRICT))) {
			params.setDistrict(adminService.getDistrictById(searchCommand
					.getValue(DISTRICT)));
		}
		if (StringUtils.isNotBlank(searchCommand.getValue(COUNTY))) {
			params.setCounty(adminService.getCountyById(searchCommand
					.getValue(COUNTY)));
		}
		if (StringUtils.isNotBlank(searchCommand.getValue(SUBCOUNTY))) {
			params.setSubCounty(adminService.getSubCountyById(searchCommand
					.getValue(SUBCOUNTY)));
		}
		if (StringUtils.isNotBlank(searchCommand.getValue(PARISH))) {
			params.setParish(adminService.getParishById(searchCommand
					.getValue(PARISH)));
		}
		if (StringUtils.isNotBlank(searchCommand.getValue(VILLAGE))) {
			params.setVillage(adminService.getVillageById(searchCommand
					.getValue(VILLAGE)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(SMS_TEXT))) {
			params.setSmsText(searchCommand.getValue(SMS_TEXT));
		}
		return params;
	}

	/**
	 * prepares the crop model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param model
	 */
	public void prepareSearchModel(UserSearchParameters params,
			Boolean searching, Boolean newSearch, Integer pageNo,
			ModelMap model, boolean sms) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<User> users = userService.searchWithParams(params, pageNo);
		model.put("users", users);

		WebUtils.prepareNavigation("User",
				userService.numberOfUsersWithSearchParams(params),
				users.size(), pageNo, buildSearchNavigationUrl(params, sms),
				model);

		prepareSearchCommand(model, params);

		if (sms) {
			if (!model
					.containsAttribute(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE)) {
				if (newSearch)
					model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
							String.format("Found %s eligible recipient(s)",
									String.valueOf(users.size())));

				model.put(WebConstants.CONTENT_HEADER, "Recipients");

			} else
				model.put(WebConstants.CONTENT_HEADER, "List of Users");
		} else {
			if (!model
					.containsAttribute(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE)) {
				if (newSearch)
					model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
							String.format(
									"search completed with: %s result(s)",
									String.valueOf(users.size())));
				if (searching) {
					model.put(WebConstants.CONTENT_HEADER,
							"Users - search results ");
				}

			} else
				model.put(WebConstants.CONTENT_HEADER, "List of Users");
		}
	}

	/**
	 * builds a search navigation url based on the given search parameter
	 * object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildSearchNavigationUrl(UserSearchParameters params,
			boolean sms) {
		StringBuffer buffer = new StringBuffer();
		if (sms)
			buffer.append("/user?action=sms");
		else
			buffer.append("/user?action=search");

		if (StringUtils.isNotBlank(params.getNameOrUserName())) {
			buffer.append("&").append(NAME_OR_USERNAME).append("=")
					.append(params.getNameOrUserName());
		}

		if (params.getProducerOrg() != null) {
			buffer.append("&").append(PRODUCER_ORG).append("=")
					.append(params.getProducerOrg().getId());
		}

		if (params.getRole() != null) {
			buffer.append("&").append(ROLE).append("=")
					.append(params.getRole().getId());
		}

		if (params.getDistrict() != null) {
			buffer.append("&").append(DISTRICT).append("=")
					.append(params.getDistrict().getId());
		}

		if (params.getCounty() != null) {
			buffer.append("&").append(COUNTY).append("=")
					.append(params.getCounty().getId());
		}

		if (params.getSubCounty() != null) {
			buffer.append("&").append(SUBCOUNTY).append("=")
					.append(params.getSubCounty().getId());
		}

		if (params.getParish() != null) {
			buffer.append("&").append(PARISH).append("=")
					.append(params.getParish().getId());
		}

		if (params.getVillage() != null) {
			buffer.append("&").append(VILLAGE).append("=")
					.append(params.getVillage().getId());
		}

		if (StringUtils.isNotBlank(params.getCommitteeId())) {
			buffer.append("&").append(COMMITTEE).append("=")
					.append(params.getCommitteeId());
		}

		if (StringUtils.isNotBlank(params.getCommitteeMemberId())) {
			buffer.append("&").append(COMMITTEE_MEMBER).append("=")
					.append(params.getCommitteeMemberId());
		}

		if (params.getGender() != null) {
			buffer.append("&").append(GENDER).append("=")
					.append(params.getGender().toString());
		}
		if (params.getHouseHoldCategory() != null) {
			buffer.append("&").append(HOUSE_HOLD_CATEGORY).append("=")
					.append(params.getHouseHoldCategory().toString());
		}

		if (params.getLandSize1() != null) {
			buffer.append("&").append(LAND_SIZE1).append("=")
					.append(params.getLandSize1().toString());
		}

		if (params.getLandSize2() != null) {
			buffer.append("&").append(LAND_SIZE2).append("=")
					.append(params.getLandSize2().toString());
		}
		if (params.getLandSizeCondition1() != null) {
			buffer.append("&").append(LAND_SIZE_CONDITION1).append("=")
					.append(params.getLandSizeCondition1().toString());
		}
		if (params.getLandSizeCondition2() != null) {
			buffer.append("&").append(LAND_SIZE_CONDITION2).append("=")
					.append(params.getLandSize2().toString());
		}
		if (StringUtils.isNotBlank(params.getSmsText())) {
			buffer.append("&").append(SMS_TEXT).append("=")
					.append(params.getSmsText());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView searchNavigationHandler(
			@RequestParam(value = NAME_OR_USERNAME, required = false) String name,
			@RequestParam(value = PRODUCER_ORG, required = false) String pOrgId,
			@RequestParam(value = ROLE, required = false) String roleId,
			@RequestParam(value = GENDER, required = false) String gender,
			@RequestParam(value = HOUSE_HOLD_CATEGORY, required = false) String hhcategory,
			@RequestParam(value = LAND_SIZE1, required = false) String landsize1,
			@RequestParam(value = LAND_SIZE2, required = false) String landsize2,
			@RequestParam(value = LAND_SIZE_CONDITION1, required = false) String landsizecondition1,
			@RequestParam(value = LAND_SIZE_CONDITION2, required = false) String landsizecondition2,
			@RequestParam(value = DISTRICT, required = false) String districtId,
			@RequestParam(value = COUNTY, required = false) String countyid,
			@RequestParam(value = SUBCOUNTY, required = false) String subCountyId,
			@RequestParam(value = PARISH, required = false) String parishId,
			@RequestParam(value = VILLAGE, required = false) String villageId,
			@RequestParam(value = SMS_TEXT, required = false) String smsText,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(NAME_OR_USERNAME,
				new GenericCommandValue(name));
		command.getPropertiesMap().put(PRODUCER_ORG,
				new GenericCommandValue(pOrgId));
		command.getPropertiesMap().put(ROLE, new GenericCommandValue(roleId));

		command.getPropertiesMap().put(GENDER, new GenericCommandValue(gender));
		command.getPropertiesMap().put(HOUSE_HOLD_CATEGORY,
				new GenericCommandValue(hhcategory));

		command.getPropertiesMap().put(LAND_SIZE1,
				new GenericCommandValue(landsize1));
		command.getPropertiesMap().put(LAND_SIZE2,
				new GenericCommandValue(landsize2));
		command.getPropertiesMap().put(LAND_SIZE_CONDITION1,
				new GenericCommandValue(landsizecondition1));
		command.getPropertiesMap().put(LAND_SIZE_CONDITION2,
				new GenericCommandValue(landsizecondition2));

		command.getPropertiesMap().put(DISTRICT,
				new GenericCommandValue(districtId));
		command.getPropertiesMap().put(COUNTY,
				new GenericCommandValue(countyid));
		command.getPropertiesMap().put(SUBCOUNTY,
				new GenericCommandValue(subCountyId));
		command.getPropertiesMap().put(PARISH,
				new GenericCommandValue(parishId));
		command.getPropertiesMap().put(VILLAGE,
				new GenericCommandValue(villageId));
		command.getPropertiesMap().put(SMS_TEXT,
				new GenericCommandValue(smsText));

		prepareSearchModel(extractParams(command), true, false, pageNo, model,
				false);

		return new ModelAndView("viewUser", model);
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(method = RequestMethod.GET, params = { "action=sms" })
	public ModelAndView smsNavigationHandler(
			@RequestParam(value = NAME_OR_USERNAME, required = false) String name,
			@RequestParam(value = PRODUCER_ORG, required = false) String pOrgId,
			@RequestParam(value = ROLE, required = false) String roleId,
			@RequestParam(value = GENDER, required = false) String gender,
			@RequestParam(value = HOUSE_HOLD_CATEGORY, required = false) String hhcategory,
			@RequestParam(value = LAND_SIZE1, required = false) String landsize1,
			@RequestParam(value = LAND_SIZE2, required = false) String landsize2,
			@RequestParam(value = LAND_SIZE_CONDITION1, required = false) String landsizecondition1,
			@RequestParam(value = LAND_SIZE_CONDITION2, required = false) String landsizecondition2,
			@RequestParam(value = DISTRICT, required = false) String districtId,
			@RequestParam(value = COUNTY, required = false) String countyid,
			@RequestParam(value = SUBCOUNTY, required = false) String subCountyId,
			@RequestParam(value = PARISH, required = false) String parishId,
			@RequestParam(value = VILLAGE, required = false) String villageId,
			@RequestParam(value = SMS_TEXT, required = false) String smsText,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(NAME_OR_USERNAME,
				new GenericCommandValue(name));
		command.getPropertiesMap().put(PRODUCER_ORG,
				new GenericCommandValue(pOrgId));
		command.getPropertiesMap().put(ROLE, new GenericCommandValue(roleId));

		command.getPropertiesMap().put(GENDER, new GenericCommandValue(gender));
		command.getPropertiesMap().put(HOUSE_HOLD_CATEGORY,
				new GenericCommandValue(hhcategory));

		command.getPropertiesMap().put(LAND_SIZE1,
				new GenericCommandValue(landsize1));
		command.getPropertiesMap().put(LAND_SIZE2,
				new GenericCommandValue(landsize2));
		command.getPropertiesMap().put(LAND_SIZE_CONDITION1,
				new GenericCommandValue(landsizecondition1));
		command.getPropertiesMap().put(LAND_SIZE_CONDITION2,
				new GenericCommandValue(landsizecondition2));

		command.getPropertiesMap().put(DISTRICT,
				new GenericCommandValue(districtId));
		command.getPropertiesMap().put(COUNTY,
				new GenericCommandValue(countyid));
		command.getPropertiesMap().put(SUBCOUNTY,
				new GenericCommandValue(subCountyId));
		command.getPropertiesMap().put(PARISH,
				new GenericCommandValue(parishId));
		command.getPropertiesMap().put(VILLAGE,
				new GenericCommandValue(villageId));
		command.getPropertiesMap().put(SMS_TEXT,
				new GenericCommandValue(smsText));

		prepareSearchModel(extractParams(command), true, false, pageNo, model,
				true);

		return new ModelAndView("smsForm", model);
	}

	@Secured({ PermissionConstants.ADD_USER })
	@RequestMapping(value = "add/{qpage}", method = RequestMethod.GET)
	public ModelAndView addUserHandler(@PathVariable("qpage") String qPage,
			ModelMap modelMap) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("user", new User());
		prepareUserFormModel(modelMap);
		modelMap.put("qUserPage", qPage);
		modelMap.put("profile_Img", "empty");

		modelMap.put(WebConstants.CONTENT_HEADER, "Add new User");

		return new ModelAndView("formUser", modelMap);
	}

	public void prepareUserFormModel(ModelMap modelMap) {
		List<ProducerOrg> producerOrgs = producerOrgService
				.getProducerOrganisations();
		modelMap.put("producerOrgs", producerOrgs);
		List<Role> roles = roleService.getRoles();
		modelMap.put("roles", roles);
		modelMap.put("userstatus", new UserStatus[] { UserStatus.ENABLED,
				UserStatus.DISABLED });
		modelMap.put("gender", Gender.values());

		modelMap.put("houseHoldCategories", HouseHoldCategory.values());
	}

	@Secured({ PermissionConstants.EDIT_USER })
	@RequestMapping(value = "edit/{qpage}/{userid}", method = RequestMethod.GET)
	public ModelAndView editUserHandler(@PathVariable("userid") String id,
			@PathVariable("qpage") String userPage, ModelMap modelMap)
			throws SessionExpiredException {

		if (!modelMap.containsAttribute("user")) {
			User user = userService.getUserById(id);
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);

			prepareUserFormModel(modelMap);
			modelMap.put("user", user);
			modelMap.put("qUserPage", userPage);

			// if (user.getDistrict() != null)
			// modelMap.put("preselectedDistrictId", user.getDistrict()
			// .getId());
			// if (user.getCounty() != null)
			// modelMap.put("preselectedCountyId", user.getCounty().getId());
			// if (user.getSubCounty() != null)
			// modelMap.put("preselectedSubCountyId", user.getSubCounty()
			// .getId());
			// if (user.getParish() != null)
			// modelMap.put("preselectedParishId", user.getParish().getId());
			// if (user.getVillage() != null)
			// modelMap.put("preselectedVillageId", user.getVillage().getId());

			modelMap.put(WebConstants.CONTENT_HEADER, "Edit " + user.getName());

			return new ModelAndView("formUser", modelMap);
		}

		return WebConstants.DEFAULT_PAGE(OXMSecurityUtil.getLoggedInUser());
	}

	// ========================================================================================

	@Secured({ PermissionConstants.EDIT_USER, PermissionConstants.ADD_USER,
			PermissionConstants.ANNONYMOUS_USER })
	@RequestMapping(value = "save/{qpage}", method = RequestMethod.POST)
	public ModelAndView saveUserHandler(
			@PathVariable("qpage") String qPage,
			@ModelAttribute("user") User user,
			@RequestParam(value = "userPic", required = false) MultipartFile userPic,
			ModelMap modelMap) throws IOException, SessionExpiredException {

		User existingUser = user;

		try {

			userService.validate(user);

			if (userPic != null && userPic.getSize() > 0) {
				validatePic(userPic);
				existingUser.setProfilePicture(userPic.getBytes());
			}

			if (StringUtils.isNotEmpty(user.getId())) {
				existingUser = userService.getUserById(user.getId());
				copyUserContents(existingUser, user);
			} else {
				existingUser.setId(null);
			}

			userService.saveUser(existingUser);
		} catch (Exception e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());

			modelMap.put(
					WebConstants.CONTENT_HEADER,
					"Retry - add/edit "
							+ (StringUtils.isNotBlank(existingUser.getName()) ? existingUser
									.getName() : "User"));

			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);

			prepareUserFormModel(modelMap);
			modelMap.put("user", user);
			modelMap.put("qUserPage", qPage);

			modelMap.put(WebConstants.CONTENT_HEADER, "Edit " + user.getName());

			return new ModelAndView("formUser", modelMap);
		}

		if (qPage.equals("0")) {
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"User saved sucessfully.");
		}

		if (qPage.equals(":")) {

			try {
				User loginedUser = OXMSecurityUtil.getLoggedInUser();
				if (loginedUser.hasAdministrativePrivileges()) {
					return viewUsersHandler(new ModelMap());
				} else {
					modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
							"Invalid path");
				}
			} catch (SessionExpiredException e) {
				log.error("Error", e);
			}
		}

		return new ModelAndView("dashboard", modelMap);

	}

	public void validatePic(MultipartFile userPic) throws Exception {
		if (!WebConstants.isFileAnImage(userPic)) {
			throw new Exception(
					"error: the file uploaded for the user photo is not an image.");
		}

		if (!WebUtils.isValidSize(userPic,
				WebConstants.DEFAULT_IMAGE_SIZE_IN_BYTES)) {
			throw new Exception(
					String.format(
							"error: the next of kin photo exceeds the maximum size of >> %s",
							WebConstants.DEFAULT_IMAGE_SIZE_IN_BYTES));
		}
	}

	public void copyUserContents(User existingUser, User user) {
		existingUser.setName(user.getName());
		existingUser.setGender(user.getGender());
		existingUser.setDateOfBirth(user.getDateOfBirth());

		existingUser.setAddress(user.getAddress());
		existingUser.setPhone1(user.getPhone1());
		existingUser.setPhone2(user.getPhone2());

		// account
		existingUser.setUsername(user.getUsername());
		if (StringUtils.isNotEmpty(user.getPassword())) {
			existingUser.setSalt(user.getSalt());
			existingUser.setClearTextPassword(user.getClearTextPassword());
		}
		existingUser.setStatus(user.getStatus());
		existingUser.setRoles(user.getRoles());

		existingUser.setProducerOrg(user.getProducerOrg());

		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setDateOfJoining(user.getDateOfJoining());
		existingUser.setDateCreated(user.getDateCreated());

		existingUser.setAge(user.getAge());
		existingUser.setYearOfJoining(user.getYearOfJoining());

		existingUser.setMaritalStatus(user.getMaritalStatus());
		existingUser.setHouseHoldCategory(user.getHouseHoldCategory());

		existingUser.setCreatedBy(user.getCreatedBy());

		/*
		 * private String name, username, password, salt, clearTextPassword;
		 * private String address, phone1, phone2;
		 * 
		 * private Date dateOfBirth, dateOfJoining, dateCreated; private Integer
		 * age, yearOfJoining; private MaritalStatus maritalStatus; private
		 * CategoryOfHouseHold categoryOfHouseHold;
		 * 
		 * private User createdBy;
		 * 
		 * private SubCounty subCounty; private Parish parish; private Village
		 * village; private GisCordinate gisCordinates; private Double landSize;
		 * private List<LandUse> landUses; private ProducerOrg producerOrg;
		 */
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "pic/{id}", method = RequestMethod.GET)
	public void userPicHandler(@PathVariable("id") String userId,
			HttpServletResponse response) throws IOException {
		User user = userService.getUserById(userId);
		if (user != null) {
			if (user.getProfilePicture() != null
					&& user.getProfilePicture().length > 0) {

				String contentType = "image/jpeg";

				WebConstants.writePictureOnResponse(contentType,
						user.getProfilePicture(), response);
			}
		}
	}

	@Secured({ PermissionConstants.DELETE_USER })
	@RequestMapping(value = "delete/{ids}", method = RequestMethod.GET)
	public ModelAndView deleteUserHandler(@PathVariable("ids") String userIds,
			ModelMap modelMapMap) throws SessionExpiredException {
		String[] idz2Delete = userIds.split(",");
		try {
			userService.deleteUsersByIds(idz2Delete);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"User(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
		}
		return viewUsersHandler(modelMapMap);
	}

	// =========================================================================================

	@Secured({ PermissionConstants.EXPORT_TO_EXCELL })
	@RequestMapping(value = "search/exportexcel", method = RequestMethod.POST)
	public void exportMsExcelHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			ModelMap modelMap, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			UserSearchParameters params = extractParams(searchCommand);

			UserExcelExportUtil userExportUtil = new UserExcelExportUtil(params);

			userExportUtil.addUsersWorkSheetAndHeaders();

			String fileName = userExportUtil.getFileName();

			fileName = fileName.replace(" ", "_") + ".xlsx";

			log.info("Creating Microsoft Excel workBook to hold the results");

			List<User> users = userService.searchWithParams(params);

			userExportUtil.populateWorkBook(users);

			// auto-size columns
			for (int k = 0; k <= userExportUtil.getLastColumnIndex(); k++)
				userExportUtil.getWb().getSheet(userExportUtil.getSheetName())
						.autoSizeColumn(k);

			// =============== =============== ============

			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);

			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

			// =============== ============== =============

			// write workBook to the ServeletOutputStream
			OutputStream os = response.getOutputStream();

			userExportUtil.getWb().write(response.getOutputStream());
			os.flush();
			os.close();

		} catch (Exception e) {
			log.error("Error occured in populating MS-Excel Document: "
					+ e.getMessage());
			e.printStackTrace();
			throw new ServletException(
					"Error occured in populating MS-Excel Document", e);
		} finally {

		}
	}

	// ====================================================================================

	@Secured({ PermissionConstants.EXPORT_TO_EXCELL })
	@RequestMapping(value = "search/exportpdf", method = RequestMethod.POST)
	public void exportPdfHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			ModelMap modelMap, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			UserSearchParameters params = extractParams(searchCommand);

			UserPdfExportUtil userPdfExportUtil = new UserPdfExportUtil(params);

			String fileName = userPdfExportUtil.getFileName();

			fileName = fileName.replace(" ", "_") + ".pdf";

			log.info("Creating pdf doc hold the results");

			List<User> users = userService.searchWithParams(params);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(userPdfExportUtil.getDocument(), baos);

			userPdfExportUtil.populateDocument(users);

			// ===================== ===========================

			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-store");
			response.setDateHeader("Expires", 0);

			// "Content-disposition", "inline; filename=foobar.pdf"
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName);
			response.setContentType("application/pdf");

			// ===================== ===========================

			// write ByteArrayOutputStream to the ServeletOurputStream
			OutputStream os = response.getOutputStream();

			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			log.error("Error occured in populating MS-Excel Document: "
					+ e.getMessage());
			e.printStackTrace();
			throw new ServletException(
					"Error occured in populating MS-Excel Document", e);
		} finally {

		}
	}

	// =========================================================================================

	@Secured({ PermissionConstants.VIEW_USER })
	@RequestMapping(value = "sms", method = RequestMethod.GET)
	public ModelAndView viewSmsHandler(ModelMap modelMap) {

		prepareSearchModel(new UserSearchParameters(), false, false, 1,
				modelMap, true);

		modelMap.put(WebConstants.CONTENT_HEADER, "List of Users");
		return new ModelAndView("smsForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_USER })
	@RequestMapping(value = "sms/load", method = RequestMethod.POST)
	public ModelAndView smsFormHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			ModelMap modelMap) {

		UserSearchParameters params = extractParams(searchCommand);
		prepareSearchModel(params, true, true, 1, modelMap, true);

		return new ModelAndView("smsForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_USER })
	@RequestMapping(value = "sms/send", method = RequestMethod.POST)
	public ModelAndView sendSmsHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			ModelMap modelMap) {

		UserSearchParameters params = extractParams(searchCommand);

		List<User> users = userService.searchWithParams(params);

		try {

			if (users == null || users.size() == 0)
				throw new ValidationException("No reciepients found");
			if (StringUtils.isBlank(params.getSmsText()))
				throw new ValidationException("SMS Text can not be blank");

			String recievers = generateReciepientString(users);
			if (StringUtils.isBlank(recievers))
				throw new ValidationException(
						"No reciepient had a valid telephone number");

			Sender s = new Sender(params.getSmsText(), recievers, "FarmersSys");

			s.sendMessage();

			int num = recievers.split(",").length;
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Succesfully sent SMS to " + num + " recipient(s)!!");
		} catch (Exception e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}

		prepareSearchModel(params, true, false, 1, modelMap, false);

		return new ModelAndView("smsForm", modelMap);
	}

	private String generateReciepientString(List<User> users) {

		String recievers = "";

		for (User user : users) {

			if (StringUtils.isNotBlank(user.getPhone1()))
				recievers = appendReciepientNumbers(recievers, user.getPhone1());

			if (StringUtils.isNotBlank(user.getPhone2()))
				recievers = appendReciepientNumbers(recievers, user.getPhone2());

		}
		return recievers;
	}

	private String appendReciepientNumbers(String recievers, String phone) {

		if (StringUtils.isNotBlank(phone)) {
			if (phone.contains("/")) {
				String[] phones = phone.split("/");
				for (String str : phones) {
					String validPhone = generateValidPhoneNumber(str);
					if (StringUtils.isNotBlank(validPhone))
						recievers += (StringUtils.isBlank(recievers)) ? validPhone
								: "," + validPhone;
				}
			} else {
				String validPhone = generateValidPhoneNumber(phone);
				if (StringUtils.isNotBlank(validPhone))
					recievers += (StringUtils.isBlank(recievers)) ? validPhone
							: "," + validPhone;
			}
		}
		return recievers;
	}

	private String generateValidPhoneNumber(String phone) {
		String ugandaCode = "256";
		if (phone.length() == 10 && phone.substring(0, 1).equalsIgnoreCase("0"))
			phone = phone.substring(1);

		if (phone.substring(0, 0).equalsIgnoreCase("+"))
			phone = phone.substring(1);

		if (phone.length() == 9)
			phone = ugandaCode.concat(phone);

		if (phone.length() == 12)
			return phone;

		return null;
	}

	@SuppressWarnings("unused")
	private void testSms() {

		try {
			String username = "ugd-rweb";
			String password = "jar123";
			// String phoneNumber = "256782845911,256790790181";//me and simon
			// me, okena, mary
			String phoneNumber = "256782845911,256776710217,256787998439";
			String message = "Testing sms on Oxfam System";

			String routeUrl = "http://121.241.242.114:80/bulksms/bulksms?";

			String q = "username=" + URLEncoder.encode(username, "UTF-8");
			q += "&" + "password=" + URLEncoder.encode(password, "UTF-8");
			q += "&type=0&dlr=0";
			q += "&" + "destination=" + phoneNumber;
			q += "&source=FarmersSys";
			q += "&message=" + message;

			URL url = new URL(routeUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(q);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			System.out.println("RouteSMS API Response :");
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}

		} catch (Exception e) {
			log.error("Error", e);
		}

	}

}
