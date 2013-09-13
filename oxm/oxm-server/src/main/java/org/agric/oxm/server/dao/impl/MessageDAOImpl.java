package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Message;
import org.agric.oxm.server.dao.MessageDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("messageDAO")
public class MessageDAOImpl extends BaseDAOImpl<Message> implements
		MessageDAO {

}
