package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.Product;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.CropSearchParameters;
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
@RequestMapping("crop")
public class CropController {

	@Autowired
	private CropService cropService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private ApplicationController applicationController;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String NAME = "name";
	public static final String INPUT = "input";
	public static final String SEED_VARIATION = "seedvariation";
	public static final String PLOUGHING_METHOD = "ploughingmethod";
	public static final String INTER_CROPING_TYPE = "intercropingtype";

	private static final String COMMAND_NAME = "cropsearch";

	private void prepareCropSearchCommand(ModelMap model,
			CropSearchParameters params) {
		GenericCommand searchCommand = new GenericCommand();

		if (StringUtils.isNotEmpty(params.getName()))
			searchCommand.getPropertiesMap().put(NAME,
					new GenericCommandValue(params.getName()));

		if (params.getInput() != null) {
			searchCommand.getPropertiesMap().put(INPUT,
					new GenericCommandValue(params.getInput().getId()));
		}

		if (params.getSeedVariation() != null) {
			searchCommand.getPropertiesMap().put(SEED_VARIATION,
					new GenericCommandValue(params.getSeedVariation().getId()));
		}

		if (null != params.getPloughingMethod())
			searchCommand.getPropertiesMap()
					.put(PLOUGHING_METHOD,
							new GenericCommandValue(params.getPloughingMethod()
									.getId()));

		if (params.getInterCropingType() != null) {
			searchCommand.getPropertiesMap().put(
					INTER_CROPING_TYPE,
					new GenericCommandValue(params.getInterCropingType()
							.getId()));
		}

		model.put(CropController.COMMAND_NAME, searchCommand);

		prepareCropModel(model);
	}

	/**
	 * 
	 * @param modelMap
	 */
	private void prepareCropModel(ModelMap modelMap) {
		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.CROP_INPUTS, "cropInputs");
		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.INTER_CROPING_TYPES,
				"interCropingTypes");
		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.PLOUGHING_METHODS, "ploughingMethods");
		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.SEED_VARIETIES, "seedVarieties");
		applicationController.addConceptsToModelMap(modelMap,
				DefaultConceptCategories.UNITS_OF_MEASURE, "unitsOfMeasure");
	}

	/**
	 * gets a list of crops to be viewed
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(value = { "/view" }, method = RequestMethod.GET)
	public ModelAndView view(ModelMap model) {
		prepareCropSearchModel(new CropSearchParameters(), false, false, 1,
				model);
		model.put(WebConstants.CONTENT_HEADER, "Crops");

		return new ModelAndView("viewCrop", model);
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		CropSearchParameters params = extractCropSearchParamsFromCommand(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareCropSearchModel(params, true, true, pageNo, model);

		return new ModelAndView("viewCrop", model);
	}

	/**
	 * prepares the crop model for a search operation
	 * 
	 * @param params
	 * @param pageNo
	 * @param model
	 */
	private void prepareCropSearchModel(CropSearchParameters params,
			Boolean searching, Boolean newSearch, Integer pageNo, ModelMap model) {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		List<Crop> crops = cropService.searchWithParams(params, pageNo);
		model.put("crops", crops);

		WebUtils.prepareNavigation("Crop",
				cropService.numberOfCropsWithSearchParams(params),
				crops.size(), pageNo, buildCropSearchNavigationUrl(params),
				model);

		prepareCropSearchCommand(model, params);

		if (newSearch)
			model.put(
					WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					String.format("search completed with: %s result(s)",
							String.valueOf(crops.size())));
		if (searching) {
			model.put(WebConstants.CONTENT_HEADER, "Crops - search results ");

			// set a variable searching to true
			// this variable is used in determining what navigation file to use
			model.put("searching", true);
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
	private String buildCropSearchNavigationUrl(CropSearchParameters params) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("/crop?action=search");

		if (StringUtils.isNotBlank(params.getName())) {
			buffer.append("&").append(NAME).append("=")
					.append(params.getName());
		}

		if (params.getInput() != null) {
			buffer.append("&").append(INPUT).append("=")
					.append(params.getInput().getId());
		}

		if (params.getPloughingMethod() != null) {
			buffer.append("&").append(PLOUGHING_METHOD).append("=")
					.append(params.getPloughingMethod().getId());
		}

		if (params.getInterCropingType() != null) {
			buffer.append("&").append(INTER_CROPING_TYPE).append("=")
					.append(params.getInterCropingType().getId());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView userSearchNavigationHandler(
			@RequestParam(value = NAME, required = false) String name,
			@RequestParam(value = INPUT, required = false) String inputId,
			@RequestParam(value = SEED_VARIATION, required = false) String seedVariationId,
			@RequestParam(value = PLOUGHING_METHOD, required = false) String ploughingMethodId,
			@RequestParam(value = INTER_CROPING_TYPE, required = false) String interCropingTypeId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(NAME, new GenericCommandValue(name));
		command.getPropertiesMap().put(INPUT, new GenericCommandValue(inputId));
		command.getPropertiesMap().put(SEED_VARIATION,
				new GenericCommandValue(seedVariationId));
		command.getPropertiesMap().put(PLOUGHING_METHOD,
				new GenericCommandValue(ploughingMethodId));

		command.getPropertiesMap().put(INTER_CROPING_TYPE,
				new GenericCommandValue(interCropingTypeId));

		prepareCropSearchModel(extractCropSearchParamsFromCommand(command),
				true, false, pageNo, model);

		return new ModelAndView("viewCrops", model);
	}

	private CropSearchParameters extractCropSearchParamsFromCommand(
			GenericCommand searchCommand) {
		CropSearchParameters params = new CropSearchParameters();

		if (StringUtils.isNotBlank(searchCommand.getValue(NAME))) {
			params.setName(searchCommand.getValue(NAME));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(INPUT))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(INPUT)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(SEED_VARIATION))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(SEED_VARIATION)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(PLOUGHING_METHOD))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(PLOUGHING_METHOD)));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(INTER_CROPING_TYPE))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(INTER_CROPING_TYPE)));
		}
		return params;
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(value = "/details/{cropid}", method = RequestMethod.GET)
	public ModelAndView details(@PathVariable("cropid") String cropid,
			ModelMap model) {

		Crop crop = cropService.getCropById(cropid);
		model.put("crop", crop);

		String header = "Details of " + crop.getName();
		model.put("contentHeader", header);

		return new ModelAndView("viewCropDetails", model);
	}

	@Secured({ PermissionConstants.EDIT_CROP })
	@RequestMapping(value = "/edit/{cropid}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("cropid") String cropid,
			ModelMap model) {

		if (!model.containsAttribute("crop")) {
			Crop crop = cropService.getCropById(cropid);
			model.put("crop", crop);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + crop.getName());
		}

		prepareCropModel(model);

		return new ModelAndView("cropForm", model);
	}

	@Secured({ PermissionConstants.EDIT_CROP, PermissionConstants.ADD_CROP })
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ModelAndView save(@ModelAttribute("crop") Crop crop, ModelMap model) {

		try {

			cropService.validate(crop);

			Crop exisitingCrop = crop;
			if (StringUtils.isNotBlank(crop.getId())
					|| StringUtils.isNotEmpty(crop.getId())) {

				exisitingCrop = cropService.getCropById(crop.getId());

				exisitingCrop.setName(crop.getName());
				exisitingCrop.setInputs(crop.getInputs());
				exisitingCrop.setSeedVarieties(crop.getSeedVarieties());
				exisitingCrop.setInterCroppingTypes(crop
						.getInterCroppingTypes());
				exisitingCrop.setUnitsOfMeasure(crop.getUnitsOfMeasure());
				exisitingCrop.setRecordStatus(crop.getRecordStatus());
			} else {
				exisitingCrop.setId(null);
			}

			cropService.save(exisitingCrop);

			saveDefaultProduct(exisitingCrop);

			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Crop changes are saved successful");
			return view(model);
		} catch (Exception e) {
			log.error("Error", e);

			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			model.put("crop", crop);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + crop.getName());
			return edit(crop.getId(), model);

		}

	}

	private void saveDefaultProduct(Crop crop) throws ValidationException {
		List<Product> products = productService.getBy(crop);
		if (products == null || products.size() == 0) {
			Product product = new Product();
			product.generateDefaultProduct(crop);
			
			productService.validate(product);
			productService.save(product);
		}
	}

	@Secured({ PermissionConstants.ADD_CROP })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap model) {

		Crop crop = new Crop();
		model.put("crop", crop);
		model.put(WebConstants.CONTENT_HEADER, "Add new Crop");
		prepareCropModel(model);

		return new ModelAndView("cropForm", model);
	}

	/**
	 * Handles deleting of crops
	 * 
	 * @param cropIds
	 * @param model
	 * @return
	 */
	@Secured({ PermissionConstants.DELETE_CROP })
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{cropIds}")
	public ModelAndView delete(@PathVariable("cropIds") String cropIds,
			ModelMap model) {
		String[] cropIdzToDelete = cropIds.split(",");

		try {
			cropService.deleteCropsByIds(cropIdzToDelete);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Crop  deleted successfully");
			return view(model);

		} catch (Exception e) {
			log.error("Error", e);
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			return view(model);
		}
	}

}
