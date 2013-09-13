package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.FinancialInstitutionService;
import org.agric.oxm.web.WebConstants;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("finstitution")
public class FinancialInstitutionController {

	@Autowired
	private FinancialInstitutionService financialService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap) {
		List<FinancialInstitution> fInstitution = financialService
				.getFinancialInstitutions();
		modelMap.put("fInstitutions", fInstitution);
		return new ModelAndView("viewFinancialInstitution", modelMap);

	}

	@Secured({ PermissionConstants.ADD_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) throws SessionExpiredException {
		modelMap.put("fInstitution", new FinancialInstitution());
		return new ModelAndView("formFinancialInstitution", modelMap);

	}

	@Secured({ PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(ModelMap modelMap,
			@PathVariable("id") String fInstitutionId) {

		FinancialInstitution fInsitution = financialService
				.getFinancialInstitutionById(fInstitutionId);

		if (fInsitution != null) {
			modelMap.put("fInstitution", fInsitution);
			return new ModelAndView("formFinancialInstitution", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid Financial Insitution id submitted");
		return view(modelMap);

	}

	@Secured({ PermissionConstants.DELETE_FINANCIAL_INSTITUTION })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{ids}")
	public ModelAndView delete(@PathVariable("ids") String fInstitutionIds,
			ModelMap modelMapMap) {

		String[] fInstitutionIdzToDelete = fInstitutionIds.split(",");
		try {
			financialService
					.deleteFinancialInstitutionsByIds(fInstitutionIdzToDelete);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Institution(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
		}

		return view(modelMapMap);

	}

	@Secured({ PermissionConstants.ADD_FINANCIAL_INSTITUTION,
			PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(
			@ModelAttribute("fInstitution") FinancialInstitution fInstitution,
			ModelMap modelMap) {

		try {

			financialService.validate(fInstitution);

			FinancialInstitution existingFInstitution = fInstitution;

			if (StringUtils.isNotEmpty(fInstitution.getId())) {
				existingFInstitution = financialService
						.getFinancialInstitutionById(fInstitution.getId());
				existingFInstitution.setName(fInstitution.getName());
				existingFInstitution.setDescription(fInstitution
						.getDescription());
				existingFInstitution.setAddress(fInstitution.getAddress());
			} else {
				existingFInstitution.setId(null);
			}

			financialService.save(existingFInstitution);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Institution saved sucessfully.");
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("fInsititution", fInstitution);
			return new ModelAndView("formFinancialInstitution", modelMap);
		}
		return view(modelMap);
	}

}
