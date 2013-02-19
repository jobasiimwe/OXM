package org.agric.oxm.server.dao.hibernameimpl;

import org.agric.oxm.model.Post;
import org.agric.oxm.server.dao.PostDAO;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Job
 * 
 */
@Repository("PostDAO")
public class HibernatePostDAOImpl extends BaseDAOImpl<Post> implements PostDAO {

}
