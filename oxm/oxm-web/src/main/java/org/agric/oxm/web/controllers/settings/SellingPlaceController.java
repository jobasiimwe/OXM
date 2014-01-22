package org.agric.oxm.web.controllers.settings;

import java.util.List;

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
import org.agric.oxm.utils.OXMUtil;
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
@RequestMapping("sellingplace")
public class SellingPlaceController {

	@Autowired
	private SellingPlaceService sellingPlaceService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private Adminservice adminservice;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_SELLING_PLACE })
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(ModelMap model) {
		List<SellingPlace> sellingPrices = sellingPlaceService
				.getAll();
		try {
			WebConstants.loadLoggedInUserProfile(
					OXMSecurityUtil.getLoggedInUser(), model);
		} catch (SessionExpiredException e) {
			log.error("Error", e.getMessage());
		}
		model.put("sellingplaces", sellingPrices);
		model.put(WebConstants.CONTENT_HEADER, "Markets/Selling Places");
		return new ModelAndView("viewSellingPlace", model);

	}

	@Secured({ PermissionConstants.ADD_SELLING_PLACE })
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		model.put("sellingprice", new SellingPlace());
		prepareSellingPlaceModel(model);
		return new ModelAndView("formSellingPlace", model);

	}

	@Secured({ PermissionConstants.EDIT_SELLING_PLACE })
	@RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(ModelMap model,
			@PathVariable("id") String sPlaceId) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);

		SellingPlace sPlace = sellingPlaceService.getSellingPlaceById(sPlaceId);

		if (sPlace != null) {
			model.put("sellingprice", sPlace);
			prepareSellingPlaceModel(model);
			return new ModelAndView("formSellingPlace", model);
		}

		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid selling price id submitted");
		return view(model);

	}

	@Secured({ PermissionConstants.DELETE_SELLING_PLACE })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{ids}")
	public ModelAndView delete(
			@PathVariable("ids") String ids, ModelMap modelMap) {

		String[] sellingPlaceIdzToDelete = ids.split(",");
		try {
			sellingPlaceService
					.deleteSellingPlacesByIds(sellingPlaceIdzToDelete);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Selling Place(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return view(modelMap);
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

	@Secured({ PermissionConstants.ADD_SELLING_PLACE,
			PermissionConstants.EDIT_SELLING_PLACE })
	@RequestMapping(method = RequestMethod.POST, value = "save")
	public ModelAndView save(
			@ModelAttribute("sellingplace") SellingPlace sellingPlace,
			ModelMap model) throws SessionExpiredException {

		try {
			sellingPlaceService.validate(sellingPlace);

			SellingPlace existingSellingPlace = sellingPlace;

			if (StringUtils.isNotEmpty(sellingPlace.getId())) {
				existingSellingPlace = sellingPlaceService
						.getSellingPlaceById(sellingPlace.getId());
				existingSellingPlace.setName(sellingPlace.getName());
				// existingSellingPlace.setSellingTypes(sellingPlace
				// .getSellingTypes());
			} else {
				existingSellingPlace.setId(null);
			}

			sellingPlaceService.save(existingSellingPlace);
			model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Selling Place saved sucessfully.");
		} catch (ValidationException e) {
			model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			model.put("sellingprice", sellingPlace);
			prepareSellingPlaceModel(model);
			return new ModelAndView("formSellingPlace", model);
		}
		return view(model);
	}
}
