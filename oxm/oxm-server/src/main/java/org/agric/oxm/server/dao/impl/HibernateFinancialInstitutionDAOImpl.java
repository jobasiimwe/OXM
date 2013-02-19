package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.server.dao.FinancialInstitutionDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 *
 */
@Repository("financialInstitutionDAO")
public class HibernateFinancialInstitutionDAOImpl extends
		BaseDAOImpl<FinancialInstitution> implements FinancialInstitutionDAO {

}
