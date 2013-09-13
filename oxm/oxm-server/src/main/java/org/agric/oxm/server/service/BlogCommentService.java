/**
 * 
 */
package org.agric.oxm.server.service;

import org.agric.oxm.model.BlogComment;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface BlogCommentService {

	void validate(BlogComment t) throws ValidationException;

	BlogComment getById(String id);

}
