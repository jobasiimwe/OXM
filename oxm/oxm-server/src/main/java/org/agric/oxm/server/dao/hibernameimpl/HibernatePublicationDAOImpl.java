package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Publication;
import org.agric.oxm.server.dao.PublicationDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("PublicationDAO")
public class HibernatePublicationDAOImpl extends BaseDAOImpl<Publication>
		implements PublicationDAO {

}
