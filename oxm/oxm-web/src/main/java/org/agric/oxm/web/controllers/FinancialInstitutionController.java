package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.FinancialInstitutionService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FinancialInstitutionController {

    @Autowired
    FinancialInstitutionService financialService;

    @Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
    @RequestMapping(value = "/fInstitution/view/", method = RequestMethod.GET)
    public ModelAndView viewFiniancialInsititutionHandler(ModelMap model)
			throws SessionExpiredException {
	List<FinancialInstitution> fInstitution = financialService.getFinancialInstitutions();
	model.put("fInstitutions", fInstitution);
	return new ModelAndView("viewFinancialInstitution", model);

    }

    @Secured({ PermissionConstants.ADD_FINANCIAL_INSTITUTION })
    @RequestMapping(value = "/fInstitution/add/", method = RequestMethod.GET)
    public ModelAndView addFiniancialInstitutionHandler(ModelMap model)
			throws SessionExpiredException {
	model.put("fInstitution", new FinancialInstitution());
	return new ModelAndView("formFinancialInstitution", model);

    }

    @Secured({ PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
    @RequestMapping(value = "/fInstitution/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editFinancialInstitutionHandler(ModelMap model,
			@PathVariable("id") String fInstitutionId) throws SessionExpiredException {

	FinancialInstitution fInsitution = financialService
		.getFinancialInstitutionById(fInstitutionId);

	if (fInsitution != null) {
	    model.put("fInstitution", fInsitution);
	    return new ModelAndView("formFinancialInstitution", model);
	}

	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid Financial Insitution id submitted");
	return viewFiniancialInsititutionHandler(model);

    }

    @Secured({ PermissionConstants.DELETE_FINANCIAL_INSTITUTION })
    @RequestMapping(method = RequestMethod.POST, value = "/fInstitution/delete/")
    public void deleteFinancialInstitutionHandler(
			@RequestParam("selectedfInstitution") List<String> ids,
			HttpServletResponse response) {

	try {
	    if (ids != null) {
		String[] sPlaceIds = new String[ids.size()];
		sPlaceIds = ids.toArray(sPlaceIds);

		financialService.deleteFinancialInstitutionsByIds(sPlaceIds);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(
						"Financial Institution(s) deleted successfully");
	    } else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(
						"no Financial Institution(s) supplied for deleting");
	    }
	} catch (IOException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }


    @Secured({ PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
    @RequestMapping(method = RequestMethod.POST, value = "/fInstitution/save/")
    public ModelAndView saveSellingPlaceHandler(
			@ModelAttribute("fInstitution") FinancialInstitution fInstitution,
			ModelMap model) throws SessionExpiredException {

	FinancialInstitution existingFInstitution = fInstitution;

	if (StringUtils.isNotEmpty(fInstitution.getId())) {
	    existingFInstitution = financialService.getFinancialInstitutionById(fInstitution.getId());
	    existingFInstitution.setName(fInstitution.getName());
	    existingFInstitution.setAddress(fInstitution.getAddress());
	} else {
	    existingFInstitution.setId(null);
	}

	try {
	    financialService.validate(existingFInstitution);
	    financialService.save(existingFInstitution);
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Institution saved sucessfully.");
	} catch (ValidationException e) {
	    model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
	    model.put("fInsititution", fInstitution);
	    return new ModelAndView("formFinancialInstitution", model);
	}
	return viewFiniancialInsititutionHandler(model);
    }

}
