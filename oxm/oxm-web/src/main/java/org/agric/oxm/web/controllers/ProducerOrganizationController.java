package org.agric.oxm.web.controllers;

import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ProductionOrganisationService;
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
public class ProducerOrganizationController {

	@Autowired
	private ProductionOrganisationService prdnOrgService;

	@Secured({ PermissionConstants.VIEW_PROD_ORG })
	@RequestMapping(value = "/pOrganization/view/", method = RequestMethod.GET)
	public ModelAndView viewProdnOrgHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("pOrganizations", prdnOrgService.getProductionOrganisations());
		return new ModelAndView("viewProducerOrg", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/pOrganization/add/", method = RequestMethod.GET)
	public ModelAndView addProdnOrgHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("pOrganization", new ProducerOrganisation());
		return new ModelAndView("formProducerOrg", model);

	}

	@Secured({ PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(value = "/pOrganization/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProdnOrgHandler(ModelMap model,
			@PathVariable("id") String pOrgId) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);

		ProducerOrganisation pOrg = prdnOrgService
				.getProductionOrganisationById(pOrgId);

		if (pOrg != null) {
			model.put("pOrganization", pOrg);
			return new ModelAndView("formProductionOrg", model);
		}

		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid production organization id submitted");
		return viewProdnOrgHandler(model);

	}

	@Secured({ PermissionConstants.DELETE_PROD_ORG })
	@RequestMapping(method = RequestMethod.GET, value = "/pOrganization/delete/{idz}")
	public ModelAndView deleteProducerOrgHandler(
			@PathVariable("idz") String idz, ModelMap model)
			throws SessionExpiredException {

		String[] ids = idz.split(",");
		try {
			prdnOrgService.deleteProductionOrganisationsByIds(ids);

		} catch (Exception e) {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"no producer organisation(s) supplied for deleting");
		}

		return viewProdnOrgHandler(model);
	}

	@Secured({ PermissionConstants.ADD_PROD_ORG,
			PermissionConstants.EDIT_PROD_ORG })
	@RequestMapping(method = RequestMethod.POST, value = "/pOrganization/save/")
	public ModelAndView saveProductionOrgHandler(
			@ModelAttribute("pOrganization") ProducerOrganisation pOrganization,
			ModelMap model) throws SessionExpiredException {

		ProducerOrganisation existingPOrganization = pOrganization;

		if (StringUtils.isNotEmpty(pOrganization.getId())) {
			existingPOrganization = prdnOrgService
					.getProductionOrganisationById(pOrganization.getId());
			existingPOrganization.setName(pOrganization.getName());

			if (pOrganization.getProducers() != null) {
				existingPOrganization
						.setProducers(pOrganization.getProducers());
			}
		} else {
			existingPOrganization.setId(null);
		}

		try {
			prdnOrgService.validate(existingPOrganization);
			prdnOrgService.save(existingPOrganization);
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
