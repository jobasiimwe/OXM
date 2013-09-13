/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Blog;
import org.agric.oxm.model.search.BlogSearchParams;

/**
 * @author Job
 * 
 */
public interface BlogService extends
		BaseServiceWithSearch<Blog, BlogSearchParams> {
	
	List<Blog> getAnnonymously();
	
}
