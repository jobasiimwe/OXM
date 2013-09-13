package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.Product;
import org.agric.oxm.model.Price;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PriceSearchParams;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.ProductService;
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
@RequestMapping("price")
public class PriceController {

	@Autowired
	private PriceService priceService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private SellingPlaceService sellingPlaceService;

	@Autowired
	private ApplicationController applicationController;

	public static final String PRODUCT = "product";
	public static final String SELLING_PLACE = "sellingplaceid";
	public static final String SELL_TYPE = "selltypeid";
	public static final String FROM_DATE = "fromdate";
	public static final String TO_DATE = "todate";
	public static final String ADMIN_VIEW = "adminview";

	private static final String COMMAND_NAME = "pricesearch";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private PriceSearchParams extractParams(GenericCommand searchCommand) {
		PriceSearchParams params = new PriceSearchParams();
		if (StringUtils.isNotBlank(searchCommand.getValue(PRODUCT))) {
			params.setProduct(productService.getById(searchCommand
					.getValue(PRODUCT)));
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

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(method = RequestMethod.GET, value = "price", params = { "action=search" })
	public ModelAndView navigate(
			@RequestParam(value = PRODUCT, required = false) String productId,
			@RequestParam(value = SELLING_PLACE, required = false) String sellingPlaceId,
			@RequestParam(value = SELL_TYPE, required = false) String selltypeid,
			@RequestParam(value = FROM_DATE, required = false) String fromDate,
			@RequestParam(value = TO_DATE, required = false) String toDate,
			@RequestParam(value = ADMIN_VIEW, required = false) String adminview,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap modelMap) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(PRODUCT,
				new GenericCommandValue(productId));
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

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {
		PriceSearchParams params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, pageNo, model);

		return new ModelAndView("priceView", model);
	}

	private void prepareSearchCommand(ModelMap modelMap,
			PriceSearchParams params) {

		GenericCommand searchCommand = new GenericCommand();

		if (params.getProduct() != null) {
			searchCommand.getPropertiesMap().put(PRODUCT,
					new GenericCommandValue(params.getProduct().getId()));
		}

		if (params.getSellingPlace() != null) {
			searchCommand.getPropertiesMap().put(SELLING_PLACE,
					new GenericCommandValue(params.getSellingPlace().getId()));
		}

		if (params.getSellType() != null) {
			searchCommand.getPropertiesMap().put(SELL_TYPE,
					new GenericCommandValue(params.getSellType().getId()));
		}

		searchCommand.checkAndPut(FROM_DATE, params.getFromDate());
		searchCommand.checkAndPut(TO_DATE, params.getToDate());

		if (params.getFromDate() != null && params.getToDate() != null)
			if (params.getToDate().before(params.getFromDate()))
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"Error, 'to' date is before 'from' date");

		searchCommand.getPropertiesMap().put(ADMIN_VIEW,
				new GenericCommandValue(params.getAdminView().toString()));
		modelMap.put(ADMIN_VIEW, params.getAdminView());

		modelMap.put(COMMAND_NAME, searchCommand);

		preparePriceModel(modelMap);
	}

	private void prepareSearchModel(PriceSearchParams params, Integer pageNo,
			ModelMap modelMap) {

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

	private String buildSearchNavigationUrl(PriceSearchParams params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/price?action=search");

		if (params.getProduct() != null) {
			buffer.append("&").append(PRODUCT).append("=")
					.append(params.getProduct().getId());
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

	@Secured({ PermissionConstants.VIEW_PRICE })
	@RequestMapping(value = { "view/admin/{adminview}" }, method = RequestMethod.GET)
	public ModelAndView view(ModelMap modelMap,
			@PathVariable(value = "adminview") Boolean adminView) {

		PriceSearchParams params = new PriceSearchParams(adminView);
		prepareSearchModel(params, 1, modelMap);

		return new ModelAndView("priceView", modelMap);
	}

	// =============== ================= ================ ===============

	@Secured({ PermissionConstants.ADD_PRICE })
	@RequestMapping(value = { "add" }, method = RequestMethod.GET)
	public ModelAndView add(ModelMap modelMap) {

		List<Product> products = productService.getAll();
		if (products == null || products.size() == 0) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No Products found in the database");
			return view(modelMap, true);
		}

		preparePriceModel(modelMap);

		return new ModelAndView("priceForm", modelMap);
	}

	@Secured({ PermissionConstants.ADD_PRICE })
	@RequestMapping(value = { "add/{productid}" }, method = RequestMethod.GET)
	public ModelAndView add2(@PathVariable("productid") String productid,
			ModelMap modelMap) {
		if (StringUtils.isNotBlank(productid)) {
			Product product = productService.getById(productid);

			if (product.getUnitsOfMeasure() == null
					|| product.getUnitsOfMeasure().size() == 0) {
				modelMap.put(
						WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"No Units of measure found for Product - "
								+ product.getName());

				preparePriceModel(modelMap);

			} else {
				Price price = new Price(product);
				preparePriceModel(price, modelMap);
				modelMap.put(WebConstants.CONTENT_HEADER, "Add price of "
						+ product.getName());
			}
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No Product selected");
			List<Product> products = productService.getAll();
			modelMap.put("products", products);
		}

		return new ModelAndView("priceForm", modelMap);
	}

	// =============== ================= ================ ===============

	private void preparePriceModel(ModelMap modelMap) {
		List<Product> products = productService.getAll();

		if (products == null || products.size() == 0)
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Ooops No Products found");
		modelMap.put("products", products);

		modelMap.put("sellingPlaces", sellingPlaceService.getSellingPlaces());

		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.SELLING_TYPE, "selltypes");
	}

	private void preparePriceModel(Price price, ModelMap modelMap) {
		modelMap.put("price", price);

		Product product = price.getProduct();
		modelMap.put("unitOfMeasures", product.getUnitsOfMeasure());

		preparePriceModel(modelMap);
	}

	// =============== ================= ================ ===============

	@Secured({ PermissionConstants.EDIT_PRICE })
	@RequestMapping(value = { "edit/{pid}" }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("pid") String priceId,
			ModelMap modelMap) {

		Price price = priceService.getPriceById(priceId);

		if (price != null) {
			preparePriceModel(price, modelMap);
			modelMap.put(WebConstants.CONTENT_HEADER, "Edit price of "
					+ price.getProduct().getName() + " in "
					+ price.getSellingPlace().getName());
			return new ModelAndView("formPrice", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid price id supplied");
		return view(modelMap, true);
	}

	@Secured({ PermissionConstants.DELETE_PRICE })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{ids}")
	public ModelAndView delete(@PathVariable("ids") String ids,
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

		return view(modelMap, true);
	}

	@Secured({ PermissionConstants.ADD_PRICE, PermissionConstants.EDIT_PRICE })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(@ModelAttribute("price") Price price,
			ModelMap modelMap) {
		Price existingPrice = price;

		if (StringUtils.isNotEmpty(price.getId())) {
			existingPrice = priceService.getPriceById(price.getId());
			existingPrice.setProduct(price.getProduct());
			existingPrice.setSellingPlace(price.getSellingPlace());
			existingPrice.setSellType(price.getSellType());
			existingPrice.setPrice(price.getPrice());
			existingPrice.setUnitOfMeasure(price.getUnitOfMeasure());
			existingPrice.setDate(price.getDate());
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
		return view(modelMap, true);
	}
}
