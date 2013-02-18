package org.agric.oxm.server.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.agric.oxm.model.BaseData;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.server.dao.BaseDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.genericdao.dao.jpa.GenericDAO;
import com.googlecode.genericdao.dao.jpa.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.jpa.JPASearchProcessor;

/**
 * Abstract Base class for all Data Access interface Implementations and
 * abstracts<br/>
 * </br/>
 * 
 * This class requires the hibernate {@link SessionFactory} <br/>
 * <br/>
 * 
 * This class also extends the {@link GenericDAOImpl} which is a hibernate
 * specific implementation of the {@link GenericDAO} interface. This allows our
 * implementing classes to use generics when providing CRUD operations
 * 
 * 
 * @author ctumwebaze
 * @param <T>
 */
public abstract class BaseDAOImpl<T> extends GenericDAOImpl<T, String>
		implements BaseDAO<T> {

	protected EntityManager entityManager;

	@PersistenceContext
	@Override
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
		this.entityManager = entityManager;
	}

	@Autowired
	@Override
	public void setSearchProcessor(JPASearchProcessor searchProcessor) {
		super.setSearchProcessor(searchProcessor);
	}

	@Override
	public List<T> searchByPropertyEqual(String property, Object value) {
		Search search = new Search();
		search.addFilterEqual(property, value);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return search(search);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T searchUniqueByPropertyEqual(String property, Object value) {
		Search search = new Search();
		search.addFilterEqual(property, value);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return (T) searchUnique(search);
	}

	@Override
	public void delete(T entity) {
		if (entity != null) {
			if (entity instanceof BaseData) {
				((BaseData) entity).setRecordStatus(RecordStatus.DELETED);
				super.save(entity);
			}
		}
	}

	@Override
	public void update(T entity) {
		super.save(entity);
	}

	@Override
	public void add(T entity) {
		super.save(entity);
	}

	@Override
	public List<T> searchByPropertyEqual(String property, Object value,
			RecordStatus recordStatus) {
		Search search = new Search();
		search.addFilterEqual(property, value);
		search.addFilterEqual("recordStatus", recordStatus);
		return search(search);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T searchUniqueByPropertyEqual(String property, Object value,
			RecordStatus recordStatus) {
		Search search = new Search();
		search.addFilterEqual(property, value);
		search.addFilterEqual("recordStatus", recordStatus);
		return (T) searchUnique(search);
	}

	@Override
	public List<T> searchByRecordStatus(RecordStatus recordStatus) {
		Search search = new Search();
		search.addFilterEqual("recordStatus", recordStatus);
		return search(search);
	}

	@Override
	public T save(T entity) {
		if (entity == null)
			return null;

		if (entity instanceof BaseData) {
			if (StringUtils.isBlank(((BaseData) entity).getId())) {
				((BaseData) entity).setId(null);
			}
		}

		return super.save(entity);
	}
}
