package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Committee;
import org.agric.oxm.server.dao.CommitteeDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("committeeDAO")
public class HibernateCommitteeDAOImpl extends BaseDAOImpl<Committee> implements
		CommitteeDAO {

}
