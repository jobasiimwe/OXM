/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Comment;
import org.agric.oxm.server.dao.CommentDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("commentDAO")
public class CommentDAOImpl extends BaseDAOImpl<Comment> implements
		CommentDAO {

}
