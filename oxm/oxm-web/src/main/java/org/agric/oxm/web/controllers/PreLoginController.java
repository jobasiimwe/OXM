package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Blog;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.Season;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.search.PriceSearchParams;
import org.agric.oxm.server.service.BlogService;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.server.service.FinancialInstitutionService;
import org.agric.oxm.server.service.PriceService;
import org.agric.oxm.server.service.SeasonService;
import org.agric.oxm.server.service.SellingPlaceService;
import org.agric.oxm.web.forms.GenericCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@Autowired
	private SeasonService seasonService;

	@Autowired
	private BlogService blogService;

	@Autowired
	private PriceController priceController;

	@RequestMapping(value = "prices/search", method = RequestMethod.POST)
	public ModelAndView searchPriceHandler(
			@ModelAttribute(PriceController.COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {
		PriceSearchParams params = priceController.extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		priceController.prepareSearchModel(params, pageNo, modelMap,
				"prelogin/prices");

		return new ModelAndView("preloginPriceView", modelMap);
	}

	@RequestMapping(value = "prices/search", method = RequestMethod.GET)
	public ModelAndView searchPriceGetHandler(ModelMap modelMap) {
		return viewPriceHandler(modelMap);
	}

	@RequestMapping(method = RequestMethod.GET, value = { "prices" }, params = { "action=search" })
	public ModelAndView navigatePrices(
			@RequestParam(value = PriceController.PRODUCT, required = false) String productId,
			@RequestParam(value = PriceController.SELLING_PLACE, required = false) String sellingPlaceId,
			@RequestParam(value = PriceController.FROM_DATE, required = false) String fromDate,
			@RequestParam(value = PriceController.TO_DATE, required = false) String toDate,
			@RequestParam(value = PriceController.ADMIN_VIEW, required = false) String adminview,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.checkAndPut(PriceController.PRODUCT, productId);
		command.checkAndPut(PriceController.SELLING_PLACE, sellingPlaceId);
		command.checkAndPut(PriceController.FROM_DATE, fromDate);
		command.checkAndPut(PriceController.TO_DATE, toDate);
		command.checkAndPut(PriceController.ADMIN_VIEW, adminview);

		return searchPriceHandler(command, pageNo, modelMap);
	}

	@RequestMapping(value = { "prices" }, method = RequestMethod.GET)
	public ModelAndView viewPriceHandler(ModelMap modelMap) {

		PriceSearchParams params = new PriceSearchParams(false);
		priceController.prepareSearchModel(params, 1, modelMap,
				"prelogin/prices");

		return new ModelAndView("preloginPriceView", modelMap);

		// List<Price> prices = priceService.getAnnonymouslyViewablePrices();
		// modelMap.put("prices", prices);
		//
		// return new ModelAndView("preloginPriceView", modelMap);
	}

	// ========================================================================================
	// ========================================================================================
	// ========================================================================================

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

		List<FinancialInstitution> financialInstitutions = financialInstitutionService
				.getAnnonymouslyViewableFInstitutions();
		modelMap.put("fInstitutions", financialInstitutions);

		return new ModelAndView("preloginfInstitutionView", modelMap);
	}

	@RequestMapping(value = { "seasons" }, method = RequestMethod.GET)
	public ModelAndView viewSeasonHandler(ModelMap modelMap) {

		List<Season> seasons = seasonService.getAnnonymously();
		modelMap.put("seasons", seasons);

		return new ModelAndView("preloginSeasonView", modelMap);
	}

	// ====================================================================================
	// ====================================================================================

	// @Autowired
	// private Blo

	@RequestMapping(value = { "blogs" }, method = RequestMethod.GET)
	public ModelAndView viewBlogHandler(ModelMap modelMap) {

		List<Blog> blogs = blogService.getAnnonymously();
		modelMap.put("blogs", blogs);

		return new ModelAndView("preloginBlogView", modelMap);
	}

	@RequestMapping(value = { "blogs/{id}" }, method = RequestMethod.GET)
	public ModelAndView viewBlogDetailHandler(
			@PathVariable(value = "id") String id, ModelMap modelMap) {

		Blog b = blogService.getById(id);

		if (b != null) {
			modelMap.put("blog", b);
		}
		return new ModelAndView("preloginBlogView", modelMap);
	}
}
