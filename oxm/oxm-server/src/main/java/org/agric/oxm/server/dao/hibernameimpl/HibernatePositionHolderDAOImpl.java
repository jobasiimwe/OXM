package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.PositionHolder;
import org.agric.oxm.server.dao.PositionHolderDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("PositionHolderDAO")
public class HibernatePositionHolderDAOImpl extends BaseDAOImpl<PositionHolder>
		implements PositionHolderDAO {

}
