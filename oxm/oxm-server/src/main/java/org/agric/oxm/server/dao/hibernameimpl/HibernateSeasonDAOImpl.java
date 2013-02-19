package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Season;
import org.agric.oxm.server.dao.SeasonDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("SeasonDAO")
public class HibernateSeasonDAOImpl extends BaseDAOImpl<Season> implements
		SeasonDAO {

}
