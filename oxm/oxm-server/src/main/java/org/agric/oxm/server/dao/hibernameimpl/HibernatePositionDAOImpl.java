package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Position;
import org.agric.oxm.server.dao.PositionDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("PositionDAO")
public class HibernatePositionDAOImpl extends BaseDAOImpl<Position> implements
		PositionDAO {

}
