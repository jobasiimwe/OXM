package org.agric.oxm.web.controllers;

import java.util.Date;
import java.util.List;

import org.agric.oxm.model.Post;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PostSearchParameters;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.server.service.PostService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * this is the controller for posts and comments
 * 
 * @author Job
 * 
 */
@Controller
@RequestMapping("post")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private CropService cropService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private ApplicationController applicationController;

	public static final String CROP = "cropid";
	public static final String POST_TYPE = "typeid";
	public static final String FROM_DATE = "fromdate";
	public static final String TO_DATE = "todate";

	private static final String COMMAND_NAME = "postsearch";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private PostSearchParameters extractParams(GenericCommand searchCommand) {
		PostSearchParameters params = new PostSearchParameters();
		if (StringUtils.isNotBlank(searchCommand.getValue(CROP))) {
			params.setCrop(cropService.getCropById(searchCommand.getValue(CROP)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(POST_TYPE))) {
			params.setPostType(conceptService.getConceptById(searchCommand
					.getValue(POST_TYPE)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(FROM_DATE)))
			params.setFromDate(searchCommand.getAsDate(FROM_DATE));

		if (StringUtils.isNotBlank(searchCommand.getValue(TO_DATE)))
			params.setToDate(searchCommand.getAsDate(TO_DATE));

		return params;
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/price", params = { "action=search" })
	public ModelAndView searchNavigationHandler(
			@RequestParam(value = CROP, required = false) String cropId,
			@RequestParam(value = POST_TYPE, required = false) String posttypeid,
			@RequestParam(value = FROM_DATE, required = false) String fromDate,
			@RequestParam(value = TO_DATE, required = false) String toDate,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(CROP, new GenericCommandValue(cropId));
		command.getPropertiesMap().put(POST_TYPE,
				new GenericCommandValue(posttypeid));

		command.getPropertiesMap().put(FROM_DATE,
				new GenericCommandValue(fromDate));
		command.getPropertiesMap()
				.put(TO_DATE, new GenericCommandValue(toDate));

		return searchHandler(command, pageNo, modelMap);
	}

	@RequestMapping(value = "/price/search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute("pricesearch") GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {
		PostSearchParameters params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, pageNo, model);

		return new ModelAndView("viewPrice", model);
	}

	private void prepareSearchCommand(ModelMap modelMap,
			PostSearchParameters params) {

		GenericCommand searchCommand = new GenericCommand();

		if (params.getCrop() != null) {
			searchCommand.getPropertiesMap().put(CROP,
					new GenericCommandValue(params.getCrop().getId()));
		}

		if (params.getPostType() != null) {
			searchCommand.getPropertiesMap().put(POST_TYPE,
					new GenericCommandValue(params.getPostType().getId()));
		}

		if (params.getFromDate() != null) {
			searchCommand.getPropertiesMap().put(
					FROM_DATE,
					new GenericCommandValue(GenericCommandValue.dateFormat
							.format(params.getFromDate())));
		}

		if (params.getToDate() != null) {
			searchCommand.getPropertiesMap().put(
					TO_DATE,
					new GenericCommandValue(GenericCommandValue.dateFormat
							.format(params.getToDate())));
		}

		if (params.getFromDate() != null && params.getToDate() != null) {
			if (params.getToDate().before(params.getFromDate()))
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"Error, 'to' date is before 'from' date");
		}

		modelMap.put(COMMAND_NAME, searchCommand);

		modelMap.put("crops", cropService.getCrops());

		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.FORUM_POST_TYPE, "types");
	}

	/**
	 * prepares the post search model
	 * 
	 * @param params
	 * @param pageNo
	 * @param modelMap
	 */
	private void prepareSearchModel(PostSearchParameters params,
			Integer pageNo, ModelMap modelMap) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Post> posts = postService.searchWithParams(params, pageNo);
		modelMap.put("posts", posts);
		modelMap.put(
				WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
				String.format("search completed with: %s result(s)",
						String.valueOf(posts.size())));

		WebUtils.prepareNavigation("Posts", postService.numberInSearch(params),
				posts.size(), pageNo, buildSearchNavigationUrl(params),
				modelMap);

		prepareSearchCommand(modelMap, params);

		modelMap.put(WebConstants.CONTENT_HEADER, "Prices - search results ");

		// set a variable searching to true
		// this variable is used in determining what navigation file to use
		modelMap.put("searching", true);
	}

	/**
	 * builds a search navigation url based on the given search parameter
	 * object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildSearchNavigationUrl(PostSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/post?action=search");

		if (params.getCrop() != null) {
			buffer.append("&").append(CROP).append("=")
					.append(params.getCrop().getId());
		}

		if (params.getPostType() != null) {
			buffer.append("&").append(POST_TYPE).append("=")
					.append(params.getPostType().getId());
		}

		if (params.getFromDate() != null) {
			buffer.append("&").append(FROM_DATE).append("=")
					.append(params.getFromDate().toString());
		}

		if (params.getToDate() != null) {
			buffer.append("&").append(TO_DATE).append("=")
					.append(params.getToDate().toString());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_POST })
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewPostHandler(ModelMap modelMap)
			throws SessionExpiredException {

		prepareDefaultPostView(modelMap);
		prepareNewPostModel(modelMap);
		return new ModelAndView("postView", modelMap);
	}

	private void prepareDefaultPostView(ModelMap modelMap) {
		PostSearchParameters params = new PostSearchParameters();
		prepareSearchCommand(modelMap, params);
		prepareSearchModel(params, 1, modelMap);

		modelMap.put(WebConstants.CONTENT_HEADER, "Forum Posts");
	}

	private void prepareNewPostModel(ModelMap modelMap)
			throws SessionExpiredException {
		Post post = new Post();
		post.setOwner(OXMSecurityUtil.getLoggedInUser());

		modelMap.put("post", post);
	}

	@Secured({ PermissionConstants.VIEW_POST })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addPostHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("posts", postService.getPosts());
		modelMap.put(WebConstants.CONTENT_HEADER, "Forum Posts");
		return new ModelAndView("postView", modelMap);
	}

	@Secured({ PermissionConstants.ADD_POST, PermissionConstants.EDIT_POST })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView savePostHandler(@ModelAttribute("post") Post post,
			ModelMap modelMap) throws SessionExpiredException {

		try {
			if (post.getDatePosted() == null)
				post.setDatePosted(new Date());

			postService.validate(post);

			Post existingPost = post;

			if (StringUtils.isNotEmpty(post.getId())) {
				existingPost = postService.getPostById(post.getId());
				existingPost.setCrop(post.getCrop());
				existingPost.setDatePosted(post.getDatePosted());
				existingPost.setOwner(post.getOwner());
				existingPost.setType(post.getType());
			} else {
				existingPost.setId(null);

			}
			postService.save(existingPost);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Post saved sucessfully.");

			return viewPostHandler(modelMap);

		} catch (ValidationException e) {

			log.error("Error saving post", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error - "
					+ e.getMessage());
			prepareDefaultPostView(modelMap);
			return new ModelAndView("formPrice", modelMap);
		}
	}
}
