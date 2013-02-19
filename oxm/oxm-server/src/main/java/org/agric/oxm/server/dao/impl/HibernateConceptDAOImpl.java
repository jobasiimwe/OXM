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


}
