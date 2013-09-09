package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.exception.ValidationException;


public interface BaseService<ModelObject> {

	void save(ModelObject t);

	void validate(ModelObject t) throws ValidationException;

	ModelObject getById(String id);

	List<ModelObject> getAll();

	void deleteByIds(String[] ids);
}
