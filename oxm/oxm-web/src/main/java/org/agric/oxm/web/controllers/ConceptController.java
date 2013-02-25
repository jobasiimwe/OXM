package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ConceptSearchParameters;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ConceptService;
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
public class ConceptController {

    @Autowired
    private ConceptService conceptService;

    @RequestMapping(value = "/concept/view/{cid}", method = RequestMethod.GET)
    public ModelAndView viewConceptHandler(ModelMap model,
	    @PathVariable("cid") String conceptCategoryId) throws SessionExpiredException {
	List<Concept> concepts = conceptService.getConcepts();
	if (conceptCategoryId != null && !conceptCategoryId.equals("x")) {
	    ConceptCategory conceptCategory = conceptService
		    .getConceptCategoryById(conceptCategoryId);
	    concepts = conceptService.getConceptsByCategory(conceptCategory);
	    model.put("conceptCategory", conceptCategory);
	    model.put("concepts", concepts);
	} else {
	    model.put("concept", concepts);
	    model.put("concepts", concepts);
	    model.put("conceptCategory", new ConceptCategory());
	}

	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	prepareConceptFormModel(model);
	return new ModelAndView("viewConcept", model);
    }

    @RequestMapping(value = "/concept/view/{cid}", method = RequestMethod.POST)
    public ModelAndView viewConceptByCategoryHandler(ModelMap model,
	    @PathVariable("cid") String conceptCategoryId) throws SessionExpiredException {
	return viewConceptHandler(model, conceptCategoryId);
    }

    @RequestMapping(value = "/concept/add/", method = RequestMethod.GET)
    public ModelAndView addConceptHandler(ModelMap model) throws SessionExpiredException {
	prepareConceptFormModel(model);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	model.put("concept", new Concept());
	return new ModelAndView("addOREditConcept", model);
    }

    private void prepareConceptFormModel(ModelMap model) {
	List<ConceptCategory> conceptCategories = conceptService.getConceptCategories();
	model.put("conceptcategories", conceptCategories);
    }

    @RequestMapping(value = "/concept/save/", method = RequestMethod.POST)
    public ModelAndView saveConceptHandler(@ModelAttribute("concept") Concept concept,
	    ModelMap model) throws SessionExpiredException {

	Concept exitingConcept = concept;
	if (StringUtils.isNotEmpty(concept.getId())) {
	    exitingConcept = conceptService.getConceptById(concept.getId());
	    copyConceptContent(exitingConcept, concept);
	}
	try {
	    conceptService.validate(exitingConcept);
	    conceptService.save(exitingConcept);
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE, "concept saved sucessfully");
	} catch (ValidationException e) {
	    model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
	    prepareConceptFormModel(model);
	    WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	    model.put("concept", concept);
	    return new ModelAndView("addOREditConcept", model);
	}
	return viewConceptHandler(model, null);
    }

    private void copyConceptContent(Concept exitingConcept, Concept concept) {
	exitingConcept.setName(concept.getName());
	exitingConcept.setDescription(concept.getDescription());
	exitingConcept.setCategories(concept.getCategories());
    }

    @RequestMapping(value = "/concept/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editConcept(@PathVariable("id") String conceptId, ModelMap model)
	    throws SessionExpiredException {
	Concept concept = conceptService.getConceptById(conceptId);
	if (concept != null) {
	    prepareConceptFormModel(model);
	    WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	    model.put("concept", concept);
	    return new ModelAndView("addOREditConcept", model);
	}

	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Invalid concept ID submit");
	return viewConceptHandler(model, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/concept/delete/")
    public void deleteConceptHandler(@RequestParam("selectedConcepts") List<String> ids,
	    HttpServletResponse response) {

	try {
	    if (ids != null) {
		String[] concpetIds = new String[ids.size()];
		concpetIds = ids.toArray(concpetIds);

		conceptService.deleteConceptsByIds(concpetIds);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write("concept(s) deleted successfully");
	    } else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write("no concept(s) supplied for deleting");
	    }
	} catch (IOException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/concept/search/")
    public ModelAndView searchConcept(
	    @RequestParam(WebConstants.SEARCH_QUERY_REQUEST_PARAMETER_NAME) String query,
	    ModelMap model) {

	model.put("query", query);

	ConceptSearchParameters params = new ConceptSearchParameters(query);

	List<ConceptCategory> categories = conceptService.getConceptCategories();
	categories.add(0, new ConceptCategory());
	model.put("conceptcategories", categories);
	model.put("conceptCategory", new ConceptCategory());

	List<Concept> concepts = conceptService.getConceptsWithParams(params, 0);

	if (concepts != null && concepts.size() > 0) {

	    model.put("concepts", concepts);

	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE, "search for query >> \""
		    + query + "\" completed successfully with " + concepts.size() + " results");
	} else {
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "No Search found. with this search: " + query);
	}

	return new ModelAndView("viewConcept", model);
    }

    @RequestMapping(value = "/category/view/", method = RequestMethod.GET)
    public ModelAndView viewConceptCategoryHandler(ModelMap model) throws SessionExpiredException {
	List<ConceptCategory> conceptCategories = conceptService.getConceptCategories();
	model.put("conceptcategories", conceptCategories);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	return new ModelAndView("viewConceptCategory", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/add/")
    public ModelAndView viewAddConceptCategoryHandler(ModelMap model)
	    throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	model.put("conceptcategory", new ConceptCategory());
	return new ModelAndView("addOREditConceptCategory", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/edit/{id}")
    public ModelAndView viewEditConceptCategoryHandler(
	    @PathVariable("id") String conceptCategoryId, ModelMap model)
	    throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	ConceptCategory conceptCategory = conceptService.getConceptCategoryById(conceptCategoryId);
	if (conceptCategory != null) {
	    model.put("conceptcategory", conceptCategory);
	    return new ModelAndView("addOREditConceptCategory", model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the id of the concept category supplied doesn't match a concept category in the system");
	    return viewConceptCategoryHandler(model);
	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/category/save/")
    public ModelAndView saveConceptCategoryHandler(
	    @ModelAttribute("conceptcategory") ConceptCategory conceptCategory, ModelMap model)
	    throws SessionExpiredException {
	if (conceptCategory != null) {
	    try {

		ConceptCategory existingConceptCategory = conceptCategory;
		if (StringUtils.isNotEmpty(existingConceptCategory.getId())) {
		    existingConceptCategory = conceptService
			    .getConceptCategoryById(existingConceptCategory.getId());
		    copyExistingConceptCategory(existingConceptCategory, conceptCategory);
		}

		conceptService.validate(existingConceptCategory);
		conceptService.save(existingConceptCategory);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"concept category saved successfully");
	    } catch (ValidationException ex) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, ex.getMessage());
		model.put("conceptcategory", conceptCategory);
		return new ModelAndView("addOREditConceptCategory", model);
	    }
	}

	return viewConceptCategoryHandler(model);
    }

    private void copyExistingConceptCategory(ConceptCategory existingConceptCategory,
	    ConceptCategory conceptCategory) {
	existingConceptCategory.setDescription(conceptCategory.getDescription());
	existingConceptCategory.setName(conceptCategory.getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/category/delete/{id}")
    public ModelAndView deleteConceptCategoryHandler(
	    @PathVariable("id") String conceptCategoryId, ModelMap model)
	    throws SessionExpiredException {

	/*ConceptCategory cCategory = conceptService.getConceptCategoryById(conceptCategoryId);
	if (cCategory != null) {
	    try {
		String[] cCategoryIds = new String[1];
		cCategoryIds[0] = conceptCategoryId;
		conceptService.deleteConceptsByIds(cCategoryIds);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"concept category saved sucessfully");
	    } catch (ValidationException e) {
		
	    }
	}*/

	return viewConceptCategoryHandler(model);

    }
}
