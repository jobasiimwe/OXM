package org.agric.oxm.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.model.District;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.UserStatus;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.DefaultConcepts;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("producerorg")
public class ProducerOrgController {

	@Autowired
	private Adminservice adminservice;

	@Autowired
	private ProducerOrgService producerOrgService;

	@Autowired
	private UserService userService;
	
	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewProducerOrgHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("pOrganizations",
				producerOrgService.getProducerOrganisations());
		return new ModelAndView("viewProducerOrg", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addProducerOrgHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("pOrganization", new ProducerOrganisation());
		prepareProducerOrgFormModel(modelMap);
		return new ModelAndView("formProducerOrg", modelMap);

	}

	private void prepareProducerOrgFormModel(ModelMap modelMap) {
		List<District> districts = adminservice.getDistricts();
		if (districts.size() == 0) {
			modelMap.put(
					WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No districts found, saving a Producer Organisation requires a District and sub-county!!");
		}

		modelMap.put("districts", districts);
	}

	@Secured({ PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProducerOrgHandler(ModelMap modelMap,
			@PathVariable("id") String pOrgId) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		if (!modelMap.containsAttribute("pOrganization")) {
			ProducerOrganisation pOrg = producerOrgService
					.getProducerOrganisationById(pOrgId);

			if (pOrg != null)
				modelMap.put("pOrganization", pOrg);
			else {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"Invalid production organization id submitted");
				return viewProducerOrgHandler(modelMap);
			}
		}

		prepareProducerOrgFormModel(modelMap);

		return new ModelAndView("formProducerOrg", modelMap);
	}

	@Secured({ PermissionConstants.DELETE_PROD_ORG })
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{idz}")
	public ModelAndView deleteProducerOrgHandler(
			@PathVariable("idz") String idz, ModelMap model)
			throws SessionExpiredException {

		String[] ids = idz.split(",");
		try {
			producerOrgService.deleteProducerOrganisationsByIds(ids);

		} catch (Exception e) {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"no producer organisation(s) supplied for deleting");
		}

		return viewProducerOrgHandler(model);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView saveProducerOrgHandler(
			@ModelAttribute("pOrganization") ProducerOrganisation pOrganization,
			ModelMap model) throws SessionExpiredException {

		ProducerOrganisation existingPOrganization = pOrganization;

		if (StringUtils.isNotEmpty(pOrganization.getId())) {
			existingPOrganization = producerOrgService
					.getProducerOrganisationById(pOrganization.getId());
			existingPOrganization.setName(pOrganization.getName());

			if (pOrganization.getProducers() != null) {
				existingPOrganization
						.setProducers(pOrganization.getProducers());
			}
		} else {
			existingPOrganization.setId(null);
		}

		try {
			producerOrgService.validate(existingPOrganization);
			producerOrgService.save(existingPOrganization);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Production Organization saved sucessfully.");
		} catch (ValidationException e) {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), model);
			model.put("pOrganization", pOrganization);
			return new ModelAndView("formProducerOrg", model);

		}
		return viewProducerOrgHandler(model);
	}

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/producers/view/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView viewProducerOrgProducersHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap model) {

		ProducerOrganisation pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		model.put("pOrg", pOrg);
		return new ModelAndView("viewPOrgProducers", model);
	}
	
	@Secured({ PermissionConstants.ADD_PRODUCER })
	@RequestMapping(value = "/producers/add/{pOrgId}", method = RequestMethod.GET)
	public ModelAndView addProducerOrgProducersHandler(
			@PathVariable("pOrgId") String pOrgId, ModelMap model) {

		ProducerOrganisation pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);
		model.put("pOrg", pOrg);
		
		//ROLE_PRODUCER id = 4836AFAB-3D62-482c-BA9A-D9D15839C68A
		Role roleProducer = userService.getRoleById("4836AFAB-3D62-482c-BA9A-D9D15839C68A");
		User producer = new User(pOrg, roleProducer);
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleProducer);
		model.put("roles", roles);
		model.put("userstatus", UserStatus.values());
		return new ModelAndView("formPOrgProducer", model);
	}

	private void prepareUserFormModel(ModelMap model) {
		List<Role> roles = userService.getRoles();
		model.put("roles", roles);
		model.put("userstatus", new UserStatus[] { UserStatus.ENABLED,
				UserStatus.DISABLED });
		model.put("gender", OXMUtil.getGenderList());
	}
}
