package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.Village;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.web.WebConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DistrictController {

    @Autowired
    private Adminservice adminService;

    @RequestMapping(value = "/district/view/", method = RequestMethod.GET)
    public ModelAndView viewDistrictHandler(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	model.put("district", new District());
	model.put("districts", adminService.getDistricts());
	return new ModelAndView("viewDistrict", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/district/add/")
    public ModelAndView viewAddDistrictHandler(ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	model.put("district", new District());
	return new ModelAndView("addOREditDistrict", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/district/edit/{id}")
    public ModelAndView viewEditDistrictHandler(@PathVariable("id") String districtId,
	    ModelMap model) throws SessionExpiredException {
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	District district = adminService.getDistrictById(districtId);
	if (district != null) {
	    model.put("district", district);
	    return new ModelAndView("addOREditDistrict", model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the id of the district supplied doesn't match a district in the system");
	    return new ModelAndView("viewDistrict", model);
	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/district/save/")
    public ModelAndView saveDistrictHandler(@ModelAttribute("district") District district,
	    ModelMap model) throws SessionExpiredException {

	if (district != null) {
	    try {

		District existingDistrict = district;

		if (StringUtils.isNotEmpty(district.getId())) {
		    existingDistrict = adminService.getDistrictById(district.getId());
		    existingDistrict.setName(district.getName());
		}

		adminService.validate(existingDistrict);
		adminService.save(existingDistrict);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"district saved successfully");
	    } catch (ValidationException ex) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
			ex.getMessage());
	    }
	}

	return viewDistrictHandler(model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/district/delete/{id}")
    public ModelAndView deleteDistrictHandler(@PathVariable("id") String districtId,
	    ModelMap model) throws SessionExpiredException {
	District district = adminService.getDistrictById(districtId);
	if (district != null) {
	    adminService.deleteDistrict(districtId);
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "District >> " + district.getName()
			    + " deleted successfully");
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the district id supplied does not belong to a valid district in the system");
	}

	return viewDistrictHandler(model);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/district/search")
    public ModelAndView searchDistrict(
	    @RequestParam(value = "pageNo", required = false) Integer pageNo,
	    @RequestParam(WebConstants.SEARCH_QUERY_REQUEST_PARAMETER_NAME) String query,
	    ModelMap model) throws SessionExpiredException {
	model.put("query", query);

	if (pageNo == null || (pageNo != null && pageNo <= 0)) {
	    pageNo = 1;
	}

	if (query.equals("")) {
	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "No search query!");
	    return viewDistrictHandler(model);
	} else {

	    List<District> districts = adminService.searchDistrict(query);

	    if (districts != null && districts.size() == 0) {
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"search for query >> \"" + query
				+ "\" completed with no results");
	    } else {

		if (districts != null && districts.size() > 0) {
		    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
				"search for query >> \""
					+ query
					+ "\" completed successfully with the following results");
		}
		model.put("districts", districts);

	    }
	}
	return new ModelAndView("viewDistrict", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subcounty/add/{did}")
    public ModelAndView viewAddSubCountyHandler(@PathVariable("did") String districtId,
	    ModelMap model) throws SessionExpiredException {
	District district = adminService.getDistrictById(districtId);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	if (district != null) {
	    SubCounty subCounty = new SubCounty();
	    subCounty.setDistrict(district);
	    model.put("district", district);
	    model.put("subcounty", subCounty);
	    return new ModelAndView("addOREditSubCounty", model);
	}

	return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subcounty/edit/{sid}")
    public ModelAndView viewEditSubCountyHandler(@PathVariable("sid") String subCountyId,
	    ModelMap model) throws SessionExpiredException {
	SubCounty subCounty = adminService.getSubCountyById(subCountyId);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	if (subCounty != null) {
	    model.put("subcounty", subCounty);
	    model.put("district", subCounty.getDistrict());
	    return new ModelAndView("addOREditSubCounty", model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the id of the subcounty supplied doesn't match a district in the system");
	    return viewSubCountyHandler(null, null, model);
	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/subcounty/save/{did}")
    public ModelAndView saveSubCountyHandler(@ModelAttribute("subCounty") SubCounty subCounty,
	    @PathVariable("did") String districtId,
	    ModelMap model) throws SessionExpiredException {
	SubCounty existingSubcounty = null;
	if (subCounty != null) {
	    try {

		existingSubcounty = subCounty;

		if (StringUtils.isNotEmpty(subCounty.getId())) {
		    existingSubcounty = adminService.getSubCountyById(subCounty.getId());
		    existingSubcounty.setName(subCounty.getName());
		    existingSubcounty.setDistrict(subCounty.getDistrict());
		} else {
		    existingSubcounty.setId(null);
		}

		adminService.validate(existingSubcounty);
		adminService.save(existingSubcounty);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"subcounty saved successfully");

	    } catch (ValidationException ex) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
			ex.getMessage());
	    }
	}

	return viewSubCountyHandler(1, existingSubcounty.getDistrict().getId(), model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subcounty/delete/{id}")
    public ModelAndView deleteSubCountyHandler(@PathVariable("id") String subcountyId,
	    ModelMap model) throws SessionExpiredException {
	SubCounty subCounty = adminService.getSubCountyById(subcountyId);
	if (subCounty != null) {
	    subCounty.getDistrict().removeSubCounty(subCounty);
	    adminService.deleteSubCounty(subcountyId);

	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "District >> " + subCounty.getName()
			    + " deleted successfully");
	    return viewSubCountyHandler(null, subCounty.getDistrict().getId(), model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the subcounty id supplied does not belong to a valid district in the system");
	}

	return viewSubCountyHandler(null, null, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subcounty/view/{did}")
    public ModelAndView viewSubCountyHandler(
	    @RequestParam(value = "pageNo", required = false) Integer pageNo,
	    @PathVariable("did") String districtId, ModelMap model) throws SessionExpiredException {

	if (pageNo == null || (pageNo != null && pageNo <= 0)) {
	    pageNo = 1;
	}

	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);

	District district = adminService.getDistrictById(districtId);
	if (district != null) {
	    model.put("district", district);
	    model.put("subcounties", district.getSubCounties());
	    return new ModelAndView("viewSubCounty", model);
	}
	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "supplied district does not exist");
	return viewDistrictHandler(model);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/parish/add/{scid}")
    public ModelAndView addParishHandler(@PathVariable("scid") String subCountyId,
	    ModelMap model) throws SessionExpiredException {
	SubCounty subCounty = adminService.getSubCountyById(subCountyId);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	if (subCounty != null) {
	    Parish parish = new Parish();
	    parish.setSubCounty(subCounty);
	    model.put("subCounty", parish.getSubCounty());
	    model.put("parish", parish);
	    return new ModelAndView("addOREditParish", model);
	}

	return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/parish/edit/{pid}")
    public ModelAndView editParishHandler(@PathVariable("pid") String parishID,
	    ModelMap model) throws SessionExpiredException {
	Parish parish = adminService.getParishById(parishID);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	if (parish != null) {
	    model.put("subCounty", parish.getSubCounty());
	    model.put("parish", parish);
	    return new ModelAndView("addOREditParish", model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the id of the parish supplied doesn't match a district in the system");
	    return viewSubCountyHandler(null, null, model);
	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/parish/save/{scid}")
    public ModelAndView saveParishHandler(@ModelAttribute("parish") Parish parish,
	    @PathVariable("scid") String subCountyId,
	    ModelMap model) throws SessionExpiredException {
	Parish existingParish = null;

	SubCounty sbCounty = adminService.getSubCountyById(subCountyId);
	if (parish != null) {
	    try {

		existingParish = parish;

		if (StringUtils.isNotEmpty(parish.getId())) {
		    existingParish = adminService.getParishById(parish.getId());
		    existingParish.setName(parish.getName());
		    existingParish.setSubCounty(sbCounty);
		} else {
		    existingParish.setId(null);
		    existingParish.setSubCounty(sbCounty);
		}

		adminService.validate(existingParish);
		adminService.save(existingParish);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"parish saved successfully");

	    } catch (ValidationException ex) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
			ex.getMessage());
	    }
	}

	return viewParishHandler(1, existingParish.getSubCounty().getId(), model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/parish/delete/{pid}")
    public ModelAndView deleteParishHandler(@PathVariable("pid") String parishId,
	    ModelMap model) throws SessionExpiredException {
	Parish parish = adminService.getParishById(parishId);
	if (parish != null) {
	    parish.getSubCounty().removeParish(parish);
	    adminService.deleteParish(parishId);

	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "Parish >> " + parish.getName()
			    + " deleted successfully");
	    return viewParishHandler(null, parish.getSubCounty().getId(), model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the subcounty id supplied does not belong to a valid district in the system");
	}

	return viewParishHandler(null, null, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/parish/view/{scid}")
    public ModelAndView viewParishHandler(
	    @RequestParam(value = "pageNo", required = false) Integer pageNo,
	    @PathVariable("scid") String subCountyId, ModelMap model)
	    throws SessionExpiredException {

	if (pageNo == null || (pageNo != null && pageNo <= 0)) {
	    pageNo = 1;
	}

	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);

	SubCounty subCounty = adminService.getSubCountyById(subCountyId);
	if (subCounty != null) {
	    model.put("subCounty", subCounty);
	    model.put("parishes", subCounty.getParishes());
	    return new ModelAndView("viewParish", model);
	}
	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "supplied parish does not exist");
	return viewSubCountyHandler(1, null, model);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/village/add/{pid}")
    public ModelAndView addVillageHandler(@PathVariable("pid") String parishId,
	    ModelMap model) throws SessionExpiredException {
	Parish parish = adminService.getParishById(parishId);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	if (parish != null) {
	    Village village = new Village();
	    village.setParish(parish);
	    model.put("parish", village.getParish());
	    model.put("village", village);
	    return new ModelAndView("addOREditVillage", model);
	}

	return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/village/edit/{vid}")
    public ModelAndView editVillageHandler(@PathVariable("vid") String VillageID,
	    ModelMap model) throws SessionExpiredException {
	Village village = adminService.getVillageById(VillageID);
	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);
	if (village != null) {
	    model.put("parish", village.getParish());
	    model.put("village", village);
	    return new ModelAndView("addOREditVillage", model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the id of the village supplied doesn't match a district in the system");
	    return viewVillageHandler(null, null, model);
	}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/village/save/{pid}")
    public ModelAndView saveVillageHandler(@ModelAttribute("village") Village village,
	    @PathVariable("pid") String parishId,
	    ModelMap model) throws SessionExpiredException {
	Village exisitingVillage = null;

	Parish parish = adminService.getParishById(parishId);
	if (parish != null) {
	    try {

		exisitingVillage = village;

		if (StringUtils.isNotEmpty(village.getId())) {
		    exisitingVillage = adminService.getVillageById(village.getId());
		    exisitingVillage.setName(village.getName());
		    exisitingVillage.setParish(parish);
		} else {
		    exisitingVillage.setId(null);
		    exisitingVillage.setParish(parish);
		}

		adminService.validate(exisitingVillage);
		adminService.save(exisitingVillage);
		model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
			"village saved successfully");

	    } catch (ValidationException ex) {
		model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
			ex.getMessage());
	    }
	}

	return viewVillageHandler(1, exisitingVillage.getParish().getId(), model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/village/delete/{vid}")
    public ModelAndView deleteVillagehHandler(@PathVariable("vid") String villageId,
	    ModelMap model) throws SessionExpiredException {
	Village village = adminService.getVillageById(villageId);
	if (village != null) {
	    village.getParish().removeVillage(village);
	    adminService.deleteVillage(villageId);

	    model.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
		    "Village >> " + village.getName()
			    + " deleted successfully");
	    return viewVillageHandler(null, village.getParish().getId(), model);
	} else {
	    model.put(
		    WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
		    "the village id supplied does not belong to a valid district in the system");
	}

	return viewVillageHandler(null, null, model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/village/view/{pid}")
    public ModelAndView viewVillageHandler(
	    @RequestParam(value = "pageNo", required = false) Integer pageNo,
	    @PathVariable("pid") String parishId, ModelMap model) throws SessionExpiredException {

	if (pageNo == null || (pageNo != null && pageNo <= 0)) {
	    pageNo = 1;
	}

	WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(), model);

	Parish parish = adminService.getParishById(parishId);
	if (parish != null) {
	    model.put("parish", parish);
	    model.put("villages", parish.getVillages());
	    return new ModelAndView("viewVillage", model);
	}
	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "supplied village does not exist");
	return viewVillageHandler(1, null, model);

    }
}
