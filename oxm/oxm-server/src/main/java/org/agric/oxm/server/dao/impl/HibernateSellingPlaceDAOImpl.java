package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.server.dao.SellingPlaceDAO;
import org.springframework.stereotype.Repository;

/**
 * Implements {@link SellingPlaceDAO}
 * 
 * @author Job
 * 
 */
@Repository("SellingPlaceDAO")
public class HibernateSellingPlaceDAOImpl extends BaseDAOImpl<SellingPlace>
		implements SellingPlaceDAO {

}
