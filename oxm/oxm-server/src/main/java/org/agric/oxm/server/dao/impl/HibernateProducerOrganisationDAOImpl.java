/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.server.dao.ProducerOrganisationDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("producuserOrganisationDAO")
public class HibernateProducerOrganisationDAOImpl extends
		BaseDAOImpl<ProducerOrganisation> implements
		ProducerOrganisationDAO {

}
