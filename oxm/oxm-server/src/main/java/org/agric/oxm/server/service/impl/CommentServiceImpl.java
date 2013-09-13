package org.agric.oxm.server.service.impl;

import org.agric.oxm.model.Comment;
import org.agric.oxm.server.dao.CommentDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDAO commentDAO;

	@Override
	@Secured({ PermissionConstants.EDIT_COMMENT })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Comment getCommentById(String id) {
		return commentDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	@Secured({ PermissionConstants.DELETE_COMMENT })
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCommentByIds(String[] ids) {
		commentDAO.removeByIds(ids);
	}

	@Override
	@Secured({ PermissionConstants.DELETE_COMMENT })
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCommentById(String id) {
		commentDAO.removeById(id);
	}

}
