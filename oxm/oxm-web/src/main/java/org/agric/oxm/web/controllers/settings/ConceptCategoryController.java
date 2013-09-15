package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ConceptService;
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
@RequestMapping("category")
public class ConceptCategoryController {

	@Autowired
	private ConceptService conceptService;

	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView viewConceptCategoryHandler(ModelMap modelMap)
			throws SessionExpiredException {
		List<ConceptCategory> conceptCategories = conceptService
				.getConceptCategories();
		modelMap.put("conceptcategories", conceptCategories);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		return new ModelAndView("viewConceptCategory", modelMap);
	}

	@RequestMapping(method = RequestMethod.GET, value = "add")
	public ModelAndView addConceptCategoryHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("conceptcategory", new ConceptCategory());
		return new ModelAndView("formConceptCategory", modelMap);
	}

	@Secured({ PermissionConstants.EDIT_CONCEPT_DETAILS })
	@RequestMapping(method = RequestMethod.GET, value = "edit/{id}")
	public ModelAndView editConceptCategoryHandler(
			@PathVariable("id") String conceptCategoryId, ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		ConceptCategory conceptCategory = conceptService
				.getConceptCategoryById(conceptCategoryId);
		if (conceptCategory != null) {
			modelMap.put("conceptcategory", conceptCategory);
			return new ModelAndView("formConceptCategory", modelMap);
		} else {
			modelMap.put(
					WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"the id of the concept category supplied doesn't match a concept category in the system");
			return viewConceptCategoryHandler(modelMap);
		}
	}

	@Secured({ PermissionConstants.ADD_CONCEPT_DETAILS,
			PermissionConstants.EDIT_CONCEPT_DETAILS })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView saveConceptCategoryHandler(
			@ModelAttribute("conceptcategory") ConceptCategory conceptCategory,
			ModelMap modelMap) throws SessionExpiredException {
		if (conceptCategory != null) {
			try {

				ConceptCategory existingConceptCategory = conceptCategory;
				if (StringUtils.isNotEmpty(existingConceptCategory.getId())) {
					existingConceptCategory = conceptService
							.getConceptCategoryById(existingConceptCategory
									.getId());
					copyExistingConceptCategory(existingConceptCategory,
							conceptCategory);
				} else {
					existingConceptCategory.setId(null);
				}

				conceptService.validate(existingConceptCategory);
				conceptService.save(existingConceptCategory);
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
						"concept category saved successfully");
			} catch (ValidationException ex) {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						ex.getMessage());
				modelMap.put("conceptcategory", conceptCategory);
				return new ModelAndView("formConceptCategory", modelMap);
			}
		}

		return viewConceptCategoryHandler(modelMap);
	}

	private void copyExistingConceptCategory(
			ConceptCategory existingConceptCategory,
			ConceptCategory conceptCategory) {
		existingConceptCategory
				.setDescription(conceptCategory.getDescription());
		existingConceptCategory.setName(conceptCategory.getName());
	}

	@Secured({ PermissionConstants.DELETE_CONCEPT_DETAILS })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{id}")
	public ModelAndView deleteConceptCategoryHandler(
			@PathVariable("id") String conceptCategoryId, ModelMap modelMap)
			throws SessionExpiredException {

		/*
		 * ConceptCategory cCategory =
		 * conceptService.getConceptCategoryById(conceptCategoryId); if
		 * (cCategory != null) { try { String[] cCategoryIds = new String[1];
		 * cCategoryIds[0] = conceptCategoryId;
		 * conceptService.deleteConceptsByIds(cCategoryIds);
		 * modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		 * "concept category saved sucessfully"); } catch (ValidationException
		 * e) {
		 * 
		 * } }
		 */

		return viewConceptCategoryHandler(modelMap);

	}
}
