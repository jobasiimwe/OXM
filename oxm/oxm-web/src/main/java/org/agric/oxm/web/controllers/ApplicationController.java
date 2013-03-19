package org.agric.oxm.web.controllers;

import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

	@Autowired
	private ConceptService conceptService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/", "index.jsp" })
	public ModelAndView welcomeHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		return new ModelAndView("dashboard", model);
	}

	@RequestMapping("/ServiceLogin")
	public String loginHandler() {
		return "login";
	}

	@RequestMapping("/ServiceLoginFailure")
	public ModelAndView loginFailureHander(ModelMap model) {
		model.put("errorMessage", "username OR password is incorrect");
		return new ModelAndView("login", model);
	}

	@Secured({ PermissionConstants.PERM_ADMIN })
	@RequestMapping("/cpanel")
	public ModelAndView controlpanelHander(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		return new ModelAndView("cpanel", model);
	}

	public void addConceptsToModelMap(ModelMap modelMap,
			String conceptCategoryName, String key) {
		try {
			ConceptCategoryAnnotation sellTypeRoleAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class, conceptCategoryName);

			if (sellTypeRoleAnnotation != null) {
				modelMap.put(
						key,
						conceptService
								.getConceptsByCategoryAnnotation(sellTypeRoleAnnotation));
			}

		} catch (Exception e) {
			log.error("Error picking concepts", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}
	}
}
