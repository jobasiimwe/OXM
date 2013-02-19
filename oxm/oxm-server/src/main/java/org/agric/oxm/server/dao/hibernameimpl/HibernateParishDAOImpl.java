package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Parish;
import org.agric.oxm.server.dao.ParishDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("ParishDAO")
public class HibernateParishDAOImpl extends BaseDAOImpl<Parish> implements
		ParishDAO {

}
