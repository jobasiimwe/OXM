package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.server.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("crop")
public class CropController {

	@Autowired
	private CropService cropService;

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView viewConceptHandler(ModelMap model) {
		List<Crop> crops = cropService.getCrops();
		model.put("crops", crops);
		return new ModelAndView("viewCrop", model);
	}
	
	
}
