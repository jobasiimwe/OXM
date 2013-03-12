package org.agric.oxm.web.controllers;

import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.web.WebConstants;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {
	
    @RequestMapping(value = { "/", "index.jsp" })
    public ModelAndView welcomeHandler(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
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
    public ModelAndView controlpanelHander(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	return new ModelAndView("cpanel", model);
    }

}
