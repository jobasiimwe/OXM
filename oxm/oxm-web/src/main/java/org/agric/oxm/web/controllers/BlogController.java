package org.agric.oxm.web.controllers;

import java.util.Date;
import java.util.List;

import org.agric.oxm.model.Blog;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.BlogSearchParams;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.BlogService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.server.service.impl.UserServiceImpl;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.agric.oxm.web.forms.GenericCommand;
import org.agric.oxm.web.forms.GenericCommandValue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("blog")
public class BlogController {

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private ApplicationController applicationController;

	public static final String TEXT = "text";
	public static final String CREATED_BY = "createdby";
	public static final String FROM_DATE = "fromdate";
	public static final String TO_DATE = "todate";
	public static final String DRAFT = "draft";
	public static final String ADMIN_VIEW = "adminview";

	private static final String COMMAND_NAME = "blogsearch";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private BlogSearchParams extractParams(GenericCommand searchCommand) {

		BlogSearchParams params = new BlogSearchParams();

		if (StringUtils.isNotBlank(searchCommand.getValue(TEXT)))
			params.setText(searchCommand.getValue(TEXT));

		if (StringUtils.isNotBlank(searchCommand.getValue(CREATED_BY)))
			params.setCreatedBy(userService.getUserById(searchCommand
					.getValue(CREATED_BY)));

		if (StringUtils.isNotBlank(searchCommand.getValue(FROM_DATE)))
			params.setFromDate(searchCommand.getAsDate(FROM_DATE));

		if (StringUtils.isNotBlank(searchCommand.getValue(TO_DATE)))
			params.setToDate(searchCommand.getAsDate(TO_DATE));

		if (StringUtils.isNotBlank(searchCommand.getValue(DRAFT)))
			params.setDraft(searchCommand.getBooleanValue(DRAFT));

		if (StringUtils.isNotBlank(searchCommand.getValue(ADMIN_VIEW)))
			params.setDraft(searchCommand.getBooleanValue(ADMIN_VIEW));

		return params;
	}

	// text, createdBy, fromDate, toDate, draft;

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(method = RequestMethod.GET, value = "blog", params = { "action=search" })
	public ModelAndView navigate(
			@RequestParam(value = TEXT, required = false) String text,
			@RequestParam(value = CREATED_BY, required = false) String createdBy,
			@RequestParam(value = FROM_DATE, required = false) String fromDate,
			@RequestParam(value = TO_DATE, required = false) String toDate,
			@RequestParam(value = DRAFT, required = false) String draft,
			@RequestParam(value = ADMIN_VIEW, required = false) String adminView,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(TEXT, new GenericCommandValue(text));
		command.getPropertiesMap().put(CREATED_BY,
				new GenericCommandValue(createdBy));

		command.getPropertiesMap().put(FROM_DATE,
				new GenericCommandValue(fromDate));
		command.getPropertiesMap()
				.put(TO_DATE, new GenericCommandValue(toDate));

		command.getPropertiesMap().put(DRAFT, new GenericCommandValue(draft));
		command.getPropertiesMap().put(ADMIN_VIEW,
				new GenericCommandValue(adminView));

		return searchHandler(command, pageNo, modelMap);
	}

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {
		BlogSearchParams params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, pageNo, model);

		return new ModelAndView("blogView", model);
	}

	private void prepareSearchCommand(ModelMap modelMap, BlogSearchParams params) {

		GenericCommand searchCommand = new GenericCommand();

		searchCommand.checkAndPut(TEXT, params.getText());
		searchCommand.checkAndPut(CREATED_BY, params.getCreatedBy());
		searchCommand.checkAndPut(FROM_DATE, params.getFromDate());
		searchCommand.checkAndPut(TO_DATE, params.getToDate());
		searchCommand.checkAndPut(DRAFT, params.getDraft());
		searchCommand.checkAndPut(ADMIN_VIEW, params.getAdminView());

		modelMap.put(ADMIN_VIEW, params.getAdminView());

		modelMap.put(COMMAND_NAME, searchCommand);
	}

	private void prepareSearchModel(BlogSearchParams params, Integer pageNo,
			ModelMap modelMap) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Blog> blogs = blogService.searchWithParams(params, pageNo);
		modelMap.put("blogs", blogs);
		modelMap.put(
				WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
				String.format("search completed with: %s result(s)",
						String.valueOf(blogs.size())));

		WebUtils.prepareNavigation("Blogs", blogService.numberInSearch(params),
				blogs.size(), pageNo, buildSearchNavigationUrl(params),
				modelMap);

		prepareSearchCommand(modelMap, params);

		modelMap.put(WebConstants.CONTENT_HEADER, "Blogs - search results ");

		// set a variable searching to true
		// this variable is used in determining what navigation file to use
		modelMap.put("searching", true);
	}

	private String buildSearchNavigationUrl(BlogSearchParams params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/blog?action=search");

		GenericCommand.checkAndAppend(TEXT, params.getText(), buffer);
		GenericCommand
				.checkAndAppend(CREATED_BY, params.getCreatedBy(), buffer);

		GenericCommand.checkAndAppend(FROM_DATE, params.getFromDate(), buffer);
		GenericCommand.checkAndAppend(TO_DATE, params.getToDate(), buffer);

		GenericCommand.checkAndAppend(DRAFT, params.getDraft(), buffer);
		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(value = { "view/{adminview}" }, method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap,
			@PathVariable(value = "adminview") Boolean adminView) {

		BlogSearchParams params = new BlogSearchParams();

		try {

			if (adminView) {
				User u = UserServiceImpl.getLoggedInUser();

				if (!u.hasAdministrativePrivileges())
					adminView = false;
			}
		} catch (SessionExpiredException e) {
			log.error("Error", e);
			return applicationController.loginHandler(modelMap);
		}

		params.setAdminView(adminView);
		prepareSearchModel(params, 1, modelMap);

		return new ModelAndView("blogView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(value = { "detail/{adminview}/{id}" }, method = RequestMethod.GET)
	public ModelAndView viewDetail(ModelMap modelMap,
			@PathVariable(value = "adminview") Boolean adminView,
			@PathVariable(value = "id") String id) {

		Blog b = blogService.getById(id);
		if (b != null) {
			BlogSearchParams params = new BlogSearchParams();

			try {

				if (adminView) {
					User u = UserServiceImpl.getLoggedInUser();

					if (!u.hasAdministrativePrivileges())
						adminView = false;
				}
			} catch (SessionExpiredException e) {
				log.error("Error", e);
				return applicationController.loginHandler(modelMap);
			}
			params.setAdminView(adminView);

			prepareSearchCommand(modelMap, params);

			modelMap.put("blog", b);
			//modelMap.put(WebConstants.LONG_RESPONSE_TEXT, b.getText());
		}

		return new ModelAndView("blogView", modelMap);
	}

	// =============== ================= ================ ===============

	@Secured({ PermissionConstants.ADD_PRICE })
	@RequestMapping(value = { "add" }, method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) {

		if (!modelMap.containsAttribute("blog")) {

			try {
				User u = UserServiceImpl.getLoggedInUser();

				Date date = new Date();
				Blog blog = new Blog(u);
				blog.setDateCreated(date);

				modelMap.put("blog", blog);

			} catch (SessionExpiredException e) {
				log.error("Error", e);
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						e.getMessage());
				return applicationController.controlpanelHander(modelMap);
			}

		}

		return new ModelAndView("blogForm", modelMap);
	}

	// =============== ================= ================ ===============

	@Secured({ PermissionConstants.EDIT_PRICE })
	@RequestMapping(value = { "edit/{id}" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String blogId,
			ModelMap modelMap) {

		Blog blog = blogService.getById(blogId);

		if (blog != null) {
			modelMap.put("blog", blog);
			
			modelMap.put(WebConstants.CONTENT_HEADER, "Edit blog!! ");
			return new ModelAndView("blogForm", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid blog id supplied");
		return view(modelMap, true);
	}

	@Secured({ PermissionConstants.DELETE_PRICE })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{ids}")
	public ModelAndView delete(@PathVariable("ids") String ids,
			ModelMap modelMap) {

		String[] blogIdzToDelete = ids.split(",");
		try {
			blogService.deleteByIds(blogIdzToDelete);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Blog(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return view(modelMap, true);
	}

	@Secured({ PermissionConstants.ADD_PRICE, PermissionConstants.EDIT_PRICE })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(@ModelAttribute("blog") Blog blog,
			ModelMap modelMap) {
		Blog existingBlog = blog;

		try {
			blogService.validate(blog);

			if (StringUtils.isNotEmpty(blog.getId())) {
				existingBlog = blogService.getById(blog.getId());
				existingBlog.setCreatedBy(blog.getCreatedBy());
				existingBlog.setDateCreated(blog.getDateCreated());
				existingBlog.setDraft(blog.getDraft());
				existingBlog.setText(blog.getText().trim());
				existingBlog.setTitle(blog.getTitle().trim());
			} else
				existingBlog.setId(null);

			blogService.save(existingBlog);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Blog saved sucessfully.");
		} catch (ValidationException e) {
			modelMap.put("blog", blog);
			return new ModelAndView("formBlog", modelMap);
		}
		return view(modelMap, true);
	}

}
