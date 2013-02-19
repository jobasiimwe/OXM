package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Price;
import org.agric.oxm.server.dao.PriceDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("PriceDAO")
public class HibernatePriceDAOImpl extends BaseDAOImpl<Price> implements
		PriceDAO {

}
