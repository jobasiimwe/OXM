package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Position;
import org.agric.oxm.model.search.SingleStringSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PositionService;
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
@RequestMapping("position")
public class PositionController {

	@Autowired
	private PositionService positionService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String PARAM_NAME = "name";

	private static final String COMMAND_NAME = "positionsearch";

	private void preparePositionSearchCommand(ModelMap model,
			SingleStringSearchParameters params) {
		GenericCommand searchCommand = new GenericCommand();

		if (StringUtils.isNotEmpty(params.getName()))
			searchCommand.getPropertiesMap().put(PositionController.PARAM_NAME,
					new GenericCommandValue(params.getName()));

		model.put(PositionController.COMMAND_NAME, searchCommand);
	}

	/**
	 * gets a list of positions to be viewed
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/view/page/{pageNo}" }, method = RequestMethod.GET)
	public ModelAndView viewPositionsHandler(
			@PathVariable(value = "pageNo") Integer pageNo, ModelMap model) {
		preparePositionSearchModel(new SingleStringSearchParameters(), false,
				false, pageNo, model);
		model.put(WebConstants.CONTENT_HEADER, "Positions");

		return new ModelAndView("viewPosition", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView courseSearchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		SingleStringSearchParameters params = extractPositionSearchParamsFromCommand(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		preparePositionSearchModel(params, true, true, pageNo, model);

		return new ModelAndView("viewCourses", model);
	}

	/**
	 * prepares the position model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param model
	 */
	private void preparePositionSearchModel(
			SingleStringSearchParameters params, Boolean searching,
			Boolean newSearch, Integer pageNo, ModelMap model) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Position> positions = positionService.searchWithParams(params,
				pageNo);
		model.put("positions", positions);

		WebUtils.prepareNavigation("Position",
				positionService.numberOfPositionsWithSearchParams(params),
				positions.size(), pageNo,
				buildPositionSearchNavigationUrl(params), model);

		preparePositionSearchCommand(model, params);

		if (newSearch)
			model.put(
					WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					String.format("search completed with: %s result(s)",
							String.valueOf(positions.size())));
		if (searching) {
			model.put(WebConstants.CONTENT_HEADER,
					"Positions - search results ");

			// set a variable searching to true
			// this variable is used in determining what navigation file to use
			model.put("searching", true);
		} else
			model.put(WebConstants.CONTENT_HEADER, "List of Positions");

	}

	/**
	 * builds a search navigation url based on the given search parameter
	 * object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildPositionSearchNavigationUrl(
			SingleStringSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/position?action=search");

		if (StringUtils.isNotBlank(params.getName())) {
			buffer.append("&").append(PARAM_NAME).append("=")
					.append(params.getName());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView userSearchNavigationHandler(
			@RequestParam(value = PositionController.PARAM_NAME, required = false) String name,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(PositionController.PARAM_NAME,
				new GenericCommandValue(name));

		preparePositionSearchModel(
				extractPositionSearchParamsFromCommand(command), true, false,
				pageNo, model);

		return new ModelAndView("viewPositions", model);
	}

	private SingleStringSearchParameters extractPositionSearchParamsFromCommand(
			GenericCommand searchCommand) {
		SingleStringSearchParameters params = new SingleStringSearchParameters();

		if (StringUtils.isNotBlank(searchCommand.getValue(PARAM_NAME))) {
			params.setName(searchCommand.getValue(PARAM_NAME));
		}

		return params;
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(value = "/details/{positionid}", method = RequestMethod.GET)
	public ModelAndView viewPositionDetailsHandler(
			@PathVariable("positionid") String positionid, ModelMap model) {

		if (!model.containsAttribute("position")) {
			Position position = positionService.getPositionById(positionid);
			model.put("position", position);
			model.put(WebConstants.CONTENT_HEADER, position.getName()
					+ " Details");
		}

		return new ModelAndView("positionDetails", model);
	}

	@Secured({ PermissionConstants.ADD_CROP })
	@RequestMapping(value = "/edit/{positionid}", method = RequestMethod.GET)
	public ModelAndView editPositionHandler(
			@PathVariable("positionid") String positionid, ModelMap model) {

		if (!model.containsAttribute("position")) {
			Position position = positionService.getPositionById(positionid);
			model.put("position", position);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + position.getName());
		}

		return new ModelAndView("positionForm", model);
	}

	@Secured({ PermissionConstants.EDIT_CROP, PermissionConstants.ADD_CROP })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView savePositionHandler(
			@ModelAttribute("position") Position position, ModelMap model) {

		try {

			positionService.validate(position);

			Position exisitingPosition = position;
			if (StringUtils.isNotBlank(position.getId())
					|| StringUtils.isNotEmpty(position.getId())) {

				exisitingPosition = positionService.getPositionById(position
						.getId());

				exisitingPosition.setName(position.getName());
				exisitingPosition.setRecordStatus(position.getRecordStatus());
			} else {
				exisitingPosition.setId(null);
			}

			positionService.save(exisitingPosition);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Position changes are saved successful");
			return viewPositionsHandler(1, model);
		} catch (Exception e) {
			log.error("Error", e);

			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			model.put("position", position);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + position.getName());
			return editPositionHandler(position.getId(), model);

		}

	}

	@Secured({ PermissionConstants.ADD_CROP })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addPositionHandler(ModelMap model) {

		Position position = new Position();
		model.put("position", position);
		model.put(WebConstants.CONTENT_HEADER, "Add new Position");

		return new ModelAndView("positionForm", model);
	}

	/**
	 * Handles deleting of positions
	 * 
	 * @param positionIds
	 * @param model
	 * @return
	 */
	@Secured({ PermissionConstants.DELETE_CROP })
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{positionIds}")
	public ModelAndView deletePositionHandler(
			@PathVariable("positionIds") String positionIds, ModelMap model) {
		String[] positionIdzToDelete = positionIds.split(",");

		try {
			positionService.deletePositionsByIds(positionIdzToDelete);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Position  deleted successfully");
			return viewPositionsHandler(1, model);

		} catch (Exception e) {
			log.error("Error", e);
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			return viewPositionsHandler(1, model);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/position/details/view/{positionId}", method = RequestMethod.GET)
	public ModelAndView viewPositionDetailHandler(
			@PathVariable("positionId") String positionId, ModelMap model) {

		Position position = positionService.getPositionById(positionId);
		model.put("position", position);

		String header = "Details of " + position.getName();
		model.put("contentHeader", header);

		return new ModelAndView("viewPositionDetails", model);

	}
}
