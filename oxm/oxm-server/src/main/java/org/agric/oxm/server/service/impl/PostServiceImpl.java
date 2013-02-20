package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Post;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PostSearchParameters;
import org.agric.oxm.server.dao.PostDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Job
 * 
 */
@Service("postService")
@Transactional
public class PostServiceImpl implements PostService {
	@Autowired
	private PostDAO postDAO;

	@Secured({ PermissionConstants.ADD_POST, PermissionConstants.EDIT_POST })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Post post) {
		postDAO.save(post);
	}

	@Secured({ PermissionConstants.VIEW_POST })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Post post) throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_POST })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Post> getPosts() {
		return postDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_POST })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Post> getPostsWithParams(PostSearchParameters params) {
		// Search search = new Search();
		// search.addFilterEqual("owner", user);
		// return postDAO.search(search);
		return null;
	}

	@Secured({ PermissionConstants.VIEW_POST })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Post getPostById(String id) {
		return postDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_POST })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deletePostsByIds(String[] ids) {
		postDAO.removeByIds(ids);
	}

}
