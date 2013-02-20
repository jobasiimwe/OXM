package org.agric.oxm.web.controllers;

import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

    @RequestMapping(value = { "/", "index.jsp" })
    public ModelAndView welcomeHandler(ModelMap model) {
	User loggedInUser  = null;
	try {
	    loggedInUser   = OXMSecurityUtil.getLoggedInUser();
	} catch (SessionExpiredException e) {
	    e.printStackTrace();
	}
	
	if(loggedInUser.getProfilePicture() == null){
	    model.put("profile_Img", "empty");
	}
	
	model.put("user", loggedInUser);
	
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
}
