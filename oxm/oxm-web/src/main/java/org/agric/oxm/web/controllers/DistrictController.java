package org.agric.oxm.web.controllers;

import java.util.List;

import org.agric.oxm.model.District;
import org.agric.oxm.model.SubCounty;
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
    public ModelAndView viewAddSubCountyHandler(@PathVariable("did") String districtId, ModelMap model) throws SessionExpiredException {
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
    public ModelAndView viewEditSubCountyHandler(@PathVariable("sid") String subCountyId, ModelMap model) throws SessionExpiredException {
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
    public ModelAndView saveSubCountyHandler(@ModelAttribute("subCounty") SubCounty subCounty,@PathVariable("did") String districtId,
	    ModelMap model) throws SessionExpiredException {
	    District d = adminService.getDistrictById(districtId);
	if (subCounty != null) {
	    try {

		SubCounty existingSubcounty = subCounty;

		if (StringUtils.isNotEmpty(subCounty.getId())) {
		    existingSubcounty = adminService.getSubCountyById(subCounty.getId());
		    existingSubcounty.setName(subCounty.getName());
		}else{
		
		    existingSubcounty.setDistrict(d);
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

		model.put("district", d);
	    model.put("subcounties", adminService.getDistrictById(districtId).getSubCounties());
	    return new ModelAndView("viewSubCounty", model);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subcounty/delete/{id}")
    public ModelAndView deleteSubCountyHandler(@PathVariable("id") String subcountyId,
	    ModelMap model) throws SessionExpiredException {
	SubCounty subCounty = adminService.getSubCountyById(subcountyId);
	if (subCounty != null) {
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
    public ModelAndView viewSubCountyHandler(@RequestParam(value = "pageNo", required = false) Integer pageNo,
	    @PathVariable("did") String districtId, ModelMap model) throws SessionExpiredException {

	if (pageNo == null || (pageNo != null && pageNo <= 0)) {
	    pageNo = 1;
	}

	District district = adminService.getDistrictById(districtId);
	if (district != null) {
	    model.put("district", district);
	    model.put("subcounties", district.getSubCounties());
	    return new ModelAndView("viewSubCounty", model);
	}
	model.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "supplied district does not exist");
	return viewDistrictHandler(model);
	
    }
}
