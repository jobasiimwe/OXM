/**
 * 
 */
package org.agric.oxm.server.service.impl;

import org.agric.oxm.model.BlogComment;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.BlogCommentDAO;
import org.agric.oxm.server.service.BlogCommentService;
import org.agric.oxm.utils.MyValidate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Job
 * 
 */
@Service("blogCommentService")
@Transactional
public class BlogCommentServiceImpl implements BlogCommentService {

	@Autowired
	private BlogCommentDAO blogCommentDAO;

	@Override
	public void validate(BlogComment t) throws ValidationException {
		MyValidate.isNotNull(t.getBlog(), "Blog");

		MyValidate.isNotBlank(t.getText(), "Text");
		MyValidate.isNotNull(t.getDateCreated(), "Creation Date");

		if (t.getCreatedBy() == null && StringUtils.isBlank(t.getName()))
			throw new ValidationException("Name can not be blank");
	}

	@Override
	public BlogComment getById(String id) {
		return blogCommentDAO.searchUniqueByPropertyEqual("id", id);

	}

}
