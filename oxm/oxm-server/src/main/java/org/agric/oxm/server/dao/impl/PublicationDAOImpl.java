package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Publication;
import org.agric.oxm.server.dao.PublicationDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("publicationDAO")
public class PublicationDAOImpl extends BaseDAOImpl<Publication>
		implements PublicationDAO {

}
