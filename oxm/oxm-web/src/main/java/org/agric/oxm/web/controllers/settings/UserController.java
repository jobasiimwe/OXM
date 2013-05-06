package org.agric.oxm.web.controllers.settings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.Gender;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.UserStatus;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.agric.oxm.web.forms.GenericCommand;
import org.agric.oxm.web.forms.GenericCommandValue;
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

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Adminservice adminService;

	@Autowired
	private ProducerOrgService producerOrgService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Search parameter field names
	 */
	public static final String NAME_OR_USERNAME = "name-or-username";
	public static final String PRODUCER_ORG = "porg";
	public static final String ROLE = "role";
	public static final String GENDER = "gender";
	public static final String COMMITTEE = "committee";
	public static final String COMMITTEE_MEMBER = "committeemember";

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
					new GenericCommandValue(params.getGender().getName()));

		if (StringUtils.isNotBlank(params.getCommitteeMemberId())) {
			searchCommand.getPropertiesMap().put(COMMITTEE_MEMBER,
					new GenericCommandValue(params.getCommitteeMemberId()));
		}

		if (StringUtils.isNotBlank(params.getCommitteeId())) {
			searchCommand.getPropertiesMap().put(COMMITTEE,
					new GenericCommandValue(params.getCommitteeId()));
		}

		modelMap.put(COMMAND_NAME, searchCommand);

		modelMap.put("pOrgs", producerOrgService.getProducerOrganisations());
		modelMap.put("roles", userService.getRoles());
		modelMap.put("genders", OXMUtil.getGenderList());
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/view/page/{pageNo}", method = RequestMethod.GET)
	public ModelAndView viewUsersHandler(
			@PathVariable(value = "pageNo") Integer pageNo, ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		prepareSearchModel(new UserSearchParameters(), false, false, pageNo,
				modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, "List of Users");
		return new ModelAndView("viewUser", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		UserSearchParameters params = extractSearchParamsFromCommand(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, true, true, pageNo, modelMap);

		return new ModelAndView("viewUser", modelMap);
	}

	public UserSearchParameters extractSearchParamsFromCommand(
			GenericCommand searchCommand) {
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
			params.setRole(userService.getRoleById(searchCommand.getValue(ROLE)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(GENDER))) {
			params.setGender(Gender.valueOf(searchCommand.getValue(GENDER)));
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
			Boolean searching, Boolean newSearch, Integer pageNo, ModelMap model) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<User> users = userService.searchWithParams(params, pageNo);
		model.put("users", users);

		WebUtils.prepareNavigation("User",
				userService.numberOfUsersWithSearchParams(params),
				users.size(), pageNo, buildSearchNavigationUrl(params), model);

		prepareSearchCommand(model, params);

		if (newSearch)
			model.put(
					WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					String.format("search completed with: %s result(s)",
							String.valueOf(users.size())));
		if (searching) {
			model.put(WebConstants.CONTENT_HEADER, "Users - search results ");

			// set a variable searching to true
			// this variable is used in determining what navigation file to use
			model.put("searching", true);
		} else
			model.put(WebConstants.CONTENT_HEADER, "List of Users");

	}

	/**
	 * builds a search navigation url based on the given search parameter
	 * object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildSearchNavigationUrl(UserSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
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

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(method = RequestMethod.GET, value = "user", params = { "action=search" })
	public ModelAndView userSearchNavigationHandler(
			@RequestParam(value = NAME_OR_USERNAME, required = false) String name,
			@RequestParam(value = PRODUCER_ORG, required = false) String pOrgId,
			@RequestParam(value = ROLE, required = false) String roleId,
			@RequestParam(value = GENDER, required = false) String gender,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(NAME_OR_USERNAME,
				new GenericCommandValue(name));
		command.getPropertiesMap().put(PRODUCER_ORG,
				new GenericCommandValue(pOrgId));
		command.getPropertiesMap().put(ROLE, new GenericCommandValue(roleId));
		command.getPropertiesMap().put(GENDER, new GenericCommandValue(gender));

		prepareSearchModel(extractSearchParamsFromCommand(command), true,
				false, pageNo, model);

		return new ModelAndView("viewUser", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/add/{qpage}", method = RequestMethod.GET)
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
		List<ProducerOrganisation> producerOrgs = producerOrgService
				.getProducerOrganisations();
		modelMap.put("producerOrgs", producerOrgs);
		List<Role> roles = userService.getRoles();
		modelMap.put("roles", roles);
		modelMap.put("userstatus", new UserStatus[] { UserStatus.ENABLED,
				UserStatus.DISABLED });
		modelMap.put("gender", OXMUtil.getGenderList());
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/edit/{qpage}/{userid}", method = RequestMethod.GET)
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

			modelMap.put(WebConstants.CONTENT_HEADER, "Edit " + user.getName());

			return new ModelAndView("formUser", modelMap);
		}

		return WebConstants.DEFAULT_PAGE(OXMSecurityUtil.getLoggedInUser());
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION,
			PermissionConstants.ANNONYMOUS_USER })
	@RequestMapping(value = "/user/save/{qpage}", method = RequestMethod.POST)
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
					return viewUsersHandler(1, new ModelMap());
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
		existingUser.setPhone1(user.getPhone1());
		existingUser.setPhone2(user.getPhone2());
		existingUser.setUsername(user.getUsername());
		existingUser.setStatus(user.getStatus());
		existingUser.setRoles(user.getRoles());
		existingUser.setProducerOrg(user.getProducerOrg());

		if (StringUtils.isNotEmpty(user.getPassword())) {
			existingUser.setSalt(user.getSalt());
			existingUser.setClearTextPassword(user.getClearTextPassword());
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/pic/{id}", method = RequestMethod.GET)
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

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/user/delete/{ids}", method = RequestMethod.GET)
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
		return viewUsersHandler(1, modelMapMap);
	}

	@RequestMapping("/annoymous/create")
	public ModelAndView annoymousUserHander(ModelMap modelMap)
			throws SessionExpiredException {
		modelMap.put("annoymousUser", new User());
		prepareAnnoymousUser(modelMap);
		return new ModelAndView("createUser", modelMap);
	}

	private void prepareAnnoymousUser(ModelMap modelMap) {
		List<Role> roles = userService.getRoles();
		List<Role> rz = new ArrayList<Role>();
		Role dd = null;
		for (Role r : roles) {
			if (r.getName().endsWith("ROLE_ANNOYMOUS_USER")) {
				dd = r;
			}

		}
		rz.add(dd);
		modelMap.put("roles", rz);
		modelMap.put("userstatus", new UserStatus[] { UserStatus.ENABLED,
				UserStatus.DISABLED });
		modelMap.put("gender", OXMUtil.getGenderList());

		modelMap.put("subcounties", adminService.getSubCounties());
		modelMap.put("parishes", adminService.getParishes());
		modelMap.put("villages", adminService.getVillages());
	}

	@RequestMapping(value = "/annoymous/save", method = RequestMethod.POST)
	public ModelAndView saveAnnoymousUserHandler(
			@ModelAttribute("annoymousUser") User user,
			@RequestParam(value = "userPic", required = false) MultipartFile userPic,
			ModelMap modelMap) throws IOException, SessionExpiredException {

		User existingUser = user;
		try {
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

		} catch (Exception e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("annoymousUser", user);
			prepareAnnoymousUser(modelMap);
			return new ModelAndView("createUser", modelMap);
		}
		if (StringUtils.isNotEmpty(user.getId())) {
			existingUser = userService.getUserById(user.getId());
			copyUserContents(existingUser, user);
			existingUser.setSubCounty(user.getSubCounty());
			existingUser.setParish(user.getParish());
			existingUser.setVillage(user.getVillage());
		} else {
			existingUser.setId(null);
		}

		try {

			if (userPic != null && userPic.getSize() > 0) {
				existingUser.setProfilePicture(userPic.getBytes());
			}
			userService.validate(existingUser);
			userService.saveUser(existingUser);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Thank You for registering. you can now login.");
			return new ModelAndView("sucessfullogin", modelMap);
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("annoymousUser", user);
			prepareAnnoymousUser(modelMap);
			return new ModelAndView("createUser", modelMap);
		}

	}

}
