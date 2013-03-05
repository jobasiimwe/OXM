/**
 * 
 */
package org.agric.oxm.web.controllers;

import org.agric.oxm.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This handles system errors like File not found, acess denied etc
 * 
 * @author Jmpango
 * 
 */
@Controller
@RequestMapping("error")
public class ErrorController {

	@RequestMapping(method = RequestMethod.GET, value = "/404")
	public ModelAndView handler404(ModelMap model) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"This Path does not Exit on the system");
		return new ModelAndView("errorPage", model);

	}

	/**
	 * HTTP ERROR: 405
	 * 
	 * Request method 'GET' not supported RequestURI=/users/search
	 * (this is an example uri)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/405")
	public ModelAndView handler405(ModelMap model) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"This Path requires post-data to process!. "
						+ "HTTP ERROR: 405, Request method 'GET' not supported");
		return new ModelAndView("errorPage", model);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/accessDenied")
	public ModelAndView handlerAcessDenied(ModelMap model) {
		if (!model
				.containsAttribute(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE))
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"You dont have sufficient rights to access this resource.");
		return new ModelAndView("errorPage", model);
	}
}
