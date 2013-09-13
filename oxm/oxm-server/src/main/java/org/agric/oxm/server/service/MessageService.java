/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Message;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface MessageService {

	void save(Message message);

	void validate(Message message) throws ValidationException;

	List<Message> getMessages();

	List<Message> getMessages(User from, User to);

	Message getMessageById(String id);

	List<Message> getMessagesByIds(String[] ids);

	void deleteMessagesByIds(String[] ids);
}
