package org.agric.oxm.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.search.CropSearchParameters;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.web.OXMUtil;
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
@RequestMapping("crop")
public class CropController {

	@Autowired
	private CropService cropService;

	@Autowired
	private ConceptService conceptService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String PARAM_NAME = "name";
	public static final String PARAM_INPUT = "input";
	public static final String PARAM_SEED_VARIATION = "seedvariation";
	public static final String PARAM_PLOUGHING_METHOD = "ploughingmethod";
	public static final String PARAM_INTER_CROPING_TYPE = "intercropingtype";

	private static final String COMMAND_NAME = "cropsearch";

	private void prepareCropSearchCommand(ModelMap model,
			CropSearchParameters params) {
		GenericCommand searchCommand = new GenericCommand();

		if (StringUtils.isNotEmpty(params.getName()))
			searchCommand.getPropertiesMap().put(CropController.PARAM_NAME,
					new GenericCommandValue(params.getName()));

		if (params.getInput() != null) {
			searchCommand.getPropertiesMap().put(CropController.PARAM_INPUT,
					new GenericCommandValue(params.getInput().getId()));
		}

		if (params.getSeedVariation() != null) {
			searchCommand.getPropertiesMap().put(
					CropController.PARAM_SEED_VARIATION,
					new GenericCommandValue(params.getSeedVariation().getId()));
		}

		if (null != params.getPloughingMethod())
			searchCommand.getPropertiesMap()
					.put(CropController.PARAM_PLOUGHING_METHOD,
							new GenericCommandValue(params.getPloughingMethod()
									.getId()));

		if (params.getInterCropingType() != null) {
			searchCommand.getPropertiesMap().put(
					CropController.PARAM_INTER_CROPING_TYPE,
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

		List<Crop> crops = cropService.getCrops();

		List<ConceptCategory> inputConceptCategories = new ArrayList<ConceptCategory>();
		List<ConceptCategory> seedVariationConceptCategories = new ArrayList<ConceptCategory>();
		List<ConceptCategory> ploughingMethodConceptCategories = new ArrayList<ConceptCategory>();
		List<ConceptCategory> interCropingTypeConceptCategories = new ArrayList<ConceptCategory>();

		for (Crop crop : crops) {
			if (crop.getInput() != null)
				inputConceptCategories.add(crop.getInput());

			if (crop.getSeedVariation() != null)
				seedVariationConceptCategories.add(crop.getSeedVariation());

			if (crop.getPloughingMethod() != null)
				ploughingMethodConceptCategories.add(crop.getPloughingMethod());

			if (crop.getInterCropingType() != null)
				interCropingTypeConceptCategories.add(crop.getInput());
		}

		List<Concept> inputs = conceptService
				.getConceptsByCategories(inputConceptCategories);
		List<Concept> seedVariations = conceptService
				.getConceptsByCategories(seedVariationConceptCategories);
		List<Concept> ploughingMethods = conceptService
				.getConceptsByCategories(ploughingMethodConceptCategories);
		List<Concept> interCropingTypes = conceptService
				.getConceptsByCategories(interCropingTypeConceptCategories);

		modelMap.put("inputs", inputs);
		modelMap.put("seedVariations", seedVariations);
		modelMap.put("ploughingMethods", ploughingMethods);
		modelMap.put("interCropingTypes", interCropingTypes);

		try {
			ConceptCategoryAnnotation unitsOfMeasureCategoryAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class,
							DefaultConceptCategories.UNITS_OF_MEASURE);
			if (unitsOfMeasureCategoryAnnotation != null) {
				List<Concept> unitsOfMeasure = conceptService
						.getConceptsByCategoryAnnotation(unitsOfMeasureCategoryAnnotation);
				modelMap.put("unitsOfMeasure", unitsOfMeasure);
			}

		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}
	}

	/**
	 * gets a list of crops to be viewed
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/view/page/{pageNo}" }, method = RequestMethod.GET)
	public ModelAndView viewCropsHandler(
			@PathVariable(value = "pageNo") Integer pageNo, ModelMap model) {
		prepareCropSearchModel(new CropSearchParameters(), false, false,
				pageNo, model);
		model.put(WebConstants.CONTENT_HEADER, "Crops");

		return new ModelAndView("viewCrop", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView courseSearchHandler(
			@ModelAttribute(COMMAND_NAME) GenericCommand searchCommand,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		CropSearchParameters params = extractCropSearchParamsFromCommand(searchCommand);
		if (pageNo == null || pageNo <= 0) {
			pageNo = 1;
		}

		prepareCropSearchModel(params, true, true, pageNo, model);

		return new ModelAndView("viewCourses", model);
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

		WebUtils.prepareNavigation("Course",
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
			buffer.append("&").append(PARAM_NAME).append("=")
					.append(params.getName());
		}

		if (params.getInput() != null) {
			buffer.append("&").append(PARAM_INPUT).append("=")
					.append(params.getInput().getId());
		}

		if (params.getPloughingMethod() != null) {
			buffer.append("&").append(PARAM_PLOUGHING_METHOD).append("=")
					.append(params.getPloughingMethod().getId());
		}

		if (params.getInterCropingType() != null) {
			buffer.append("&").append(PARAM_INTER_CROPING_TYPE).append("=")
					.append(params.getInterCropingType().getId());
		}

		return buffer.toString();
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(method = RequestMethod.GET, params = { "action=search" })
	public ModelAndView userSearchNavigationHandler(
			@RequestParam(value = CropController.PARAM_NAME, required = false) String name,
			@RequestParam(value = CropController.PARAM_INPUT, required = false) String inputId,
			@RequestParam(value = CropController.PARAM_SEED_VARIATION, required = false) String seedVariationId,
			@RequestParam(value = CropController.PARAM_PLOUGHING_METHOD, required = false) String ploughingMethodId,
			@RequestParam(value = CropController.PARAM_INTER_CROPING_TYPE, required = false) String interCropingTypeId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			ModelMap model) {

		GenericCommand command = new GenericCommand();

		command.getPropertiesMap().put(CropController.PARAM_NAME,
				new GenericCommandValue(name));
		command.getPropertiesMap().put(CropController.PARAM_INPUT,
				new GenericCommandValue(inputId));
		command.getPropertiesMap().put(CropController.PARAM_SEED_VARIATION,
				new GenericCommandValue(seedVariationId));
		command.getPropertiesMap().put(CropController.PARAM_PLOUGHING_METHOD,
				new GenericCommandValue(ploughingMethodId));

		command.getPropertiesMap().put(CropController.PARAM_INTER_CROPING_TYPE,
				new GenericCommandValue(interCropingTypeId));

		prepareCropSearchModel(extractCropSearchParamsFromCommand(command),
				true, false, pageNo, model);

		return new ModelAndView("viewCrops", model);
	}

	private CropSearchParameters extractCropSearchParamsFromCommand(
			GenericCommand searchCommand) {
		CropSearchParameters params = new CropSearchParameters();

		if (StringUtils.isNotBlank(searchCommand.getValue(PARAM_NAME))) {
			params.setName(searchCommand.getValue(PARAM_NAME));
		}

		if (StringUtils.isNotBlank(searchCommand.getValue(PARAM_INPUT))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(PARAM_INPUT)));
		}

		if (StringUtils
				.isNotBlank(searchCommand.getValue(PARAM_SEED_VARIATION))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(PARAM_SEED_VARIATION)));
		}

		if (StringUtils.isNotBlank(searchCommand
				.getValue(PARAM_PLOUGHING_METHOD))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(PARAM_PLOUGHING_METHOD)));
		}

		if (StringUtils.isNotBlank(searchCommand
				.getValue(PARAM_INTER_CROPING_TYPE))) {
			params.setInput(conceptService.getConceptById(searchCommand
					.getValue(PARAM_INTER_CROPING_TYPE)));
		}
		return params;
	}

	@Secured({ PermissionConstants.VIEW_CROP })
	@RequestMapping(value = "/details/{cropid}", method = RequestMethod.GET)
	public ModelAndView viewCropDetailsHandler(
			@PathVariable("cropid") String cropid, ModelMap model) {

		if (!model.containsAttribute("crop")) {
			Crop crop = cropService.getCropById(cropid);
			model.put("crop", crop);
			model.put(WebConstants.CONTENT_HEADER, crop.getName() + " Details");
		}

		prepareCropModel(model);

		return new ModelAndView("cropDetails", model);
	}

	@Secured({ PermissionConstants.ADD_CROP })
	@RequestMapping(value = "/edit/{cropid}", method = RequestMethod.GET)
	public ModelAndView editCropHandler(@PathVariable("cropid") String cropid,
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
	public ModelAndView saveCropHandler(@ModelAttribute("crop") Crop crop,
			ModelMap model) {

		try {

			cropService.validate(crop);

			Crop exisitingCrop = crop;
			if (StringUtils.isNotBlank(crop.getId())
					|| StringUtils.isNotEmpty(crop.getId())) {

				exisitingCrop = cropService.getCropById(crop.getId());

				if (!exisitingCrop.getName().equals(crop.getName())) {
					exisitingCrop.setName(crop.getName());
					exisitingCrop.getInput()
							.setName(crop.getName() + " Imputs");
					exisitingCrop.getInput().setDescription(
							"Inputs for " + crop.getName());

					exisitingCrop.getPloughingMethod().setName(
							crop.getName() + " Ploughing Methods");
					exisitingCrop.getInput().setDescription(
							"Ploughing Methods for " + crop.getName());

					exisitingCrop.getSeedVariation().setName(
							crop.getName() + " Seed Varieties");
					exisitingCrop.getSeedVariation().setDescription(
							"Seed Varieties for " + crop.getName());

					exisitingCrop.getInterCropingType().setName(
							crop.getName() + " Inter-croping types");
					exisitingCrop.getInterCropingType().setDescription(
							"Inter-croping types for " + crop.getName());

					exisitingCrop.setInterCropingType(new ConceptCategory(crop
							.getName() + " Inter-croping types",
							"Inter-croping types for " + crop.getName()));
				}
				exisitingCrop.setUnitsOfMeasure(crop.getUnitsOfMeasure());
				exisitingCrop.setRecordStatus(crop.getRecordStatus());
			} else {
				exisitingCrop.setId(null);
				exisitingCrop.setInput(new ConceptCategory(crop.getName()
						+ " Imputs", "Inputs for " + crop.getName()));
				exisitingCrop.setPloughingMethod(new ConceptCategory(crop
						.getName() + " Ploughing Methods",
						"Ploughing Methods for " + crop.getName()));
				exisitingCrop.setSeedVariation(new ConceptCategory(crop
						.getName() + " Seed Varieties", "Seed Varieties "
						+ crop.getName()));
				exisitingCrop.setInterCropingType(new ConceptCategory(crop
						.getName() + " Inter-croping types",
						"Inter-croping types for " + crop.getName()));
			}

			cropService.save(exisitingCrop);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Crop changes are saved successful");
			return viewCropsHandler(1, model);
		} catch (Exception e) {
			log.error("Error", e);

			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			model.put("crop", crop);
			model.put(WebConstants.CONTENT_HEADER, "Edit " + crop.getName());
			return editCropHandler(crop.getId(), model);

		}

	}

	@Secured({ PermissionConstants.ADD_CROP })
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addCropHandler(ModelMap model) {

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
	public ModelAndView deleteCropHandler(
			@PathVariable("cropIds") String cropIds, ModelMap model) {
		String[] cropIdzToDelete = cropIds.split(",");

		try {
			cropService.deleteCropsByIds(cropIdzToDelete);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Crop  deleted successfully");
			return viewCropsHandler(1, model);

		} catch (Exception e) {
			log.error("Error", e);
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
			return viewCropsHandler(1, model);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/crop/details/view/{cropId}", method = RequestMethod.GET)
	public ModelAndView viewCropOptionHandler(
			@PathVariable("cropId") String cropId, ModelMap model) {

		Crop crop = cropService.getCropById(cropId);
		model.put("crop", crop);

		String header = "Details of " + crop.getName();
		model.put("contentHeader", header);

		return new ModelAndView("viewCropDetails", model);

	}
}
