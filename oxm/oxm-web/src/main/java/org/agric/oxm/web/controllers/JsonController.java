package org.agric.oxm.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.County;
import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.Village;
import org.agric.oxm.server.service.Adminservice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("get/json")
public class JsonController {

	@Autowired
	private Adminservice adminService;

	/**
	 * gets a jazonized list of counties in a given district
	 * 
	 * @param districtId
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/districts")
	public void getJSONDistricts(HttpServletResponse response) {

		try {
			List<District> districts = adminService.getDistricts();

			if (districts != null) {
				ArrayList<Object> districtsObjects = new ArrayList<Object>();

				for (District district : districts) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("value", district.getId());
					map.put("text", district.getName());
					districtsObjects.add(map);
				}

				MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
				HttpOutputMessage message = new ServletServerHttpResponse(
						response);

				converter.write(districtsObjects, MediaType.APPLICATION_JSON,
						message);
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * gets a jazonized list of counties in a given district
	 * 
	 * @param districtId
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/counties/{districtid}")
	public void getJSONCountiesForDistrict(
			@PathVariable("districtid") String districtId,
			HttpServletResponse response) {

		try {
			if (StringUtils.isNotBlank(districtId)) {
				District district = adminService.getDistrictById(districtId);

				if (district != null && district.getCounties() != null) {
					ArrayList<Object> subCounties = new ArrayList<Object>();

					for (County county : district.getCounties()) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("value", county.getId());
						map.put("text", county.getName());
						subCounties.add(map);
					}

					MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
					HttpOutputMessage message = new ServletServerHttpResponse(
							response);

					converter.write(subCounties, MediaType.APPLICATION_JSON,
							message);
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * gets a jazonized list of subCounties in a given county
	 * 
	 * @param districtId
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/subcounties/{countyid}")
	public void getJSONSubCountiesForCounty(
			@PathVariable("countyid") String countyId,
			HttpServletResponse response) {

		try {
			if (StringUtils.isNotBlank(countyId)) {
				County county = adminService.getCountyById(countyId);

				if (county != null && county.getSubCounties() != null) {
					ArrayList<Object> subCounties = new ArrayList<Object>();

					for (SubCounty subcounty : county.getSubCounties()) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("value", subcounty.getId());
						map.put("text", subcounty.getName());
						subCounties.add(map);
					}

					MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
					HttpOutputMessage message = new ServletServerHttpResponse(
							response);

					converter.write(subCounties, MediaType.APPLICATION_JSON,
							message);
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * gets a jazonized list of parishes in a given sub-county
	 * 
	 * @param subCountyId
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/parishes/{subcountyid}")
	public void getJSONParishesForSubCounty(
			@PathVariable("subcountyid") String subCountyId,
			HttpServletResponse response) {

		try {
			if (StringUtils.isNotBlank(subCountyId)) {
				SubCounty subCounty = adminService
						.getSubCountyById(subCountyId);

				if (subCounty != null && subCounty.getParishes() != null) {
					ArrayList<Object> parishes = new ArrayList<Object>();

					for (Parish parish : subCounty.getParishes()) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("value", parish.getId());
						map.put("text", parish.getName());
						parishes.add(map);
					}

					MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
					HttpOutputMessage message = new ServletServerHttpResponse(
							response);

					converter.write(parishes, MediaType.APPLICATION_JSON,
							message);
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * gets a jazonized list of villages in a given parish
	 * 
	 * @param parishId
	 * @param response
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/villages/{parishid}")
	public void getJSONVillagesForParish(
			@PathVariable("parishid") String parishId,
			HttpServletResponse response) {

		try {
			if (StringUtils.isNotBlank(parishId)) {
				Parish parish = adminService.getParishById(parishId);

				if (parish != null && parish.getVillages() != null) {
					ArrayList<Object> villages = new ArrayList<Object>();

					for (Village village : parish.getVillages()) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("value", village.getId());
						map.put("text", village.getName());
						villages.add(map);
					}

					MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
					HttpOutputMessage message = new ServletServerHttpResponse(
							response);

					converter.write(villages, MediaType.APPLICATION_JSON,
							message);
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

}
