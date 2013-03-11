package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/view/page/{pageNo}" }, method = RequestMethod.GET)
	public ModelAndView viewPriceHandler(ModelMap model,
			@PathVariable(value = "pageNo") Integer pageNo) {
		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}
		model.put("prices", priceService.getPrices());
		return new ModelAndView("viewPrice", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/add/" }, method = RequestMethod.GET)
	public ModelAndView addPriceHandler(ModelMap model) {

		List<Crop> crops = cropService.getCrops();
		if (crops != null && crops.size() > 0) {
			model.put("crops", crops);
		} else {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No Crops found in the database");
			return viewPriceHandler(model, 1);
		}

		return new ModelAndView("formPrice", model);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/add/{cropid}" }, method = RequestMethod.GET)
	public ModelAndView addPriceHandler2(@PathVariable("cropid") String cropid,
			ModelMap model) {
		if (StringUtils.isNotBlank(cropid)) {
			Crop crop = cropService.getCropById(cropid);

			if (crop.getUnitsOfMeasure() == null
					|| crop.getUnitsOfMeasure().size() == 0) {
				model.put(
						WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"No Units of measure found for Crop - "
								+ crop.getName());
				List<Crop> crops = cropService.getCrops();
				model.put("crops", crops);
			} else {
				Price price = new Price(crop);
				preparePriceModel(price, model);
			}
		} else {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"No Crop selected");
			List<Crop> crops = cropService.getCrops();
			model.put("crops", crops);
		}

		return new ModelAndView("formPrice", model);
	}

	private void preparePriceModel(Price price, ModelMap model) {

		Crop crop = price.getCrop();
		List<Crop> crops = cropService.getCrops();
		model.put("crops", crops);

		model.put("price", price);
		model.put("unitOfMeasures", crop.getUnitsOfMeasure());

		model.put("sellingPlaces", sellingPlaceService.getSellingPlaces());
		try {
			ConceptCategoryAnnotation sellTypeRoleAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class,
							DefaultConceptCategories.SELLING_TYPE);

			if (sellTypeRoleAnnotation != null) {
				ConceptCategory sellingTypeRole = conceptService
						.getConceptCategoryById(sellTypeRoleAnnotation.id());

				if (sellingTypeRole != null) {
					model.put("sellingTypes", conceptService
							.getConceptsByCategory(sellingTypeRole));
				}
			}

		} catch (Exception e) {
			log.error("Error", e);
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = { "/price/edit/{pid}" }, method = RequestMethod.GET)
	public ModelAndView editPriceHandler(@PathVariable("pid") String priceId,
			ModelMap model) {

		Price price = priceService.getPriceById(priceId);

		if (price != null) {
			preparePriceModel(price, model);
			return new ModelAndView("formPrice", model);
		}

		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid price id supplied");
		return viewPriceHandler(model, 1);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/price/delete/")
	public void deletePriceHandler(
			@RequestParam("selectedPrice") List<String> ids,
			HttpServletResponse response) {
		try {
			if (ids != null) {
				String[] priceIds = new String[ids.size()];
				priceIds = ids.toArray(priceIds);

				priceService.deletePricesByIds(priceIds);
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write("Prices(s) deleted successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("no Price(s) supplied for deleting");
			}
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/price/save/")
	public ModelAndView savePriceHandler(@ModelAttribute("price") Price price,
			ModelMap model) {
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
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Price saved sucessfully.");
		} catch (ValidationException e) {
			model.put("price", price);
			preparePriceModel(price, model);
			return new ModelAndView("formPrice", model);
		}
		return viewPriceHandler(model, 1);
	}
}
