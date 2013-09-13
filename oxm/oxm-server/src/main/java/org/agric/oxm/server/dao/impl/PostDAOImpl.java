package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Post;
import org.agric.oxm.server.dao.PostDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("postDAO")
public class PostDAOImpl extends BaseDAOImpl<Post> implements PostDAO {

}
