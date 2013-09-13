/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Post;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PostSearchParameters;

/**
 * 
 * service for forum posts
 * 
 * @author Job
 * 
 */
public interface PostService {

	void save(Post post);

	void validate(Post post) throws ValidationException;

	List<Post> getPosts();

	List<Post> searchWithParams(PostSearchParameters params);

	long numberInSearch(PostSearchParameters params);

	List<Post> searchWithParams(PostSearchParameters params, Integer pageNo);

	Post getPostById(String id);

	void deletePostsByIds(String[] ids);

}
