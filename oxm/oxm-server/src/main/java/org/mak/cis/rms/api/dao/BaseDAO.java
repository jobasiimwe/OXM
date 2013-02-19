package org.mak.cis.rms.api.dao;

import java.util.List;

import org.agric.oxm.model.RecordStatus;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface BaseDAO<T> extends GenericDAO<T, String> {
	/**
	 * retrieves a list of active entities(whose record status is active) with a
	 * given property value is equal to the given value
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	List<T> searchByPropertyEqual(String property, Object value);

	/**
	 * retrieves a of type T active entities (whose record status is active)
	 * with a given property whose value is equal to the given value
	 */
	T searchUniqueByPropertyEqual(String property, Object value);

	/**
	 * searches for a list of entities of a given record status with a given
	 * property whose value is equal to the given value
	 * 
	 * @param property
	 * @param value
	 * @param recordStatus
	 * @return
	 */
	List<T> searchByPropertyEqual(String property, Object value,
			RecordStatus recordStatus);

	/**
	 * searches for a list of all entities of a given record status
	 * 
	 * @param recordStatus
	 * @return
	 */
	List<T> searchByRecordStatus(RecordStatus recordStatus);

	/**
	 * searches for an entity of a given record status with a given property
	 * whose value is equla to the given value
	 * 
	 * @param property
	 * @param value
	 * @param recordStatus
	 * @return
	 */
	T searchUniqueByPropertyEqual(String property, Object value,
			RecordStatus recordStatus);

	/**
	 * deletes a given entity. This doesn't remove the entity entirely from the
	 * system, but marks the record as deleted and leave it intact for
	 * historical purposes
	 * 
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * updates a given entity
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * adds a given entity
	 * 
	 * @param entity
	 */
	void add(T entity);
	
	//void save(T entity, String prefferedId);
}
