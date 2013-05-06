package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Season;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.SeasonService;
import org.agric.oxm.web.WebConstants;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SeasonController {

	@Autowired
	private SeasonService seasonService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_SEASONS })
	@RequestMapping(value = "/season/view/", method = RequestMethod.GET)
	public ModelAndView viewSeasonHandler(ModelMap modelMap) {
		List<Season> seasons = seasonService.getSeasons();
		modelMap.put("seasons", seasons);
		return new ModelAndView("viewSeason", modelMap);

	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(value = "/season/add/", method = RequestMethod.GET)
	public ModelAndView addSeasonHandler(ModelMap modelMap)
			throws SessionExpiredException {
		modelMap.put("season", new Season());
		return new ModelAndView("formSeason", modelMap);

	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(value = "/season/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editSeasonHandler(ModelMap modelMap,
			@PathVariable("id") String seasonId) {

		Season season = seasonService.getSeasonById(seasonId);

		if (season != null) {
			modelMap.put("season", season);
			return new ModelAndView("formSeason", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid Season id submitted");
		return viewSeasonHandler(modelMap);

	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(method = RequestMethod.GET, value = "/season/delete/{ids}")
	public ModelAndView deleteSeasonHandler(@PathVariable("ids") String ids,
			ModelMap modelMap) {

		String[] seasonIdzToDelete = ids.split(",");
		try {
			seasonService.deleteSeasonByIds(seasonIdzToDelete);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Season(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewSeasonHandler(modelMap);
	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(method = RequestMethod.POST, value = "/season/save/")
	public ModelAndView saveSeasonHandler(
			@ModelAttribute("season") Season season, ModelMap modelMap)
			throws SessionExpiredException {

		Season existingSeason = season;

		if (StringUtils.isNotEmpty(season.getId())) {
			existingSeason = seasonService.getSeasonById(season.getId());
			existingSeason.setEndDate(season.getEndDate());
			existingSeason.setStartDate(season.getStartDate());
		} else {
			existingSeason.setId(null);
		}

		try {
			seasonService.validate(existingSeason);
			seasonService.save(existingSeason);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Season saved sucessfully.");
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("season", season);
			return new ModelAndView("formSeason", modelMap);
		}
		return viewSeasonHandler(modelMap);
	}
}
