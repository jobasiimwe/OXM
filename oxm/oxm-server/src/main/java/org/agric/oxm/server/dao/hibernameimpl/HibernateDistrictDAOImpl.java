/**
 * 
 */
package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.District;
import org.agric.oxm.server.dao.DistrictDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 *
 */
@Repository("DistrictDAO")
public class HibernateDistrictDAOImpl extends BaseDAOImpl<District> implements
		DistrictDAO {

}
