package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.exception.ValidationException;


public interface BaseServiceWithSearch<ModelObject, SearchParams> extends BaseService<ModelObject> {

	void save(ModelObject t);

	void validate(ModelObject t) throws ValidationException;

	ModelObject getById(String id);

	List<ModelObject> getAll();

	ModelObject searchUniqueWithParams(SearchParams params) throws Exception;

	List<ModelObject> searchWithParams(SearchParams params);

	List<ModelObject> searchWithParams(SearchParams params, Integer pageNo);

	long numberInSearch(SearchParams params);

}
