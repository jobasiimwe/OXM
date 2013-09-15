package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ConceptSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ConceptService;
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
@RequestMapping("concept")
public class ConceptController {

	@Autowired
	private ConceptService conceptService;

	public static final String PARAM_NAME = "query";
	public static final String PARAM_CATEGORY = "categoryid";
	private static final String COMMAND_NAME = "conceptsearch";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView conceptSearchHandler(
			@ModelAttribute("conceptsearch") GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		ConceptSearchParameters params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareConceptSearchModel(params, pageNo, model);

		return new ModelAndView("viewConcept", model);
	}

	/**
	 * prepares the concept model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param modelMap
	 */
	private void prepareConceptSearchModel(ConceptSearchParameters params,
			Integer pageNo, ModelMap modelMap) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Concept> concepts = conceptService
				.searchWithParams(params, pageNo);
		modelMap.put("concepts", concepts);
		modelMap.put(
				WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
				String.format("search completed with: %s result(s)",
						String.valueOf(concepts.size())));

		WebUtils.prepareNavigation("Concepts",
				conceptService.numberOfConceptsWithSearchParams(params),
				concepts.size(), pageNo, buildSearchNavigationUrl(params),
				modelMap);

		prepareSearchCommand(modelMap, params);

		prepareConceptFormModel(modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"System Terms - search results ");

		// set a variable searching to true
		// this variable is used in determining what navigation file to use
		modelMap.put("searching", true);
	}

	private void prepareSearchCommand(ModelMap modelMap,
			ConceptSearchParameters params) {

		GenericCommand searchCommand = new GenericCommand();

		searchCommand.getPropertiesMap().put(PARAM_NAME,
				new GenericCommandValue(params.getName()));

		if (params.getConceptCategory() != null) {
			searchCommand.getPropertiesMap()
					.put(PARAM_CATEGORY,
							new GenericCommandValue(params.getConceptCategory()
									.getId()));
		}

		modelMap.put(ConceptController.COMMAND_NAME, searchCommand);

		prepareConceptFormModel(modelMap);
	}

	private ConceptSearchParameters extractParams(GenericCommand searchCommand) {
		ConceptSearchParameters params = new ConceptSearchParameters();
		if (StringUtils.isNotBlank(searchCommand.getValue(PARAM_NAME))) {
			params.setName(searchCommand.getValue(PARAM_NAME));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(PARAM_CATEGORY))) {
			params.setConceptCategory(conceptService
					.getConceptCategoryById(searchCommand
							.getValue(PARAM_CATEGORY)));
		}

		return params;
	}

	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView searchNavigate(
			@RequestParam(value = PARAM_NAME, required = false) String name,
			@RequestParam(value = PARAM_CATEGORY, required = false) String categoryId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(PARAM_NAME,
				new GenericCommandValue(name));
		command.getPropertiesMap().put(PARAM_CATEGORY,
				new GenericCommandValue(categoryId));

		return conceptSearchHandler(command, pageNo, modelMap);
	}

	/**
	 * builds a search navigation url based on the given concept search
	 * parameter object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildSearchNavigationUrl(ConceptSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/concept?action=search");

		if (StringUtils.isNotBlank(params.getName())) {
			buffer.append("&").append(PARAM_NAME).append("=")
					.append(params.getName());
		}

		if (params.getConceptCategory() != null) {
			buffer.append("&").append(PARAM_CATEGORY).append("=")
					.append(params.getConceptCategory().getId());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap) throws SessionExpiredException {
		prepareConceptSearchModel(new ConceptSearchParameters(), 1, modelMap);
		prepareConceptFormModel(modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, "System Terms (Concepts)");
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		return new ModelAndView("viewConcept", modelMap);
	}

	@Secured({ PermissionConstants.ADD_CONCEPT_DETAILS })
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) throws SessionExpiredException {
		prepareConceptFormModel(modelMap);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("concept", new Concept());
		modelMap.put(WebConstants.CONTENT_HEADER, "Add System Term (Concept)");
		return new ModelAndView("formConcept", modelMap);
	}

	private void prepareConceptFormModel(ModelMap modelMap) {
		List<ConceptCategory> conceptCategories = conceptService
				.getConceptCategories();
		modelMap.put("conceptcategories", conceptCategories);
	}

	@Secured({ PermissionConstants.ADD_CONCEPT_DETAILS,
			PermissionConstants.EDIT_CONCEPT_DETAILS })
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("concept") Concept concept,
			ModelMap modelMap) throws SessionExpiredException {

		Concept exitingConcept = concept;
		if (StringUtils.isNotEmpty(concept.getId())) {
			exitingConcept = conceptService.getConceptById(concept.getId());
			copyConceptContent(exitingConcept, concept);
		} else {
			exitingConcept.setId(null);
		}
		try {
			conceptService.validate(exitingConcept);
			conceptService.save(exitingConcept);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"concept saved sucessfully");
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			prepareConceptFormModel(modelMap);
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);
			modelMap.put("concept", concept);
			return new ModelAndView("formConcept", modelMap);
		}
		return view(modelMap);
	}

	private void copyConceptContent(Concept exitingConcept, Concept concept) {
		exitingConcept.setName(concept.getName());
		exitingConcept.setDescription(concept.getDescription());
		exitingConcept.setCategories(concept.getCategories());
	}

	@Secured({ PermissionConstants.EDIT_CONCEPT_DETAILS })
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView editConcept(@PathVariable("id") String conceptId,
			ModelMap modelMap) throws SessionExpiredException {
		Concept concept = conceptService.getConceptById(conceptId);
		if (concept != null) {
			prepareConceptFormModel(modelMap);
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), modelMap);
			modelMap.put("concept", concept);

			modelMap.put(WebConstants.CONTENT_HEADER, "Edit System Term - "
					+ concept.getName());

			return new ModelAndView("formConcept", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid concept ID submited");
		return view(modelMap);
	}

	@Secured({ PermissionConstants.DELETE_CONCEPT_DETAILS })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{ids}")
	public ModelAndView delete(@PathVariable("ids") String ids,
			ModelMap modelMapMap) throws SessionExpiredException {

		String[] idsToDelete = ids.split(",");
		try {
			conceptService.deleteConceptsByIds(idsToDelete);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Concepts  deleted successfully");

		} catch (Exception e) {
			log.error("Error", e);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
		}
		return view(modelMapMap);
	}

}
