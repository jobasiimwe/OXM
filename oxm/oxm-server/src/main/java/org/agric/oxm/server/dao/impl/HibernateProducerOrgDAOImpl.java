/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.server.dao.ProducerOrgDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("producerOrgDAO")
public class HibernateProducerOrgDAOImpl extends
		BaseDAOImpl<ProducerOrganisation> implements
		ProducerOrgDAO {

}
