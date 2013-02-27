/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.ProductionOrganisation;
import org.agric.oxm.server.dao.ProductionOrganisationDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("productionOrganisationDAO")
public class HibernateProductionOrganisationDAOImpl extends
		BaseDAOImpl<ProductionOrganisation> implements
		ProductionOrganisationDAO {

}
