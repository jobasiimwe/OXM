package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.Price;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.server.service.FinancialInstitutionService;
import org.agric.oxm.server.service.PriceService;
import org.agric.oxm.server.service.SellingPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("prelogin")
public class PreLoginController {

	@Autowired
	private PriceService priceService;

	@Autowired
	private SellingPlaceService sellingPlaceService;

	@Autowired
	private CropService cropService;
	
	@Autowired
	private FinancialInstitutionService financialInstitutionService;

	@RequestMapping(value = { "prices" }, method = RequestMethod.GET)
	public ModelAndView viewPriceHandler(ModelMap modelMap) {

		List<Price> prices = priceService.getAnnonymouslyViewablePrices();
		modelMap.put("prices", prices);

		return new ModelAndView("preloginPriceView", modelMap);
	}

	@RequestMapping(value = { "sellingplaces" }, method = RequestMethod.GET)
	public ModelAndView viewSellingPlaceHandler(ModelMap modelMap) {

		List<SellingPlace> sellingPlaces = sellingPlaceService
				.getAnnonymouslyViewableSellingPlaces();
		modelMap.put("sellingplaces", sellingPlaces);

		return new ModelAndView("preloginSellingPlaceView", modelMap);
	}

	@RequestMapping(value = { "crops" }, method = RequestMethod.GET)
	public ModelAndView viewCropHandler(ModelMap modelMap) {

		List<Crop> crops = cropService.getAnnonymouslyViewableCrops();
		modelMap.put("crops", crops);

		return new ModelAndView("preloginCropView", modelMap);
	}
	
	@RequestMapping(value = { "finstitutions" }, method = RequestMethod.GET)
	public ModelAndView viewFInstitutionHandler(ModelMap modelMap) {

		List<FinancialInstitution> financialInstitutions = financialInstitutionService.getAnnonymouslyViewableFInstitutions();
		modelMap.put("fInstitutions", financialInstitutions);

		return new ModelAndView("preloginfInstitutionView", modelMap);
	}

}
