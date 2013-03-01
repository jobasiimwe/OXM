package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.ConceptCategory;
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
    PriceService priceService;

    @Autowired
    CropService cropService;

    @Autowired
    ConceptService conceptService;

    @Autowired
    SellingPlaceService sellingPlaceService;

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = { "/price/view/{pageNo}" }, method = RequestMethod.GET)
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
	model.put("price", new Price());
	preparePricModel(model);
	return new ModelAndView("formPrice", model);
    }

    private void preparePricModel(ModelMap model) {
	model.put("crops", cropService.getCrops());
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

	    ConceptCategoryAnnotation unitOfMeasureeRoleAnnotation = OXMUtil
		    .getConceptCategoryFieldAnnotation(
			    DefaultConceptCategories.class,
			    DefaultConceptCategories.UNITS_OF_MEASURE);

	    if (unitOfMeasureeRoleAnnotation != null) {
		ConceptCategory unitOfMeasure = conceptService
			.getConceptCategoryById(unitOfMeasureeRoleAnnotation.id());

		if (unitOfMeasure != null) {
		    model.put("unitOfMeasures", conceptService
			    .getConceptsByCategory(unitOfMeasure));
		}
	    }
	} catch (SecurityException e) {
	} catch (NoSuchFieldException e) {
	}
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(value = { "/price/edit/{pid}" }, method = RequestMethod.GET)
    public ModelAndView editPriceHandler(@PathVariable("pid") String priceId, ModelMap model) {

	Price price = priceService.getPriceById(priceId);

	if (price != null) {
	    model.put("price", price);
	    preparePricModel(model);
	    return new ModelAndView("formPrice", model);
	}

	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Invalid price id supplied");
	return viewPriceHandler(model, 1);
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(method = RequestMethod.POST, value = "/price/delete/")
    public void deletePriceHandler(@RequestParam("selectedPrice") List<String> ids,
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
		response.getWriter().write(
						"no Price(s) supplied for deleting");
	    }
	} catch (IOException e) {
	    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }

    @Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
    @RequestMapping(method = RequestMethod.POST, value = "/price/save/")
    public ModelAndView savePriceHandler(
	    @ModelAttribute("price") Price price,
			ModelMap model) {
	Price existingPrice = price;

	if (StringUtils.isNotEmpty(price.getId())) {
	    existingPrice = priceService.getPriceById(price.getId());
	    existingPrice.setCrop(price.getCrop());
	    existingPrice.setSellingPlace(price.getSellingPlace());
	    existingPrice.setSellType(price.getSellType());
	    existingPrice.setPrice(price.getPrice());
	    existingPrice.setUnitOfMeasure(price.getUnitOfMeasure());
	}else{
	    existingPrice.setId(null);
	}

	try {
	    priceService.validate(existingPrice);
	    priceService.save(existingPrice);
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "Price saved sucessfully.");
	} catch (ValidationException e) {
	    model.put("price", price);
	    preparePricModel(model);
	    return new ModelAndView("formPrice", model);
	}
	return viewPriceHandler(model, 1);
    }
}
