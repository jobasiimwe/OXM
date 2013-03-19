package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.Price;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PriceSearchParameters;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.server.service.PriceService;
import org.agric.oxm.server.service.SellingPlaceService;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.agric.oxm.web.forms.GenericCommand;
import org.agric.oxm.web.forms.GenericCommandValue;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PriceController {

	@Autowired
	private PriceService priceService;

	@Autowired
	private CropService cropService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private SellingPlaceService sellingPlaceService;

	@Autowired
	private ApplicationController applicationController;

	public static final String CROP = "cropid";
	public static final String SELLING_PLACE = "sellingplaceid";
	public static final String SELL_TYPE = "selltypeid";
	public static final String FROM_DATE = "fromdate";
	public static final String TO_DATE = "todate";
	public static final String ADMIN_VIEW = "adminview";

	private static final String COMMAND_NAME = "pricesearch";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private PriceSearchParameters extractParams(GenericCommand searchCommand) {
		PriceSearchParameters params = new PriceSearchParameters();
		if (StringUtils.isNotBlank(searchCommand.getValue(CROP))) {
			params.setCrop(cropService.getCropById(searchCommand.getValue(CROP)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(SELLING_PLACE))) {
			params.setSellingPlace(sellingPlaceService
					.getSellingPlaceById(searchCommand.getValue(SELLING_PLACE)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(SELL_TYPE))) {
			params.setSellType(conceptService.getConceptById(searchCommand
					.getValue(SELL_TYPE)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(FROM_DATE)))
			params.setFromDate(searchCommand.getAsDate(FROM_DATE));

		if (StringUtils.isNotBlank(searchCommand.getValue(TO_DATE)))
			params.setToDate(searchCommand.getAsDate(TO_DATE));

		if (StringUtils.isNotBlank(searchCommand.getValue(ADMIN_VIEW)))
			params.setAdminView(searchCommand.getBooleanValue(ADMIN_VIEW));

		return params;
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/price", params = { "action=search" })
	public ModelAndView searchNavigationHandler(
			@RequestParam(value = CROP, required = false) String cropId,
			@RequestParam(value = SELLING_PLACE, required = false) String sellingPlaceId,
			@RequestParam(value = SELL_TYPE, required = false) String selltypeid,
			@RequestParam(value = FROM_DATE, required = false) String fromDate,
			@RequestParam(value = TO_DATE, required = false) String toDate,
			@RequestParam(value = ADMIN_VIEW, required = false) String adminview,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(CROP, new GenericCommandValue(cropId));
		command.getPropertiesMap().put(SELLING_PLACE,
				new GenericCommandValue(sellingPlaceId));
		command.getPropertiesMap().put(SELL_TYPE,
				new GenericCommandValue(selltypeid));

		command.getPropertiesMap().put(FROM_DATE,
				new GenericCommandValue(fromDate));
		command.getPropertiesMap()
				.put(TO_DATE, new GenericCommandValue(toDate));

		command.getPropertiesMap().put(ADMIN_VIEW,
				new GenericCommandValue(adminview));

		return searchHandler(command, pageNo, modelMap);
	}

	@RequestMapping(value = "/price/search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute("pricesearch") GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {
		PriceSearchParameters params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, pageNo, model);

		return new ModelAndView("viewPrice", model);
	}

	private void prepareSearchCommand(ModelMap modelMap,
			PriceSearchParameters params) {

		GenericCommand searchCommand = new GenericCommand();

		if (params.getCrop() != null) {
			searchCommand.getPropertiesMap().put(CROP,
					new GenericCommandValue(params.getCrop().getId()));
		}

		if (params.getSellingPlace() != null) {
			searchCommand.getPropertiesMap().put(SELLING_PLACE,
					new GenericCommandValue(params.getSellingPlace().getId()));
		}

		if (params.getSellType() != null) {
			searchCommand.getPropertiesMap().put(SELL_TYPE,
					new GenericCommandValue(params.getSellType().getId()));
		}

		if (params.getFromDate() != null) {
			searchCommand.getPropertiesMap().put(
					FROM_DATE,
					new GenericCommandValue(GenericCommandValue.dateFormat
							.format(params.getFromDate())));
		}

		if (params.getToDate() != null) {
			searchCommand.getPropertiesMap().put(
					TO_DATE,
					new GenericCommandValue(GenericCommandValue.dateFormat
							.format(params.getToDate())));
		}

		if (params.getFromDate() != null && params.getToDate() != null) {
			if (params.getToDate().before(params.getFromDate()))
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"Error, 'to' date is before 'from' date");
		}

		searchCommand.getPropertiesMap().put(ADMIN_VIEW,
				new GenericCommandValue(params.getAdminView().toString()));
		modelMap.put(ADMIN_VIEW, params.getAdminView());

		modelMap.put(COMMAND_NAME, searchCommand);

		modelMap.put("crops", cropService.getCrops());
		modelMap.put("sellingPlaces", sellingPlaceService.getSellingPlaces());

		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.SELLING_TYPE, "selltypes");
	}

	/**
	 * prepares the concept model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param modelMap
	 */
	private void prepareSearchModel(PriceSearchParameters params,
			Integer pageNo, ModelMap modelMap) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Price> prices = priceService.searchWithParams(params, pageNo);
		modelMap.put("prices", prices);
		modelMap.put(
				WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
				String.format("search completed with: %s result(s)",
						String.valueOf(prices.size())));

		WebUtils.prepareNavigation("Prices",
				priceService.numberInSearch(params), prices.size(), pageNo,
				buildSearchNavigationUrl(params), modelMap);

		prepareSearchCommand(modelMap, params);

		modelMap.put(WebConstants.CONTENT_HEADER, "Prices - search results ");

		// set a variable searching to true
		// this variable is used in determining what navigation file to use
		modelMap.put("searching", true);
	}

	/**
	 * builds a search navigation url based on the given concept search
	 * parameter object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildSearchNavigationUrl(PriceSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/price?action=search");

		if (params.getCrop() != null) {
			buffer.append("&").append(CROP).append("=")
					.append(params.getCrop().getId());
		}

		if (params.getSellingPlace() != null) {
			buffer.append("&").append(SELLING_PLACE).append("=")
					.append(params.getSellingPlace().getId());
		}

		if (params.getFromDate() != null) {
			buffer.append("&").append(FROM_DATE).append("=")
					.append(params.getFromDate().toString());
		}

		if (params.getToDate() != null) {
			buffer.append("&").append(TO_DATE).append("=")
					.append(params.getToDate().toString());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/view/admin/{adminview}/page/{pageNo}" }, method = RequestMethod.GET)
	public ModelAndView viewPriceHandler(ModelMap modelMap,
			@PathVariable(value = "pageNo") Integer pageNo,
			@PathVariable(value = "adminview") Boolean adminView) {

		PriceSearchParameters params = new PriceSearchParameters(adminView);
		prepareSearchModel(params, pageNo, modelMap);

		return new ModelAndView("viewPrice", modelMap);
	}

	// ================================

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/add/" }, method = RequestMethod.GET)
	public ModelAndView addPriceHandler(ModelMap modelMap) {

		List<Crop> crops = cropService.getCrops();
		if (crops != null && crops.size() > 0) {
			modelMap.put("crops", crops);
			modelMap.put(WebConstants.CONTENT_HEADER, "Add price ");
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No Crops found in the database");
			return viewPriceHandler(modelMap, 1, true);
		}

		return new ModelAndView("formPrice", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/add/{cropid}" }, method = RequestMethod.GET)
	public ModelAndView addPriceHandler2(@PathVariable("cropid") String cropid,
			ModelMap modelMap) {
		if (StringUtils.isNotBlank(cropid)) {
			Crop crop = cropService.getCropById(cropid);

			if (crop.getUnitsOfMeasure() == null
					|| crop.getUnitsOfMeasure().size() == 0) {
				modelMap.put(
						WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"No Units of measure found for Crop - "
								+ crop.getName());
				List<Crop> crops = cropService.getCrops();
				modelMap.put("crops", crops);
			} else {
				Price price = new Price(crop);
				preparePriceModel(price, modelMap);
				modelMap.put(WebConstants.CONTENT_HEADER, "Add price of "
						+ crop.getName());
			}
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No Crop selected");
			List<Crop> crops = cropService.getCrops();
			modelMap.put("crops", crops);
		}

		return new ModelAndView("formPrice", modelMap);
	}

	private void preparePriceModel(Price price, ModelMap modelMap) {

		Crop crop = price.getCrop();
		List<Crop> crops = cropService.getCrops();
		modelMap.put("crops", crops);

		modelMap.put("price", price);
		modelMap.put("unitOfMeasures", crop.getUnitsOfMeasure());

		modelMap.put("sellingPlaces", sellingPlaceService.getSellingPlaces());

		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.SELLING_TYPE, "selltypes");
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/edit/{pid}" }, method = RequestMethod.GET)
	public ModelAndView editPriceHandler(@PathVariable("pid") String priceId,
			ModelMap modelMap) {

		Price price = priceService.getPriceById(priceId);

		if (price != null) {
			preparePriceModel(price, modelMap);
			modelMap.put(WebConstants.CONTENT_HEADER, "Edit price of "
					+ price.getCrop().getName() + " in "
					+ price.getSellingPlace().getName());
			return new ModelAndView("formPrice", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid price id supplied");
		return viewPriceHandler(modelMap, 1, true);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/price/delete/{ids}")
	public ModelAndView deletePriceHandler(@PathVariable("ids") String ids,
			ModelMap modelMap) {

		String[] priceIdzToDelete = ids.split(",");
		try {
			priceService.deletePricesByIds(priceIdzToDelete);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Price(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewPriceHandler(modelMap, null, true);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/price/save/")
	public ModelAndView savePriceHandler(@ModelAttribute("price") Price price,
			ModelMap modelMap) {
		Price existingPrice = price;

		if (StringUtils.isNotEmpty(price.getId())) {
			existingPrice = priceService.getPriceById(price.getId());
			existingPrice.setCrop(price.getCrop());
			existingPrice.setSellingPlace(price.getSellingPlace());
			existingPrice.setSellType(price.getSellType());
			existingPrice.setPrice(price.getPrice());
			existingPrice.setUnitOfMeasure(price.getUnitOfMeasure());
		} else {
			existingPrice.setId(null);
		}

		try {
			priceService.validate(existingPrice);
			priceService.save(existingPrice);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Price saved sucessfully.");
		} catch (ValidationException e) {
			modelMap.put("price", price);
			preparePriceModel(price, modelMap);
			return new ModelAndView("formPrice", modelMap);
		}
		return viewPriceHandler(modelMap, 1, true);
	}
}
