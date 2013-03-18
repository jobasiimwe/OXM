package org.agric.oxm.server.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.SingleStringSearchParameters;
import org.agric.oxm.model.search.ConceptSearchParameters;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.ConceptCategoryDAO;
import org.agric.oxm.server.dao.ConceptDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * implementation of {@link ConceptService}
 * 
 * @author Job
 * 
 */
@Service("conceptService")
@Transactional
public class ConceptServiceImpl implements ConceptService {

	@Autowired
	private ConceptDAO conceptDAO;

	@Autowired
	private ConceptCategoryDAO conceptCategoryDAO;

	@Override
	@Secured({ PermissionConstants.ADD_CONCEPT_DETAILS,
			PermissionConstants.EDIT_CONCEPT_DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Concept concept) {
		conceptDAO.save(concept);
	}

	@Secured({ PermissionConstants.ADD_CONCEPT_DETAILS,
			PermissionConstants.EDIT_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Concept concept) throws ValidationException {
		Concept existingConcept = conceptDAO.searchUniqueByPropertyEqual(
				"name", concept.getName());
		if (null != existingConcept && StringUtils.isEmpty(concept.getId())) {
			throw new ValidationException("Another concept with name "
					+ concept.getName() + " already exists");
		}
	}

	@Secured({ PermissionConstants.DELETE_CONCEPT_DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteConceptsByIds(String[] ids) {
		conceptDAO.removeByIds(ids);
	}

	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Concept getConceptById(String id) {
		return conceptDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Concept> getConcepts() {
		return conceptDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Concept> getConcepts(Integer pageNo) {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Concept> concepts = conceptDAO.search(search);
		return concepts;
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Concept> getConceptsByCategory(ConceptCategory conceptCategory) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		search.addFilter(Filter.some("categories",
				Filter.equal("id", conceptCategory.getId())));
		List<Concept> concepts = conceptDAO.search(search);
		Collections.sort(concepts);
		return concepts;
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Concept> getConceptsByCategories(
			List<ConceptCategory> conceptCategories) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		List<String> ids = new ArrayList<String>();

		for (ConceptCategory cc : conceptCategories)
			if (!ids.contains(cc.getId()))
				ids.add(cc.getId());

		search.addFilter(Filter.some("categories", Filter.in("id", ids)));
		List<Concept> concepts = conceptDAO.search(search);
		Collections.sort(concepts);
		return concepts;
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Concept> getConceptsByCategoryAnnotation(
			ConceptCategoryAnnotation conceptCategoryAnnotation)
			throws SecurityException, NoSuchFieldException {
		ConceptCategory conceptCategory = getConceptCategoryById(conceptCategoryAnnotation
				.id());
		List<Concept> concepts = getConceptsByCategory(conceptCategory);
		return concepts;
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Concept> searchWithParams(ConceptSearchParameters params,
			int pageNo) {
		Search search = prepareConceptSearch(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Concept> concepts = conceptDAO.search(search);
		return concepts;
	}

	private Search prepareConceptSearch(ConceptSearchParameters params) {
		Search search = new Search();

		search.addSort("name", false, true);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		if (!StringUtils.isEmpty(params.getName())) {
			Filter nameFilter = new Filter("name",
					"%" + params.getName() + "%", Filter.OP_LIKE);
			Filter descFilter = new Filter("description", "%"
					+ params.getName() + "%", Filter.OP_ILIKE);
			search.addFilterOr(nameFilter, descFilter);
			// search.addFilterLike("name", params.getName());
		}

		if (null != params.getConceptCategory())
			search.addFilter(Filter.some("categories",
					Filter.equal("id", params.getConceptCategory().getId())));

		return search;
	}

	@Override
	public int numberOfConceptsWithSearchParams(ConceptSearchParameters params) {
		Search search = prepareConceptSearch(params);
		return conceptDAO.count(search);
	}

	@Override
	@Secured({ PermissionConstants.ADD_CONCEPT_DETAILS,
			PermissionConstants.EDIT_CONCEPT_DETAILS })
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ConceptCategory conceptCategory) {
		conceptCategoryDAO.save(conceptCategory);
	}

	@Override
	public void validate(ConceptCategory conceptCategory)
			throws ValidationException {
		ConceptCategory existingConceptCategory = conceptCategoryDAO
				.searchUniqueByPropertyEqual("name", conceptCategory.getName());
		if (null != existingConceptCategory
				&& StringUtils.isEmpty(conceptCategory.getId())) {
			throw new ValidationException("Another concept category with name "
					+ existingConceptCategory.getName() + " already exists");
		}
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ConceptCategory getConceptCategoryById(String id) {
		return conceptCategoryDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ConceptCategory> getConceptCategories() {
		return conceptCategoryDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<ConceptCategory> getConceptCategoriesWithParams(
			SingleStringSearchParameters params, int pageNo) {
		Search search = prepareConceptCategorySearch(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}

		List<ConceptCategory> conceptCategories = conceptCategoryDAO
				.search(search);
		return conceptCategories;
	}

	private Search prepareConceptCategorySearch(
			SingleStringSearchParameters params) {
		Search search = new Search();

		search.addSort("name", false, true);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		if (!StringUtils.isNotEmpty(params.getName())) {
			Filter nameFilter = new Filter("name",
					"%" + params.getName() + "%", Filter.OP_ILIKE);
			Filter descFilter = new Filter("description", "%"
					+ params.getName() + "%", Filter.OP_ILIKE);
			search.addFilterOr(nameFilter, descFilter);

		}

		return search;
	}

	@Override
	@Secured({ PermissionConstants.VIEW_CONCEPT_DETAILS })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int getNumberOfConceptsCategoriesInSearch(
			SingleStringSearchParameters params) {
		Search search = prepareConceptCategorySearch(params);
		return conceptCategoryDAO.count(search);
	}

}
