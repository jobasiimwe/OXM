/**
 * 
 */
package org.agric.oxm.server.service;

import org.agric.oxm.model.Comment;

/**
 * @author Job
 * 
 */
public interface CommentService {

	Comment getCommentById(String id);

	void deleteCommentByIds(String[] ids);

	void deleteCommentById(String id);
}
