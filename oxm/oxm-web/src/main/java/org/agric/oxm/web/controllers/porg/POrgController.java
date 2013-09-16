package org.agric.oxm.web.controllers.porg;

import java.util.List;

import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProducerOrgSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ProducerOrgService;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("porg")
public class POrgController {

	@Autowired
	private Adminservice adminService;

	@Autowired
	private ProducerOrgService producerOrgService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Search parameter field names
	 */
	public static final String NAME = "name";

	public static final String DISTRICT = "district";
	public static final String COUNTY = "county";
	public static final String SUBCOUNTY = "subCounty";
	public static final String PARISH = "parish";
	public static final String VILLAGE = "village";

	private static final String COMMAND_NAME = "porgsearch";

	public void prepareSearchCommand(ModelMap modelMap,
			ProducerOrgSearchParameters params) {
		GenericCommand searchCommand = new GenericCommand();

		searchCommand.checkAndPut(NAME, params.getName());

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

		modelMap.put(COMMAND_NAME, searchCommand);

		modelMap.put("districts", adminService.getDistricts());
		// modelMap.put("roles", userService.getRoles());
		// modelMap.put("genders", OXMUtil.getGenderList());
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap) {
		try {
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);
		} catch (Exception e) {

		}
		prepareSearchModel(new ProducerOrgSearchParameters(), false, false, 1,
				modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"List of Producer Organisations");
		return new ModelAndView("viewProducerOrg", modelMap);

	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		ProducerOrgSearchParameters params = extractSearchParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, true, true, pageNo, modelMap);

		return new ModelAndView("viewProducerOrg", modelMap);
	}

	/**
	 * prepares the crop model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param model
	 */
	public void prepareSearchModel(ProducerOrgSearchParameters params,
			Boolean searching, Boolean newSearch, Integer pageNo, ModelMap model) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<ProducerOrg> porgs = producerOrgService.searchWithParams(params,
				pageNo);
		model.put("pOrganizations", porgs);

		WebUtils.prepareNavigation("ProducerOrgs",
				producerOrgService.numberInSearch(params), porgs.size(),
				pageNo, buildSearchNavigationUrl(params), model);

		prepareSearchCommand(model, params);

		if (!model
				.containsAttribute(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE)) {
			if (newSearch)
				model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE, String
						.format("search completed with: %s result(s)",
								String.valueOf(porgs.size())));
			if (searching) {
				model.put(WebConstants.CONTENT_HEADER,
						"Users - search results ");
			}
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
	private String buildSearchNavigationUrl(ProducerOrgSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/porg?action=search");

		GenericCommand.checkAndAppend(NAME, params.getName(), buffer);
		GenericCommand.checkAndAppend(DISTRICT, params.getDistrict(), buffer);
		GenericCommand.checkAndAppend(COUNTY, params.getCounty(), buffer);
		GenericCommand.checkAndAppend(SUBCOUNTY, params.getSubCounty(), buffer);

		GenericCommand.checkAndAppend(PARISH, params.getParish(), buffer);
		GenericCommand.checkAndAppend(VILLAGE, params.getVillage(), buffer);

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView navigate(
			@RequestParam(value = NAME, required = false) String name,
			@RequestParam(value = DISTRICT, required = false) String districtId,
			@RequestParam(value = COUNTY, required = false) String countyid,
			@RequestParam(value = SUBCOUNTY, required = false) String subCountyId,
			@RequestParam(value = PARISH, required = false) String parishId,
			@RequestParam(value = VILLAGE, required = false) String villageId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(NAME, new GenericCommandValue(name));
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

		return searchHandler(command, pageNo, modelMap);
	}

	public ProducerOrgSearchParameters extractSearchParams(
			GenericCommand searchCommand) {
		ProducerOrgSearchParameters params = new ProducerOrgSearchParameters();

		if (StringUtils.isNotBlank(searchCommand.getValue(NAME))) {
			params.setName(searchCommand.getValue(NAME));
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
		return params;
	}

	// ===================================================================================

	@Secured({ PermissionConstants.ADD_PROD_ORG })
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		try {
			ProducerOrg porg = new ProducerOrg();
			modelMap.put(WebConstants.CONTENT_HEADER,
					"Add new Producer Organisation");
			prepareProducerOrgFormModel(porg, modelMap);
			return new ModelAndView("formProducerOrg", modelMap);
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			return view(modelMap);
		}

	}

	private void prepareProducerOrgFormModel(ProducerOrg porg, ModelMap modelMap) {

		modelMap.put("pOrganization", porg);

		if (null != porg.getDistrict()) {
			modelMap.put("pDistrict", porg.getDistrict().getId());
		}

		if (null != porg.getCounty()) {
			modelMap.put("pCounty", porg.getCounty().getId());
		}

		if (null != porg.getSubCounty()) {
			modelMap.put("pSubCounty", porg.getSubCounty().getId());
		}

		if (null != porg.getParish()) {
			modelMap.put("pParish", porg.getParish().getId());
		}

		if (null != porg.getVillage()) {
			modelMap.put("pVillage", porg.getVillage().getId());
		}
	}

	@Secured({ PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(ModelMap modelMap,
			@PathVariable("id") String pOrgId) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		ProducerOrg pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);

		if (pOrg != null) {
			modelMap.put("pOrganization", pOrg);
			modelMap.put(WebConstants.CONTENT_HEADER,
					"Edit Producer Organisation " + pOrg.getName());
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Invalid production organization id submitted");
			return view(modelMap);
		}

		prepareProducerOrgFormModel(pOrg, modelMap);

		return new ModelAndView("formProducerOrg", modelMap);
	}

	@Secured({ PermissionConstants.DELETE_PROD_ORG })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{idz}")
	public ModelAndView delete(@PathVariable("idz") String idz,
			ModelMap modelMap) throws SessionExpiredException {

		String[] ids = idz.split(",");
		try {
			producerOrgService.deleteProducerOrganisationsByIds(ids);

		} catch (Exception e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"no producer organisation(s) supplied for deleting");
		}

		return view(modelMap);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(@ModelAttribute("pOrganization") ProducerOrg pOrg,
			ModelMap modelMap) throws SessionExpiredException {

		ProducerOrg existingPOrg = pOrg;

		if (StringUtils.isNotEmpty(pOrg.getId())) {
			existingPOrg = producerOrgService.getProducerOrganisationById(pOrg
					.getId());
			existingPOrg.setName(pOrg.getName());

			if (pOrg.getProducers() != null) {
				existingPOrg.setProducers(pOrg.getProducers());
			}
		} else {
			existingPOrg.setId(null);
		}

		try {
			producerOrgService.validate(existingPOrg);
			producerOrgService.save(existingPOrg);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Production Organization saved sucessfully.");
		} catch (ValidationException e) {
			log.error("Error Saving producerOrg: - ", e);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);

			prepareProducerOrgFormModel(pOrg, modelMap);
			modelMap.put(WebConstants.CONTENT_HEADER, "Retry Add/Edit "
					+ (StringUtils.isNotBlank(pOrg.getName()) ? pOrg.getName()
							: "Producer organisation"));

			return new ModelAndView("formProducerOrg", modelMap);

		}
		return view(modelMap);
	}

	// ==============================================================

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/details/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView viewProducerOrgDetailsHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {
		prepareProducerOrgViewsModel(pOrgId, "Details of - ", modelMap);
		return new ModelAndView("viewPOrgDetails", modelMap);
	}

	/**
	 * Prepares models for all views of producer org, i.e. the details,
	 * committees, staff, documents views
	 * 
	 * @param pOrgId
	 * @param contentHeaderString
	 * @param modelMap
	 */
	public void prepareProducerOrgViewsModel(String pOrgId,
			String contentHeaderString, ModelMap modelMap) {
		ProducerOrg pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		modelMap.put("pOrg", pOrg);

		modelMap.put(WebConstants.CONTENT_HEADER,
				contentHeaderString + pOrg.getName());
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/staff/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView viewProducerOrgStaffHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap modelMap) {
		prepareProducerOrgViewsModel(pOrgId, "Staff of - ", modelMap);
		return new ModelAndView("pOrgStaffView", modelMap);
	}

}
