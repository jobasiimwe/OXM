package org.agric.oxm.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ConceptService;
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
public class SellingPlaceController {

	@Autowired
	private SellingPlaceService sellingPlaceService;

	@Autowired
	private ConceptService conceptService;
	
	@Autowired
	private Adminservice adminservice;

	@Secured({ PermissionConstants.VIEW_SELLING_PLACE})
	@RequestMapping(value = "/sellingplace/view/", method = RequestMethod.GET)
	public ModelAndView viewSellingPlaceHandler(ModelMap model)
			throws SessionExpiredException {
		List<SellingPlace> sellingPrices = sellingPlaceService
				.getSellingPlaces();
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("sellingplaces", sellingPrices);
		return new ModelAndView("viewSellingPlace", model);

	}
	
	@Secured({ PermissionConstants.ADD_SELLING_PLACE })
	@RequestMapping(value = "/sellingplace/add/", method = RequestMethod.GET)
	public ModelAndView addSellingPlaceHandler(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("sellingprice", new SellingPlace());
		prepareSellingPlaceModel(model);
		return new ModelAndView("addOREditSellingPlace", model);

	}

	@Secured({ PermissionConstants.EDIT_SELLING_PLACE })
	@RequestMapping(value = "/sellingplace/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editSellingPlaceHandler(ModelMap model,
			@PathVariable("id") String sPlaceId) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);

		SellingPlace sPlace = sellingPlaceService.getSellingPlaceById(sPlaceId);

		if (sPlace != null) {
			model.put("sellingprice", sPlace);
			prepareSellingPlaceModel(model);
			return new ModelAndView("addOREditSellingPlace", model);
		}

		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid selling price id submitted");
		return viewSellingPlaceHandler(model);

	}

	@Secured({ PermissionConstants.DELETE_SELLING_PLACE })
	@RequestMapping(method = RequestMethod.POST, value = "/sellingplace/delete/")
	public void deleteSellingPlaceHandler(
			@RequestParam("selectedSellingPlace") List<String> ids,
			HttpServletResponse response) {

		try {
			if (ids != null) {
				String[] sPlaceIds = new String[ids.size()];
				sPlaceIds = ids.toArray(sPlaceIds);

				sellingPlaceService.deleteSellingPlacesByIds(sPlaceIds);
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().write(
						"Selling place(s) deleted successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write(
						"no selling place(s) supplied for deleting");
			}
		} catch (IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void prepareSellingPlaceModel(ModelMap model) {
	    model.put("districts", adminservice.getDistricts());
		try {
			ConceptCategoryAnnotation typeRoleAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class,
							DefaultConceptCategories.SELLING_TYPE);

			if (typeRoleAnnotation != null) {
				ConceptCategory sellingTypeRole = conceptService
						.getConceptCategoryById(typeRoleAnnotation.id());

				if (sellingTypeRole != null) {
					model.put("sellingTypes", conceptService
							.getConceptsByCategory(sellingTypeRole));
				}
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
	}

	@Secured({ PermissionConstants.EDIT_SELLING_PLACE })
	@RequestMapping(method = RequestMethod.POST, value = "/sellingplace/save/")
	public ModelAndView saveSellingPlaceHandler(
			@ModelAttribute("sellingplace") SellingPlace sellingPlace,
			ModelMap model) throws SessionExpiredException {

		SellingPlace existingSellingPlace = sellingPlace;

		if (StringUtils.isNotEmpty(sellingPlace.getId())) {
			existingSellingPlace = sellingPlaceService
					.getSellingPlaceById(sellingPlace.getId());
			existingSellingPlace.setName(sellingPlace.getName());
			existingSellingPlace
					.setSellingTypes(sellingPlace.getSellingTypes());
		} else {
			existingSellingPlace.setId(null);
		}

		try {
			sellingPlaceService.validate(existingSellingPlace);
			sellingPlaceService.save(existingSellingPlace);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Selling Place saved sucessfully.");
		} catch (ValidationException e) {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			model.put("sellingprice", sellingPlace);
			prepareSellingPlaceModel(model);
			return new ModelAndView("addOREditSellingPlace", model);
		}
		return viewSellingPlaceHandler(model);
	}
}
