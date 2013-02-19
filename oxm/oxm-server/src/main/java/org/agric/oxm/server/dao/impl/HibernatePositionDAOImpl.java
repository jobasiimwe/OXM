package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Position;
import org.agric.oxm.server.dao.PositionDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("positionDAO")
public class HibernatePositionDAOImpl extends BaseDAOImpl<Position> implements
		PositionDAO {

}
