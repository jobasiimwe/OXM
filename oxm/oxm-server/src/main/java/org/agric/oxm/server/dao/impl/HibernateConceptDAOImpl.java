package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Concept;
import org.agric.oxm.server.dao.ConceptDAO;
import org.springframework.stereotype.Repository;

/**
 * Implements the {@link ConceptDAO} interface to provide CRUD operations for
 * the {@link Concept} entities using hibernate as the ORM strategy
 * 
 * @author Job
 * 
 */
@Repository("ConceptDAO")
public class HibernateConceptDAOImpl extends BaseDAOImpl<Concept> implements
		ConceptDAO {

	@Override
	public boolean exists(String name) {
		String queryStr = "select id from Concept c where c.name = :name";
		try {
			Object result = entityManager.createQuery(queryStr)
					.setParameter("name", name).getSingleResult();
			if (result != null)
				return true;
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	@Override
	public String getConceptIdByName(String name) {
		String queryStr = "select id from Concept c where c.name = :name";
		try {
			return (String) entityManager.createQuery(queryStr)
					.setParameter("name", name).getSingleResult();
		} catch (Exception ex) {
			return "";
		}
	}

}
