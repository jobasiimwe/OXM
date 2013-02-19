package org.agric.oxm.server.dao;

import java.util.List;

import org.agric.oxm.model.RecordStatus;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

/**
 * A Base Interface for all Data Access interfaces that enables the Data Access
 * Layer objects to use generics <br/>
 * <br/>
 * 
 * The BaseDAO interface extends the {@link GenericDAO} interface to provide the
 * generalization of the Data Access objects. <br/>
 * <br/>
 * 
 * Usage of this interface can be seen below <br/>
 * <code>
 * 	public interface UserDAO extends BaseDAO{@code <User>}
 * </code>
 * 
 * @author Jmpango
 * @param <T>
 */
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
    List<T> searchByPropertyEqual(String property, Object value, RecordStatus recordStatus);

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
    T searchUniqueByPropertyEqual(String property, Object value, RecordStatus recordStatus);

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

    T searchUniqueByPropertyEqual(String property, String property2, String property3,
	    Object value, Object value2, Object value3, RecordStatus recordStatus);

}
