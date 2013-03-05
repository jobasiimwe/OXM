package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.SingleStringSearchParameters;
import org.agric.oxm.model.search.ConceptSearchParameters;
import org.agric.oxm.server.ConceptCategoryAnnotation;

/**
 * 
 * A service interface for handling concepts
 * 
 * @author Job
 * 
 */
public interface ConceptService {

	void save(Concept concept);

	void validate(Concept concept) throws ValidationException;

	void deleteConceptsByIds(String[] ids);

	Concept getConceptById(String id);

	List<Concept> getConcepts();

	List<Concept> getConceptsByCategory(ConceptCategory conceptCategory);

	List<Concept> getConceptsByCategories(
			List<ConceptCategory> conceptCategories);

	List<Concept> getConceptsByCategoryAnnotation(
			ConceptCategoryAnnotation conceptCategoryAnnotation)
			throws SecurityException, NoSuchFieldException;

	List<Concept> getConceptsWithParams(
			ConceptSearchParameters conceptSearchParameters, int pageNo);

	int getNumberOfConceptsInSearch(
			ConceptSearchParameters conceptSearchParameters);

	// =========================

	void save(ConceptCategory conceptCategory);

	void validate(ConceptCategory conceptCategory) throws ValidationException;

	ConceptCategory getConceptCategoryById(String id);

	List<ConceptCategory> getConceptCategories();

	List<ConceptCategory> getConceptCategoriesWithParams(
			SingleStringSearchParameters params, int pageNo);

	int getNumberOfConceptsCategoriesInSearch(
			SingleStringSearchParameters params);

}
