/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Blog;
import org.agric.oxm.server.dao.BlogDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("blogDAO")
public class BlogDAOImpl extends BaseDAOImpl<Blog> implements BlogDAO {

}
