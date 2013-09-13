/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.BlogComment;
import org.agric.oxm.server.dao.BlogCommentDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("blogCommentDAO")
public class BlogCommentDAOImpl extends BaseDAOImpl<BlogComment> implements
		BlogCommentDAO {

}
