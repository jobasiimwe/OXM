package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.GisCordinate;
import org.agric.oxm.server.dao.GisCordinateDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("GisCordinateDAO")
public class HibernateGisCordinateDAOImpl extends BaseDAOImpl<GisCordinate>
		implements GisCordinateDAO {

}
