package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Position;
import org.agric.oxm.model.search.SingleStringSearchParameters;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PositionService;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.agric.oxm.web.controllers.ApplicationController;
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

	@Autowired
	private ApplicationController applicationController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String PARAM_NAME = "name";

	private static final String COMMAND_NAME = "positionsearch";

	/**
	 * gets a list of positions to be viewed
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public ModelAndView view(ModelMap model) {
		preparePositionSearchModel(new SingleStringSearchParameters(), false,
				false, 1, model);
		model.put(WebConstants.CONTENT_HEADER, "Positions");

		return new ModelAndView("viewPosition", model);
	}

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		SingleStringSearchParameters params = applicationController
				.extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		preparePositionSearchModel(params, true, true, pageNo, model);

		return new ModelAndView("viewPosition", model);
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

		WebUtils.prepareNavigation("Position", positionService
				.numberOfPositionsWithSearchParams(params), positions.size(),
				pageNo, applicationController
						.buildSingleStringSearchNavigationUrl(params,
								"position"), model);

		applicationController.prepareSingleStringSearchCommand(params, model);

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

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView navigate(
			@RequestParam(value = PositionController.PARAM_NAME, required = false) String name,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(PositionController.PARAM_NAME,
				new GenericCommandValue(name));

		preparePositionSearchModel(
				applicationController.extractParams(command), true, false,
				pageNo, model);

		return new ModelAndView("viewPosition", model);
	}

	@Secured({ PermissionConstants.ADD_COMMITTEE_DETAILS,
			PermissionConstants.EDIT_COMMITTEE__DETAILS })
	@RequestMapping(value = "/edit/{positionid}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("positionid") String positionid,
			ModelMap model) {

		if (!model.containsAttribute("position")) {
			Position position = positionService.getPositionById(positionid);
			model.put("position", position);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + position.getName());
		}

		return new ModelAndView("formPosition", model);
	}

	@Secured({ PermissionConstants.EDIT_COMMITTEE__DETAILS,
			PermissionConstants.ADD_CROP })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView save(@ModelAttribute("position") Position position,
			ModelMap model) {

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
			return view(model);
		} catch (Exception e) {
			log.error("Error", e);

			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			model.put("position", position);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + position.getName());
			return edit(position.getId(), model);

		}

	}

	@Secured({ PermissionConstants.ADD_COMMITTEE_DETAILS })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap model) {

		Position position = new Position();
		model.put("position", position);
		model.put(WebConstants.CONTENT_HEADER, "Add new Position");

		return new ModelAndView("formPosition", model);
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
	public ModelAndView delete(@PathVariable("positionIds") String positionIds,
			ModelMap model) {
		String[] positionIdzToDelete = positionIds.split(",");

		try {
			positionService.deletePositionsByIds(positionIdzToDelete);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Position  deleted successfully");
			return view(model);

		} catch (Exception e) {
			log.error("Error", e);
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			return view(model);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/position/details/view/{positionId}", method = RequestMethod.GET)
	public ModelAndView details(@PathVariable("positionId") String positionId,
			ModelMap model) {

		Position position = positionService.getPositionById(positionId);
		model.put("position", position);

		String header = "Details of " + position.getName();
		model.put("contentHeader", header);

		return new ModelAndView("viewPositionDetails", model);

	}

	// =========================================================================================
	// =========================================================================================
	// =========================================================================================

	@Secured({ PermissionConstants.VIEW_COMMITTEE__DETAILS })
	@RequestMapping(value = "/holders/{positionid}", method = RequestMethod.GET)
	public ModelAndView viewPositionHoldersHandler(
			@PathVariable("positionid") String positionid, ModelMap model) {

		if (!model.containsAttribute("position")) {
			Position position = positionService.getPositionById(positionid);
			model.put("position", position);
			model.put(WebConstants.CONTENT_HEADER, "Holders of Position - "
					+ position.getName());
		}

		return new ModelAndView("positionHolders", model);
	}
}
