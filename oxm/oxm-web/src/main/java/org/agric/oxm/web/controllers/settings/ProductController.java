package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.Product;
import org.agric.oxm.model.search.ProductSearchParameters;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.server.service.ProductService;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.WebUtils;
import org.agric.oxm.web.controllers.ApplicationController;
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
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CropService cropService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private ApplicationController applicationController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String NAME_OR_DESCRIPTION = "nameordescription";
	public static final String CROP = "crop";

	private static final String COMMAND_NAME = "cropsearch";

	private void prepareSearchCommand(ModelMap model,
			ProductSearchParameters params) {
		GenericCommand searchCommand = new GenericCommand();

		if (StringUtils.isNotEmpty(params.getNameOrDescription()))
			searchCommand.getPropertiesMap().put(NAME_OR_DESCRIPTION,
					new GenericCommandValue(params.getNameOrDescription()));

		if (params.getCrop() != null) {
			searchCommand.getPropertiesMap().put(CROP,
					new GenericCommandValue(params.getCrop().getId()));
		}

		model.put(COMMAND_NAME, searchCommand);

		prepareProductModel(model);
	}

	/**
	 * 
	 * @param modelMap
	 */
	private void prepareProductModel(ModelMap modelMap) {
		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.UNITS_OF_MEASURE, "unitsOfMeasure");
		List<Crop> crops = cropService.getCrops();
		modelMap.put("crops", crops);
	}

	/**
	 * gets a list of products to be viewed
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.VIEW_PRODUCT })
	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public ModelAndView viewProductsHandler(ModelMap model) {
		prepareSearchModel(new ProductSearchParameters(), false, false, 1,
				model);
		return new ModelAndView("productView", model);
	}

	@Secured({ PermissionConstants.VIEW_PRODUCT })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		ProductSearchParameters params = extractParams(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareSearchModel(params, true, true, pageNo, model);

		return new ModelAndView("productView", model);
	}

	/**
	 * prepares the crop model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param model
	 */
	private void prepareSearchModel(ProductSearchParameters params,
			Boolean searching, Boolean newSearch, Integer pageNo, ModelMap model) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Product> products = productService
				.searchWithParams(params, pageNo);
		model.put("products", products);

		WebUtils.prepareNavigation("Product",
				productService.numberInSearch(params), products.size(), pageNo,
				buildSearchNavigationUrl(params), model);

		prepareSearchCommand(model, params);

		if (newSearch)
			model.put(
					WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					String.format("Search completed with: %s result(s)",
							String.valueOf(products.size())));
		if (searching) {
			model.put(WebConstants.CONTENT_HEADER, "Product - search results ");

		} else
			model.put(WebConstants.CONTENT_HEADER, "List of Crops");

	}

	/**
	 * builds a search navigation url based on the given search parameter
	 * object.
	 * 
	 * @param params
	 * @return
	 */
	private String buildSearchNavigationUrl(ProductSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/product?action=search");

		if (StringUtils.isNotBlank(params.getNameOrDescription())) {
			buffer.append("&").append(NAME_OR_DESCRIPTION).append("=")
					.append(params.getNameOrDescription());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_PRODUCT })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView userSearchNavigationHandler(
			@RequestParam(value = NAME_OR_DESCRIPTION, required = false) String nameOrDescription,
			@RequestParam(value = CROP, required = false) String crop,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(NAME_OR_DESCRIPTION,
				new GenericCommandValue(nameOrDescription));
		command.getPropertiesMap().put(CROP, new GenericCommandValue(crop));

		prepareSearchModel(extractParams(command), true, false, pageNo, model);

		return new ModelAndView("productView", model);
	}

	private ProductSearchParameters extractParams(GenericCommand searchCommand) {
		ProductSearchParameters params = new ProductSearchParameters();

		if (StringUtils.isNotBlank(searchCommand.getValue(NAME_OR_DESCRIPTION))) {
			params.setNameOrDescription(searchCommand
					.getValue(NAME_OR_DESCRIPTION));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(CROP))) {
			params.setCrop(cropService.getCropById(searchCommand.getValue(CROP)));
		}

		return params;
	}

	// ======================================================================================
	// ======================================================================================

	@Secured({ PermissionConstants.ADD_PRODUCT })
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap model) {

		Product product = new Product();
		model.put("product", product);
		model.put(WebConstants.CONTENT_HEADER, "Add new Product");
		prepareProductModel(model);

		return new ModelAndView("productForm", model);
	}

	@Secured({ PermissionConstants.EDIT_PRODUCT })
	@RequestMapping(value = "edit/{productid}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("productid") String productid,
			ModelMap model) {

		if (!model.containsAttribute("product")) {
			Product product = productService.getById(productid);
			model.put("product", product);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + product.getName());
		}

		prepareProductModel(model);

		return new ModelAndView("productForm", model);
	}

	@Secured({ PermissionConstants.EDIT_PRODUCT,
			PermissionConstants.ADD_PRODUCT })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(@ModelAttribute("product") Product product,
			ModelMap model) {

		try {

			productService.validate(product);

			Product exisitingProduct = product;
			if (StringUtils.isNotBlank(product.getId())
					|| StringUtils.isNotEmpty(product.getId())) {

				exisitingProduct = productService.getById(product.getId());

				exisitingProduct.setName(product.getName());
				exisitingProduct.setDescription(product.getDescription());
				exisitingProduct.setCrop(product.getCrop());
				exisitingProduct.setUnitsOfMeasure(product.getUnitsOfMeasure());
				exisitingProduct.setRecordStatus(product.getRecordStatus());
			} else {
				exisitingProduct.setId(null);
			}

			productService.save(exisitingProduct);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Product changes are saved successful");
			return viewProductsHandler(model);
		} catch (Exception e) {
			log.error("Error", e);

			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			model.put("product", product);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + product.getName());
			return edit(product.getId(), model);

		}

	}

	/**
	 * Handles deleting of products
	 * 
	 * @param productIds
	 * @param model
	 * @return
	 */
	@Secured({ PermissionConstants.DELETE_PRODUCT })
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{productIds}")
	public ModelAndView delete(@PathVariable("productIds") String productIds,
			ModelMap model) {
		String[] productIdzToDelete = productIds.split(",");

		try {
			productService.deleteByIds(productIdzToDelete);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Product  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
		}

		return viewProductsHandler(model);
	}

}
