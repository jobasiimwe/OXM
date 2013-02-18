package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Producer;
import org.agric.oxm.server.dao.ProducerDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("ProducerDAO")
public class HibernateProducerDAOImpl extends BaseDAOImpl<Producer> implements
		ProducerDAO {

}
