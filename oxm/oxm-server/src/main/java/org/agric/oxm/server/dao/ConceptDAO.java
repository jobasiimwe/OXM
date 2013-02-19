package org.agric.oxm.server.dao;

import org.agric.oxm.model.Concept;


/**
 * 
 * A Data access interface that provides methods for performing CRUD operations
 * on the {@link Concept} entities. <br/>
 * <br/>
 * 
 * The ConceptDAO interface abstracts the application from a particular
 * implementation of the CRUD operations on the entities
 * 
 * @author Job
 * 
 */
public interface ConceptDAO extends BaseDAO<Concept> {

	/**
	 * checks whether a concept with a given name exists.
	 * 
	 * @param name
	 * @return
	 */
	boolean exists(String name);

	/**
	 * gets the identifier of the concept with the given name.
	 * 
	 * @param name
	 * @return
	 */
	String getConceptIdByName(String name);

}
