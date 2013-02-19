package org.agric.oxm.server.dao.hibernameimpl;


import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.server.dao.ConceptCategoryDAO;
import org.springframework.stereotype.Repository;

/**
 * Implements the {@link ConceptCategoryDAO} interface to provide CRUD
 * operations for the {@link ConceptCategory} entities using hibernate as the
 * ORM strategy
 * 
 * @author Job
 * 
 */
@Repository("conceptCategoryDAO")
public class HibernateConceptCategoryDAOImpl extends BaseDAOImpl<ConceptCategory> implements
	ConceptCategoryDAO {

}
