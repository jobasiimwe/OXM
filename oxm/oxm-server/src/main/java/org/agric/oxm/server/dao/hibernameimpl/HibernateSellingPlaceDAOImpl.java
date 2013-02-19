package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.server.dao.SellingPlaceDAO;
import org.springframework.stereotype.Repository;

/**
 * Implements {@link SellingPlaceDAO}
 * 
 * @author Job
 * 
 */
@Repository("sellingPlaceDAO")
public class HibernateSellingPlaceDAOImpl extends BaseDAOImpl<SellingPlace>
		implements SellingPlaceDAO {

}
