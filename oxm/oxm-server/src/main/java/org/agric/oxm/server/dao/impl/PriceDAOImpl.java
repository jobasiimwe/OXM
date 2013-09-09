package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Price;
import org.agric.oxm.server.dao.PriceDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("priceDAO")
public class PriceDAOImpl extends BaseDAOImpl<Price> implements
		PriceDAO {

}
