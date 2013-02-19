package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.server.ConceptCategoryAnnotation;

/**
 * 
 * A service interface for handling concepts
 * 
 * @author Job
 * 
 */
public interface ConceptService {

	/**
	 * saves a given concept into the system
	 * 
	 * @param concept
	 * @return
	 * @throws MohrValidationException
	 *             the exception to be thrown
	 */
	Concept saveConcept(Concept concept);
//			throws RmsValidationException;

	/**
	 * deletes a given concept from the system. This just marks the concept in
	 * the system as deleted but doesn't remove it from the system
	 * 
	 * @param concept
	 */
	void deleteConcept(Concept concept);

	/**
	 * purges a given concept from the system. This completely removes the
	 * concept from the system
	 * 
	 * @param concept
	 */
	void purgeConcept(Concept concept);

	/**
	 * gets a concept with the given id
	 * 
	 * @param id
	 * @return
	 */
	Concept getConceptById(String id);

	/**
	 * gets a concept with the given code
	 * 
	 * @param code
	 * @return
	 */
	Concept getConceptByCode(String code);

	/**
	 * gets a list of concepts in a particular category
	 * 
	 * @param conceptCategory
	 * @return
	 */
	List<Concept> getConceptsByCategory(ConceptCategory conceptCategory);

	/**
	 * gets a list of concepts in a particular category
	 * 
	 * @param conceptCategoryAnnotation
	 * @return List of Concepts
	 */
	List<Concept> getConceptsByCategoryAnnotation(
			ConceptCategoryAnnotation conceptCategoryAnnotation)
			throws SecurityException, NoSuchFieldException;

	/**
	 * gets a list of concepts in a particular category
	 * 
	 * @param conceptCategory
	 * @param pageNo
	 * @return the list of concepts
	 */
	List<Concept> getConceptsByCategory(ConceptCategory conceptCategory,
			int pageNo);

	/**
	 * gets a list of all concepts in the system
	 * 
	 * @return
	 */
	List<Concept> getAllConcepts();

	/**
	 * saves a given concept category into the system
	 * 
	 * @param conceptCategory
	 * @return the ConceptCategory
	 * @throws MohrValidationException
	 *             the exception to be thrown
	 */
	ConceptCategory saveConceptCategory(ConceptCategory conceptCategory);
//			throws RmsValidationException;

	/**
	 * deletes a given concept category from the system. This doesn't remove the
	 * concept category from the system. it just marks it as deleted but it
	 * stays in the system for historical purposes.
	 * 
	 * @param conceptCategory
	 */
	void deleteConceptCategory(ConceptCategory conceptCategory);

	/**
	 * purge a given concept category from the system. This completely removes
	 * the concept category from the system entirely
	 * 
	 * @param conceptCategory
	 */
	void purgeConceptCategory(ConceptCategory conceptCategory);

	/**
	 * gets a concept category with the given id
	 * 
	 * @param id
	 * @return
	 */
	ConceptCategory getConceptCategoryById(String id);

	/**
	 * gets a concept category with the given code
	 * 
	 * @param code
	 * @return
	 */
	ConceptCategory getConceptCategoryByCode(String code);

	/**
	 * gets a list of all concept categories
	 * 
	 * @return
	 */
	List<ConceptCategory> getAllConceptCategories();

	/**
	 * gets a list of concepts in a given page
	 * 
	 * @param pageNo
	 * @return
	 */
	List<Concept> getConceptByPage(Integer pageNo);

	/**
	 * gets the total number of concepts in the system
	 * 
	 * @return
	 */
	int getNumberOfConcepts();

	/**
	 * gets a list of concept categories in the system for a given page
	 * 
	 * @param pageNo
	 * @return
	 */
	List<ConceptCategory> getConceptCategoryByPage(Integer pageNo);

	/**
	 * gets the total number of concept categories in the system
	 * 
	 * @return
	 */
	int getNumberOfConceptCategories();

	/**
	 * gets list of concept categories that contain a given query in their name
	 * 
	 * @param query
	 * @return
	 */
	List<ConceptCategory> searchConceptCategoriesByName(String query);

	/**
	 * gets a list of concepts that contain a given query in their name
	 * 
	 * @param query
	 * @return
	 */
	List<Concept> searchConceptByName(String query);

	/**
	 * 
	 * @param query
	 * @return
	 */
	List<Concept> searchConceptByName(String query, Integer pageNo);

	/**
	 * gets the total number of concepts that meet a search criteria
	 * 
	 * @return
	 */
	int getTotalNumberOfConceptsInSearch(String query, Integer pageNumber);

	/**
	 * deletes concepts whose ids are in the given list
	 * 
	 * @param conceptIds
	 */
	void deleteConcepts(List<String> conceptIds);

	/**
	 * gets the number of concepts in a given category
	 * 
	 * @return
	 */
	int getNumberOfConceptsByCategory(ConceptCategory conceptCategory);
	
	
	

}
