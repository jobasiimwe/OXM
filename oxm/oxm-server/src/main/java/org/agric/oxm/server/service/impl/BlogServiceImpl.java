/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Blog;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.BlogSearchParams;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.BlogDAO;
import org.agric.oxm.server.service.BlogService;
import org.agric.oxm.utils.BuildSearchUtil;
import org.agric.oxm.utils.MyValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

/**
 * @author Job
 * 
 */
@Service("BlogService")
@Transactional
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDAO blogDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Blog blog) {
		blogDAO.save(blog);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Blog blog) throws ValidationException {
		MyValidate.isNotBlank(blog.getTitle(), "Title");
		MyValidate.isNotBlank(blog.getText(), "Text");
		MyValidate.isNotNull(blog.getCreatedBy(), "Creation Date");
		MyValidate.isNotNull(blog.getCreatedBy(), "Created By");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Blog> getAll() {
		return blogDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Blog getById(String id) {
		return blogDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteByIds(String[] ids) {
		blogDAO.removeByIds(ids);
	}

	// =================================================================================

	@Override
	public Blog searchUniqueWithParams(BlogSearchParams params)
			throws Exception {
		throw new Exception("Un-implemented");
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Blog> searchWithParams(BlogSearchParams params) {
		Search search = prepareSearch(params);
		List<Blog> blogs = blogDAO.search(search);
		return blogs;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Blog> searchWithParams(BlogSearchParams params, Integer pageNo) {
		Search search = prepareSearch(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}
		List<Blog> blogs = blogDAO.search(search);
		return blogs;
	}

	private Search prepareSearch(BlogSearchParams params) {
		Search search = new Search();

		search.addSort("dateCreated", false, true);

		BuildSearchUtil.addEqual(search, "createdBy", params.getCreatedBy());
		BuildSearchUtil.addCeiling(search, "dateCreated", params.getToDate());
		BuildSearchUtil.addFloor(search, "dateCreated", params.getFromDate());

		BuildSearchUtil.addStringsLike(search, "text,title", ",",
				params.getText());

		return search;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public long numberInSearch(BlogSearchParams params) {
		Search search = prepareSearch(params);
		return blogDAO.count(search);
	}

	// =================================================================================

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Blog> getAnnonymously() {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PRE_LOGIN_PAGE_RECORD);

		search.addSort("dateCreated", false, true);
		search.setPage(0);

		List<Blog> blogs = blogDAO.search(search);
		return blogs;
	}

}
