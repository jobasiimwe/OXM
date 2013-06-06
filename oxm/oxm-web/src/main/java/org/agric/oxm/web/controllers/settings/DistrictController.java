package org.agric.oxm.web.controllers.settings;

import java.util.List;

import org.agric.oxm.model.County;
import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.Village;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
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
public class DistrictController {

	@Autowired
	private Adminservice adminService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(value = "/district/view/", method = RequestMethod.GET)
	public ModelAndView viewDistrictHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("district", new District());
		modelMap.put("districts", adminService.getDistricts());

		modelMap.put(WebConstants.CONTENT_HEADER, "List of Districts");
		return new ModelAndView("viewDistrict", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/district/add/")
	public ModelAndView addDistrictHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		modelMap.put("district", new District());
		modelMap.put(WebConstants.CONTENT_HEADER, "Add a District");
		return new ModelAndView("formDistrict", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/district/edit/{id}")
	public ModelAndView editDistrictHandler(
			@PathVariable("id") String districtId, ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (!modelMap.containsAttribute("district")) {
			District district = adminService.getDistrictById(districtId);
			if (district != null) {
				modelMap.put("district", district);
				modelMap.put(WebConstants.CONTENT_HEADER,
						"Edit " + district.getName());
			} else {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"the id doesn't match a district in the system");
				return new ModelAndView("viewDistrict", modelMap);
			}
		}

		return new ModelAndView("formDistrict", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/district/save/")
	public ModelAndView saveDistrictHandler(
			@ModelAttribute("district") District district, ModelMap modelMap)
			throws SessionExpiredException {

		try {

			District existingDistrict = district;

			if (StringUtils.isNotEmpty(district.getId())) {
				existingDistrict = adminService.getDistrictById(district
						.getId());
				existingDistrict.setName(district.getName());
			} else {
				existingDistrict.setId(null);
			}

			adminService.validate(existingDistrict);
			adminService.save(existingDistrict);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"district saved successfully");
			return viewDistrictHandler(modelMap);
		} catch (ValidationException ex) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					ex.getMessage());

			modelMap.put(WebConstants.CONTENT_HEADER, "Retry Add/Edit District");
			return new ModelAndView("formDistrict", modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/district/delete/{id}")
	public ModelAndView deleteDistrictHandler(
			@PathVariable("id") String districtIds, ModelMap modelMap)
			throws SessionExpiredException {
		String[] districtIdzToDelete = districtIds.split(",");
		try {
			adminService.deleteDistrictsByIds(districtIdzToDelete);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"District(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewDistrictHandler(modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/district/search")
	public ModelAndView searchDistrict(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(WebConstants.SEARCH_QUERY_REQUEST_PARAMETER_NAME) String query,
			ModelMap modelMap) throws SessionExpiredException {
		modelMap.put("query", query);

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		if (query.equals("")) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"No search query!");
			return viewDistrictHandler(modelMap);
		} else {

			List<District> districts = adminService.searchDistrict(query);

			if (districts != null && districts.size() == 0) {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
						"search for query >> \"" + query
								+ "\" completed with no results");
			} else {

				if (districts != null && districts.size() > 0) {
					modelMap.put(
							WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
							"search for query >> \""
									+ query
									+ "\" completed successfully with the following results");
				}
				modelMap.put("districts", districts);

			}
		}
		return new ModelAndView("viewDistrict", modelMap);
	}

	// ==================================================================================

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/county/view/{did}")
	public ModelAndView viewCountyHandler(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@PathVariable("did") String districtId, ModelMap modelMap)
			throws SessionExpiredException {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		District district = adminService.getDistrictById(districtId);
		if (district != null) {
			modelMap.put("district", district);
			modelMap.put("counties", district.getCounties());
			modelMap.put(WebConstants.CONTENT_HEADER, " Counties of "
					+ district.getName());
			return new ModelAndView("countyView", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"supplied district does not exist");
			return viewDistrictHandler(modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/county/add/{did}")
	public ModelAndView addCountyHandler(
			@PathVariable("did") String districtId, ModelMap modelMap)
			throws SessionExpiredException {
		District district = adminService.getDistrictById(districtId);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (district != null) {
			County County = new County(district);
			modelMap.put("district", district);
			modelMap.put("county", County);
			modelMap.put(WebConstants.CONTENT_HEADER, "Add County to "
					+ district.getName());
			return new ModelAndView("countyForm", modelMap);
		}

		return null;
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/county/edit/{cid}")
	public ModelAndView editCountyHandler(@PathVariable("cid") String CountyId,
			ModelMap modelMap) throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (!modelMap.containsAttribute("county")) {
			County County = adminService.getCountyById(CountyId);
			if (County != null) {
				modelMap.put("county", County);
				modelMap.put("district", County.getDistrict());
			} else {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"the id of the county supplied doesn't match a county in the system");
				return viewCountyHandler(null, null, modelMap);
			}
		}

		return new ModelAndView("countyform", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/county/save")
	public ModelAndView saveCountyHandler(
			@ModelAttribute("County") County County, ModelMap modelMap)
			throws SessionExpiredException {

		try {

			District district = County.getDistrict();
			adminService.validate(County);

			County existingcounty = County;
			if (StringUtils.isNotEmpty(County.getId())) {
				existingcounty = adminService.getCountyById(County.getId());
				existingcounty.setName(County.getName());

				district.removeCounty(existingcounty);
				district.addCounty(existingcounty);
			} else {
				existingcounty.setId(null);
				district.addCounty(existingcounty);
			}

			adminService.save(district);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"county saved successfully");

			return viewCountyHandler(1, existingcounty.getDistrict().getId(),
					modelMap);

		} catch (ValidationException ex) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					ex.getMessage());
			modelMap.put(WebConstants.CONTENT_HEADER, "Re-try Add/Edit County");
			modelMap.put("district", County.getDistrict());
			return new ModelAndView("countyForm", modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/county/delete/{districtId}/{ids}")
	public ModelAndView deleteCountyHandler(
			@PathVariable("districtId") String districtId,
			@PathVariable("ids") String countyIds, ModelMap modelMap)
			throws SessionExpiredException {

		String[] countyIdzToDelete = countyIds.split(",");
		try {
			District district = adminService.getDistrictById(districtId);
			district.removeCountiesByIds(countyIdzToDelete);

			adminService.save(district);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"County(ies)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewCountyHandler(1, districtId, modelMap);
	}

	// ==================================================================================

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/subcounty/view/{cid}")
	public ModelAndView viewSubCountyHandler(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@PathVariable("cid") String countyId, ModelMap modelMap)
			throws SessionExpiredException {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		County county = adminService.getCountyById(countyId);
		if (county != null) {
			modelMap.put("county", county);
			modelMap.put("subcounties", county.getSubCounties());
			modelMap.put(WebConstants.CONTENT_HEADER, "Sub Counties of "
					+ county.getName());
			return new ModelAndView("viewSubCounty", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"supplied district does not exist");
			return viewDistrictHandler(modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/subcounty/add/{cid}")
	public ModelAndView addSubCountyHandler(
			@PathVariable("cid") String countyId, ModelMap modelMap)
			throws SessionExpiredException {
		County county = adminService.getCountyById(countyId);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (county != null) {
			SubCounty subCounty = new SubCounty();
			subCounty.setCounty(county);
			modelMap.put("county", county);
			modelMap.put("subcounty", subCounty);
			modelMap.put(WebConstants.CONTENT_HEADER, "Add SubCounty to "
					+ county.getName());
			return new ModelAndView("formSubCounty", modelMap);
		}

		return null;
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/subcounty/edit/{sid}")
	public ModelAndView editSubCountyHandler(
			@PathVariable("sid") String subCountyId, ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (!modelMap.containsAttribute("subcounty")) {
			SubCounty subCounty = adminService.getSubCountyById(subCountyId);
			if (subCounty != null) {
				modelMap.put("subcounty", subCounty);
				modelMap.put("county", subCounty.getCounty());
			} else {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"the id of the subcounty supplied doesn't match a district in the system");
				return viewSubCountyHandler(null, null, modelMap);
			}
		}

		return new ModelAndView("formSubCounty", modelMap);
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/subcounty/save/{cid}")
	public ModelAndView saveSubCountyHandler(
			@ModelAttribute("subCounty") SubCounty subCounty,
			@PathVariable("cid") String districtId, ModelMap modelMap)
			throws SessionExpiredException {

		try {

			County county = subCounty.getCounty();
			adminService.validate(subCounty);

			SubCounty existingSubcounty = subCounty;
			if (StringUtils.isNotEmpty(subCounty.getId())) {
				existingSubcounty = adminService.getSubCountyById(subCounty
						.getId());
				existingSubcounty.setName(subCounty.getName());

				county.removeSubCounty(existingSubcounty);
				county.addSubCounty(existingSubcounty);
			} else {
				existingSubcounty.setId(null);
				county.addSubCounty(existingSubcounty);
			}

			adminService.save(county);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"subcounty saved successfully");

			return viewSubCountyHandler(1, existingSubcounty.getCounty()
					.getId(), modelMap);

		} catch (ValidationException ex) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					ex.getMessage());
			modelMap.put(WebConstants.CONTENT_HEADER,
					"Re-try Add/Edit Sub-County");
			modelMap.put("county", subCounty.getCounty());
			return new ModelAndView("formSubCounty", modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/subcounty/delete/{countyId}/{ids}")
	public ModelAndView deleteSubCountyHandler(
			@PathVariable("countyId") String countyId,
			@PathVariable("ids") String subcountyIds, ModelMap modelMap)
			throws SessionExpiredException {

		String[] subcountyIdzToDelete = subcountyIds.split(",");
		try {

			County county = adminService.getCountyById(countyId);
			county.removeSubCountiesByIds(subcountyIdzToDelete);

			adminService.save(county);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Sub-County(ies)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewSubCountyHandler(1, countyId, modelMap);
	}

	// ==================================================================================

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/parish/view/{scid}")
	public ModelAndView viewParishHandler(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@PathVariable("scid") String subCountyId, ModelMap modelMap)
			throws SessionExpiredException {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		SubCounty subCounty = adminService.getSubCountyById(subCountyId);
		if (subCounty != null) {
			modelMap.put("subCounty", subCounty);
			modelMap.put("parishes", subCounty.getParishes());
			modelMap.put(WebConstants.CONTENT_HEADER, "Parishes of "
					+ subCounty.getName());
			return new ModelAndView("viewParish", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"supplied parish does not exist");
			return viewSubCountyHandler(1, null, modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/parish/add/{scid}")
	public ModelAndView addParishHandler(
			@PathVariable("scid") String subCountyId, ModelMap modelMap)
			throws SessionExpiredException {
		SubCounty subCounty = adminService.getSubCountyById(subCountyId);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (subCounty != null) {
			Parish parish = new Parish();
			parish.setSubCounty(subCounty);
			modelMap.put("subCounty", parish.getSubCounty());
			modelMap.put("parish", parish);
			modelMap.put(WebConstants.CONTENT_HEADER, "Add Parish to "
					+ subCounty.getName());
			return new ModelAndView("formParish", modelMap);
		}

		return null;
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/parish/edit/{pid}")
	public ModelAndView editParishHandler(@PathVariable("pid") String parishID,
			ModelMap modelMap) throws SessionExpiredException {
		Parish parish = adminService.getParishById(parishID);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (parish != null) {
			modelMap.put("subCounty", parish.getSubCounty());
			modelMap.put("parish", parish);
			modelMap.put(WebConstants.CONTENT_HEADER,
					"Editing " + parish.getName());
			return new ModelAndView("formParish", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"the id of the parish supplied doesn't match a district in the system");
			return viewSubCountyHandler(null, null, modelMap);
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/parish/save/{scid}")
	public ModelAndView saveParishHandler(
			@ModelAttribute("parish") Parish parish,
			@PathVariable("scid") String subCountyId, ModelMap modelMap)
			throws SessionExpiredException {

		try {
			SubCounty subCounty = parish.getSubCounty();
			adminService.validate(parish);

			Parish existingParish = parish;

			if (StringUtils.isNotEmpty(parish.getId())) {
				existingParish = adminService.getParishById(parish.getId());
				existingParish.setName(parish.getName());
				subCounty.removeParish(existingParish);
				subCounty.addParish(existingParish);
			} else {
				existingParish.setId(null);
				subCounty.addParish(existingParish);
			}

			adminService.save(subCounty);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"parish saved successfully");
			return viewParishHandler(1, existingParish.getSubCounty().getId(),
					modelMap);
		} catch (ValidationException ex) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					ex.getMessage());
			modelMap.put(WebConstants.CONTENT_HEADER, "Re-try Add/Edit parish");
			modelMap.put("subCounty", parish.getSubCounty());
			return new ModelAndView("formParish", modelMap);
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/parish/delete/{sid}/{pids}")
	public ModelAndView deleteParishHandler(
			@PathVariable("sid") String subCountyId,
			@PathVariable("pids") String parishIds, ModelMap modelMap)
			throws SessionExpiredException {

		String[] parishIdzToDelete = parishIds.split(",");
		try {
			SubCounty subCounty = adminService.getSubCountyById(subCountyId);
			subCounty.removeParishesByIds(parishIdzToDelete);

			adminService.save(subCounty);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Parish(es)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewParishHandler(null, subCountyId, modelMap);
	}

	// ==================================================================================

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/village/view/{pid}")
	public ModelAndView viewVillageHandler(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@PathVariable("pid") String parishId, ModelMap modelMap)
			throws SessionExpiredException {

		if (pageNo == null || (pageNo != null && pageNo <= 0)) {
			pageNo = 1;
		}

		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		Parish parish = adminService.getParishById(parishId);
		if (parish != null) {
			modelMap.put("parish", parish);
			modelMap.put("villages", parish.getVillages());
			modelMap.put(WebConstants.CONTENT_HEADER,
					"Villages of " + parish.getName());
			return new ModelAndView("viewVillage", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"supplied parish id does not exist");
			return viewDistrictHandler(modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/village/add/{pid}")
	public ModelAndView addVillageHandler(@PathVariable("pid") String parishId,
			ModelMap modelMap) throws SessionExpiredException {
		Parish parish = adminService.getParishById(parishId);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (parish != null) {
			Village village = new Village();
			village.setParish(parish);
			modelMap.put("parish", village.getParish());
			modelMap.put("village", village);
			modelMap.put(WebConstants.CONTENT_HEADER, "Add village to "
					+ parish.getName());
			return new ModelAndView("formVillage", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"the id doesn't match a parish in the system");
			return viewDistrictHandler(modelMap);
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/village/edit/{vid}")
	public ModelAndView editVillageHandler(
			@PathVariable("vid") String VillageID, ModelMap modelMap)
			throws SessionExpiredException {
		Village village = adminService.getVillageById(VillageID);
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);
		if (village != null) {
			modelMap.put("parish", village.getParish());
			modelMap.put("village", village);
			modelMap.put(WebConstants.CONTENT_HEADER,
					"Editing " + village.getName());
			return new ModelAndView("formVillage", modelMap);
		} else {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"the id doesn't match a village in the system");
			return viewDistrictHandler(modelMap);
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.POST, value = "/village/save/{pid}")
	public ModelAndView saveVillageHandler(
			@ModelAttribute("village") Village village,
			@PathVariable("pid") String parishId, ModelMap modelMap)
			throws SessionExpiredException {

		try {

			Parish parish = village.getParish();
			adminService.validate(village);
			Village exisitingVillage = village;

			if (StringUtils.isNotEmpty(village.getId())) {
				exisitingVillage = adminService.getVillageById(village.getId());
				exisitingVillage.setName(village.getName());

				parish.removeVillage(exisitingVillage);
				parish.addVillage(exisitingVillage);
			} else {
				exisitingVillage.setId(null);
				parish.addVillage(exisitingVillage);
			}

			adminService.save(parish);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"village saved successfully");

			return viewVillageHandler(1, exisitingVillage.getParish().getId(),
					modelMap);
		} catch (ValidationException ex) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					ex.getMessage());
			modelMap.put(WebConstants.CONTENT_HEADER, "Retry Add/Edit "
					+ (StringUtils.isBlank(village.getName()) ? "Village"
							: village.getName()));
			modelMap.put("parish", village.getParish());
			return new ModelAndView("formVillage", modelMap);
		}
	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/village/delete/{pid}/{vids}")
	public ModelAndView deleteVillagehHandler(
			@PathVariable("pid") String parishId,
			@PathVariable("vids") String villageIds, ModelMap modelMap)
			throws SessionExpiredException {

		String[] villageIdzToDelete = villageIds.split(",");
		try {
			Parish parish = adminService.getParishById(parishId);
			parish.removeVillagesByIds(villageIdzToDelete);

			adminService.save(parish);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Village(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return viewVillageHandler(null, parishId, modelMap);
	}

}
