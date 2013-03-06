package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.District;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ProducerOrgService;
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
public class ProducerOrganizationController {

	@Autowired
	private Adminservice adminservice;

	@Autowired
	private ProducerOrgService producerOrgService;

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewProdnOrgHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("pOrganizations",
				producerOrgService.getProducerOrganisations());
		return new ModelAndView("viewProducerOrg", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addProdnOrgHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("pOrganization", new ProducerOrganisation());
		List<District> districts = adminservice.getDistricts();
		if (districts.size() == 0) {
			model.put(
					WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No districts found, saving a Producer Organisation requires a District and sub-county!!");
			return viewProdnOrgHandler(model);
		}

		model.put("districts", districts);
		return new ModelAndView("formProducerOrg", model);

	}

	@Secured({ PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProdnOrgHandler(ModelMap model,
			@PathVariable("id") String pOrgId) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);

		ProducerOrganisation pOrg = producerOrgService
				.getProducerOrganisationById(pOrgId);

		if (pOrg != null) {
			model.put("pOrganization", pOrg);
			return new ModelAndView("formProductionOrg", model);
		}

		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid production organization id submitted");
		return viewProdnOrgHandler(model);

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

		return viewProdnOrgHandler(model);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView saveProductionOrgHandler(
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
		return viewProdnOrgHandler(model);
	}
}
