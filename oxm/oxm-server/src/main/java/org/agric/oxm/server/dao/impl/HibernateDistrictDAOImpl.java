/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.District;
import org.agric.oxm.server.dao.DistrictDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 *
 */
@Repository("districtDAO")
public class HibernateDistrictDAOImpl extends BaseDAOImpl<District> implements
		DistrictDAO {

}
