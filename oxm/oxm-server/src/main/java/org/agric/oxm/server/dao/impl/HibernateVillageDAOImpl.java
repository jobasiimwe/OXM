/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Village;
import org.agric.oxm.server.dao.VillageDAO;
import org.springframework.stereotype.Repository;

/**
 * Implements the {@link VillageDAO} interface to provide CRUD operations for
 * the {@link Village} entities using hibernate as the ORM strategy
 * 
 * 
 * @author Job
 * 
 */
@Repository("villageDAO")
public class HibernateVillageDAOImpl extends BaseDAOImpl<Village> implements
		VillageDAO {

}
