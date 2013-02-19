package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Season;
import org.agric.oxm.server.dao.SeasonDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("seasonDAO")
public class HibernateSeasonDAOImpl extends BaseDAOImpl<Season> implements
		SeasonDAO {

}
