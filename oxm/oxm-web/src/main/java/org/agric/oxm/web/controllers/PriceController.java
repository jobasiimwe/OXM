package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.Price;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.CropService;
import org.agric.oxm.server.service.PriceService;
import org.agric.oxm.server.service.SellingPlaceService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
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

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/view/page/{pageNo}" }, method = RequestMethod.GET)
	public ModelAndView viewPriceHandler(ModelMap modelMap,
			@PathVariable(value = "pageNo") Integer pageNo) {
		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}
		modelMap.put("prices", priceService.getPrices());

		modelMap.put(WebConstants.CONTENT_HEADER, "List of Prices");
		return new ModelAndView("viewPrice", modelMap);
	}

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
			return viewPriceHandler(modelMap, 1);
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
		try {
			ConceptCategoryAnnotation sellTypeRoleAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class,
							DefaultConceptCategories.SELLING_TYPE);

			if (sellTypeRoleAnnotation != null) {
				ConceptCategory sellingTypeRole = conceptService
						.getConceptCategoryById(sellTypeRoleAnnotation.id());

				if (sellingTypeRole != null) {
					modelMap.put("sellingTypes", conceptService
							.getConceptsByCategory(sellingTypeRole));
				}
			}

		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}
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
		return viewPriceHandler(modelMap, 1);
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

		return viewPriceHandler(modelMap, null);
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
		return viewPriceHandler(modelMap, 1);
	}
}
