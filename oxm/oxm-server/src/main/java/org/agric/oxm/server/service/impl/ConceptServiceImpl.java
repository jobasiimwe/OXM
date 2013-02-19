package org.agric.oxm.server.service.impl;

import java.util.Collections;
import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ConceptCategorySearchParameters;
import org.agric.oxm.model.search.ConceptSearchParameters;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.ConceptCategoryDAO;
import org.agric.oxm.server.dao.ConceptDAO;
import org.agric.oxm.server.service.ConceptService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

public class ConceptServiceImpl implements ConceptService {

	@Autowired
	private ConceptDAO conceptDAO;

	@Autowired
	private ConceptCategoryDAO conceptCategoryDAO;

	@Override
	public void save(Concept concept) {
		conceptDAO.save(concept);
	}

	@Override
	public void validate(Concept concept) throws ValidationException {
		ConceptSearchParameters params = new ConceptSearchParameters(
				concept.getName());
		List<Concept> concepts = getConceptsWithParams(params, 0);

		if (null != concepts) {
			if (StringUtils.isBlank(concept.getId()))
				throw new ValidationException("Another concept with name "
						+ concept.getName() + " already exists");

			if (concepts.size() > 1
					|| (concepts.size() == 1 && !concepts.get(0)
							.equals(concept)))
				throw new ValidationException("Another concept with name "
						+ concept.getName() + " already exists");
		}
	}

	@Override
	public void deleteConceptsByIds(String[] ids) {
		conceptDAO.removeByIds(ids);
	}

	@Override
	public Concept getConceptById(String id) {
		return conceptDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	public List<Concept> getConcepts() {
		return conceptDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Override
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
	public List<Concept> getConceptsByCategoryAnnotation(
			ConceptCategoryAnnotation conceptCategoryAnnotation)
			throws SecurityException, NoSuchFieldException {
		ConceptCategory conceptCategory = getConceptCategoryById(conceptCategoryAnnotation
				.id());
		List<Concept> concepts = getConceptsByCategory(conceptCategory);
		return concepts;
	}

	@Override
	public List<Concept> getConceptsWithParams(ConceptSearchParameters params,
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
		if (!StringUtils.isNotEmpty(params.getName())) {
			Filter nameFilter = new Filter("name",
					"%" + params.getName() + "%", Filter.OP_ILIKE);
			Filter descFilter = new Filter("description", "%"
					+ params.getName() + "%", Filter.OP_ILIKE);
			search.addFilterOr(nameFilter, descFilter);

		}

		if (null != params.getConceptCategory())
			search.addFilter(Filter.some("categories",
					Filter.equal("id", params.getConceptCategory().getId())));

		return search;
	}

	@Override
	public int getNumberOfConceptsInSearch(ConceptSearchParameters params) {
		Search search = prepareConceptSearch(params);
		return conceptDAO.count(search);
	}

	@Override
	public void save(ConceptCategory conceptCategory) {
		conceptCategoryDAO.save(conceptCategory);
	}

	@Override
	public void validate(ConceptCategory conceptCategory)
			throws ValidationException {
		ConceptCategorySearchParameters params = new ConceptCategorySearchParameters(
				conceptCategory.getName());
		List<ConceptCategory> conceptCategories = getConceptCategoriesWithParams(
				params, 0);

		if (null != conceptCategories) {
			if (StringUtils.isBlank(conceptCategory.getId()))
				throw new ValidationException(
						"Another concept category with name "
								+ conceptCategory.getName() + " already exists");

			if (conceptCategories.size() > 1
					|| (conceptCategories.size() == 1 && !conceptCategories
							.get(0).equals(conceptCategory)))
				throw new ValidationException(
						"Another concept category with name "
								+ conceptCategory.getName() + " already exists");
		}

	}

	@Override
	public ConceptCategory getConceptCategoryById(String id) {
		return conceptCategoryDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	public List<ConceptCategory> getConceptCategories() {
		return conceptCategoryDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Override
	public List<ConceptCategory> getConceptCategoriesWithParams(
			ConceptCategorySearchParameters params, int pageNo) {
		Search search = prepareConceptCategorySearch(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
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
			ConceptCategorySearchParameters params) {
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
	public int getNumberOfConceptsCategoriesInSearch(
			ConceptCategorySearchParameters params) {
		Search search = prepareConceptCategorySearch(params);
		return conceptCategoryDAO.count(search);
	}

}
