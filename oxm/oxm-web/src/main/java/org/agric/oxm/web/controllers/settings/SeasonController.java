package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Season;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.util.MyDateUtil;
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
@RequestMapping("season")
public class SeasonController {

	@Autowired
	private SeasonService seasonService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_SEASONS })
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap) {
		List<Season> seasons = seasonService.getAll();
		modelMap.put("seasons", seasons);
		return new ModelAndView("viewSeason", modelMap);

	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) throws SessionExpiredException {
		modelMap.put("season", new Season());

		modelMap.put("weathers", Season.Weather.values());
		return new ModelAndView("formSeason", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(ModelMap modelMap,
			@PathVariable("id") String seasonId) {

		Season season = seasonService.getById(seasonId);

		if (season != null) {
			modelMap.put("season", season);
			return new ModelAndView("formSeason", modelMap);
		}

		modelMap.put("weathers", Season.Weather.values());

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid Season id submitted");
		return view(modelMap);

	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{ids}")
	public ModelAndView delete(@PathVariable("ids") String ids,
			ModelMap modelMap) {

		String[] seasonIdzToDelete = ids.split(",");
		try {
			seasonService.deleteByIds(seasonIdzToDelete);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Season(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return view(modelMap);
	}

	@Secured({ PermissionConstants.VIEW_SEASONS,
			PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(@ModelAttribute("season") Season season,
			ModelMap modelMap) throws SessionExpiredException {

		try {
			seasonService.validate(season);

			season.setName(MyDateUtil.getDateRangeString(season.getStartDate(),
					season.getEndDate()));

			Season existing = season;

			if (StringUtils.isNotEmpty(season.getId())) {

				existing = seasonService.getById(season.getId());

				existing.setEndDate(season.getEndDate());
				existing.setStartDate(season.getStartDate());
				existing.setName(season.getName());
				existing.setWeather(season.getWeather());
				existing.setWeatherDescription(season.getWeatherDescription());
			} else {
				existing.setId(null);
			}

			seasonService.save(existing);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Season saved sucessfully.");
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("season", season);
			return new ModelAndView("formSeason", modelMap);
		}
		return view(modelMap);
	}
}
