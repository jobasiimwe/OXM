package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.SubCounty;
import org.agric.oxm.server.dao.SubCountyDAO;
import org.springframework.stereotype.Repository;

@Repository("subCountyDAO")
public class HibernateSubCountyDAOImpl extends BaseDAOImpl<SubCounty> implements SubCountyDAO{

}
