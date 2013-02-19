package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Message;
import org.agric.oxm.server.dao.MessageDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("MessageDAO")
public class HibernateMessageDAOImpl extends BaseDAOImpl<Message> implements
		MessageDAO {

}
