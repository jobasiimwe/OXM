package org.agric.oxm.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

    /**
     * handles the index page url
     * 
     * @return
     */
    @RequestMapping(value = { "/", "index.jsp" })
    public ModelAndView welcomeHandler() {
	return null;
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
