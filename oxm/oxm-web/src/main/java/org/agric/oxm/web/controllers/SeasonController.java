package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.Season;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.SeasonService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
import org.apache.commons.lang.StringUtils;
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
public class SeasonController {

    @Autowired
    private SeasonService seasonService;
    @Autowired
    private ConceptService conceptService;

    @Secured({ PermissionConstants.VIEW_SEASONS })
    @RequestMapping(value = "/season/view/", method = RequestMethod.GET)
    public ModelAndView viewSeasonHandler(ModelMap model)
			throws SessionExpiredException {
	List<Season> seasons = seasonService.getSeasons();
	model.put("seasons", seasons);
	return new ModelAndView("viewSeason", model);

    }

    @Secured({ PermissionConstants.VIEW_SEASONS, PermissionConstants.MANAGE_SEASONS })
    @RequestMapping(value = "/season/add/", method = RequestMethod.GET)
    public ModelAndView addSeasonHandler(ModelMap model)
			throws SessionExpiredException {
	model.put("season", new Season());
	prepareSeasonModel(model);
	return new ModelAndView("formSeason", model);

    }

    @Secured({ PermissionConstants.VIEW_SEASONS, PermissionConstants.MANAGE_SEASONS })
    @RequestMapping(value = "/season/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editSeasonHandler(ModelMap model,
			@PathVariable("id") String seasonId) throws SessionExpiredException {

	Season season = seasonService.getSeasonById(seasonId);

	if (season != null) {
	    model.put("season", season);
	    prepareSeasonModel(model);
	    return new ModelAndView("formSeason", model);
	}

	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid Season id submitted");
	return viewSeasonHandler(model);

    }

    @Secured({ PermissionConstants.VIEW_SEASONS, PermissionConstants.MANAGE_SEASONS })
    @RequestMapping(method = RequestMethod.POST, value = "/season/delete/")
    public void deleteSeasonHandler(
			@RequestParam("selectedSeason") List<String> ids,
			HttpServletResponse response) {

	try {
	    if (ids != null) {
		String[] sPlaceIds = new String[ids.size()];
		sPlaceIds = ids.toArray(sPlaceIds);

		seasonService.deleteSeasonByIds(sPlaceIds);
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(
						"Season(s) deleted successfully");
	    } else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		response.getWriter().write(
						"no season(s) supplied for deleting");
	    }
	} catch (IOException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }

    private void prepareSeasonModel(ModelMap model) {
	try {
	    ConceptCategoryAnnotation typeRoleAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class,
							DefaultConceptCategories.SEASON_NAME);

	    if (typeRoleAnnotation != null) {
		ConceptCategory sellingTypeRole = conceptService.getConceptCategoryById(typeRoleAnnotation.id());

		if (sellingTypeRole != null) {
		    model.put("names", conceptService.getConceptsByCategory(sellingTypeRole));
		}
	    }
	} catch (SecurityException e) {
	} catch (NoSuchFieldException e) {
	}
    }

    @Secured({ PermissionConstants.VIEW_SEASONS, PermissionConstants.MANAGE_SEASONS })
    @RequestMapping(method = RequestMethod.POST, value = "/season/save/")
    public ModelAndView saveSeasonHandler(
			@ModelAttribute("season") Season season,
			ModelMap model) throws SessionExpiredException {

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
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Season saved sucessfully.");
	} catch (ValidationException e) {
	    model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
	    model.put("season", season);
	    prepareSeasonModel(model);
	    return new ModelAndView("formSeason", model);
	}
	return viewSeasonHandler(model);
    }
}
