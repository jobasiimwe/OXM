package org.agric.oxm.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.model.Message;
import org.agric.oxm.model.User;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.MessageDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	private static Logger log = LoggerFactory
			.getLogger(MessageServiceImpl.class);

	@Autowired
	private MessageDAO messageDAO;

	@Secured({ PermissionConstants.ADD_MESSAGE,
			PermissionConstants.EDIT_MESSAGE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Message message) {
		messageDAO.save(message);
	}

	@Secured({ PermissionConstants.VIEW_MESSAGE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Message message) throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_MESSAGE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Message> getMessages() {
		return messageDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_MESSAGE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Message> getMessages(User from, User to) {
		Search search = new Search();
		search.addFilterEqual("from", from);
		search.addFilterEqual("to", to);
		return messageDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_MESSAGE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Message> getMessagesByIds(String[] ids) {
		Search search = new Search();
		search.addFilterIn("id", (Object) ids);
		return messageDAO.search(search);
	}

	@Secured({ PermissionConstants.VIEW_MESSAGE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Message getMessageById(String id) {
		return messageDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_MESSAGE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteMessagesByIds(String[] ids) {
		List<Message> messagesToDelete = new ArrayList<Message>();
		List<Message> messagesToUpdate = new ArrayList<Message>();
		List<Message> messages = getMessagesByIds(ids);

		try {
			User loggedInUser = OXMSecurityUtil.getLoggedInUser();

			for (Message msg : messages) {
				if (loggedInUser.equals(msg.getFrom()) && msg.isToCleared()
						|| loggedInUser.equals(msg.getTo())
						&& msg.isFromCleared()) {
					messagesToDelete.add(msg);
					continue;
				}
				if (loggedInUser.equals(msg.getFrom()) && !msg.isToCleared()) {
					msg.setFromCleared(true);
					messagesToUpdate.add(msg);
					continue;
				}
				if (loggedInUser.equals(msg.getTo()) && !msg.isFromCleared()) {
					msg.setToCleared(true);
					messagesToUpdate.add(msg);
					continue;
				}
			}

			if (messagesToDelete.size() > 0) {
				String[] toDelete = new String[messagesToDelete.size()];
				for (int i = 0; i < messagesToDelete.size(); i++) {
					toDelete[i] = messagesToDelete.get(i).getId();
				}
				messageDAO.removeByIds(toDelete);
			}

			if (messagesToUpdate.size() > 0) {
				Message[] toUpdate = new Message[messagesToUpdate.size()];
				for (int i = 0; i < messagesToUpdate.size(); i++) {
					toUpdate[i] = messagesToUpdate.get(i);
				}
				messageDAO.save(toUpdate);

			}

		} catch (SessionExpiredException e) {
			log.error("Messaging error", e);

		}
	}
}
