package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.ProductionOrganisation;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ProductionOrganisationService;
import org.agric.oxm.web.WebConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductionOrganizationController {

    @Autowired
    private ProductionOrganisationService prdnOrgService;

    @RequestMapping(value = "/pOrganization/view/", method = RequestMethod.GET)
    public ModelAndView viewProdnOrgHandler(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	model.put("pOrganizations", prdnOrgService.getProductionOrganisations());
	return new ModelAndView("viewProductionOrg", model);
    }

    @RequestMapping(value = "/pOrganization/add/", method = RequestMethod.GET)
    public ModelAndView addProdnOrgHandler(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	model.put("pOrganization", new ProductionOrganisation());
	return new ModelAndView("formProductionOrg", model);

    }

    @RequestMapping(value = "/pOrganization/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editProdnOrgHandler(ModelMap model, @PathVariable("id") String pOrgId)
	    throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);

	ProductionOrganisation pOrg = prdnOrgService.getProductionOrganisationById(pOrgId);

	if (pOrg != null) {
	    model.put("pOrganization", pOrg);
	    return new ModelAndView("formProductionOrg", model);
	}

	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		"Invalid production organization id submitted");
	return viewProdnOrgHandler(model);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/pOrganization/delete/")
    public void deleteProdnOrgHandler(@RequestParam("selectedPOrganization") List<String> ids,
	    HttpServletResponse response) {

	try {
	    if (ids != null) {
		String[] sPOrgIds = new String[ids.size()];
		sPOrgIds = ids.toArray(sPOrgIds);

		prdnOrgService.deleteProductionOrganisationsByIds(sPOrgIds);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write("Production Organization(s) deleted successfully");
	    } else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(
			"no selling place(s) supplied for deleting");
	    }
	} catch (IOException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

    }

    @RequestMapping(method = RequestMethod.POST, value = "/pOrganization/save/")
    public ModelAndView saveSellingPlaceHandler(
	    @ModelAttribute("pOrganization") ProductionOrganisation pOrganization,
			ModelMap model) throws SessionExpiredException {

	ProductionOrganisation existingPOrganization = pOrganization;

	if (StringUtils.isNotEmpty(pOrganization.getId())) {
	    existingPOrganization = prdnOrgService.getProductionOrganisationById(pOrganization
		    .getId());
	    existingPOrganization.setName(pOrganization.getName());

	    if (pOrganization.getProducers() != null) {
		existingPOrganization.setProducers(pOrganization.getProducers());
	    }
	}

	try {
	    prdnOrgService.validate(existingPOrganization);
	    prdnOrgService.save(existingPOrganization);
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "Production Organization saved sucessfully.");
	} catch (ValidationException e) {
	    model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
	    WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	    model.put("pOrganization", pOrganization);
	    return new ModelAndView("formProductionOrg", model);

	}
	return viewProdnOrgHandler(model);
    }
}
