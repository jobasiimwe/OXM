package org.agric.oxm.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.model.Document;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.DocumentService;
import org.agric.oxm.web.OXMUtil;
import org.agric.oxm.web.WebConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationController {

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private PriceController priceController;

	@Autowired
	private DocumentService documentService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = { "/", "index.jsp" })
	public ModelAndView welcomeHandler(ModelMap modelMap)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				modelMap);

		return priceController.view(modelMap, OXMSecurityUtil.getLoggedInUser()
				.hasAdministrativePrivileges());
		// return new ModelAndView("dashboard", model);
	}

	@RequestMapping("/ServiceLogin")
	public ModelAndView loginHandler(ModelMap modelMap) {
		return new ModelAndView("login");
	}

	@RequestMapping("/ServiceLoginFailure")
	public ModelAndView loginFailureHander(ModelMap model) {
		model.put("loginErrorMessage", "username OR password is incorrect");
		return new ModelAndView("login", model);
	}

	@Secured({ PermissionConstants.PERM_ADMIN })
	@RequestMapping("/cpanel")
	public ModelAndView controlpanelHander(ModelMap model)
			throws SessionExpiredException {
		WebConstants.loadLoggedInUserProfile(OXMSecurityUtil.getLoggedInUser(),
				model);
		return new ModelAndView("cpanel", model);
	}

	public void addConceptsToModelMap(ModelMap modelMap,
			String conceptCategoryName, String key) {
		try {
			ConceptCategoryAnnotation sellTypeRoleAnnotation = OXMUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class, conceptCategoryName);

			if (sellTypeRoleAnnotation != null) {
				modelMap.put(
						key,
						conceptService
								.getConceptsByCategoryAnnotation(sellTypeRoleAnnotation));
			}

		} catch (Exception e) {
			log.error("Error picking concepts", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}
	}

	/**
	 * handles request to download a document with the given id
	 * 
	 * @param documentId
	 *            id of the document to download.
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/download/document/{id}")
	public void downloadDocumentHandler(@PathVariable("id") String documentId,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isNotBlank(documentId)) {

			try {
				Document document = documentService.getDocumentById(documentId);
				if (document != null) {
					// Set to expire far in the past.
					response.setDateHeader("Expires", 0);
					// Set standard HTTP/1.1 no-cache headers.
					response.setHeader("Cache-Control",
							"no-store, no-cache, must-revalidate");
					// Set IE extended HTTP/1.1 no-cache headers (use
					// addHeader).
					response.addHeader("Cache-Control",
							"post-check=0, pre-check=0");
					// Set standard HTTP/1.0 no-cache header.
					response.setHeader("Pragma", "no-cache");

					// return a the content type
					response.setContentType(document.getContentType());

					response.addHeader("Content-Disposition", String.format(
							"attachment; filename=%1s.%2s", document.getName()
									.replace(" ", "_"), document
									.getDocumentExtension()));

					IOUtils.copy(
							document.getDocumentInputStreamFromDocumentUrl(),
							response.getOutputStream());
					response.getOutputStream().flush();
					response.getOutputStream().close();
					response.setStatus(HttpServletResponse.SC_OK);
				}
			} catch (IOException ex) {
				log.error(null, ex);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} finally {
				response.getOutputStream().close();
			}
		}
	}

	/**
	 * handles request to download a document with the given id
	 * 
	 * @param documentId
	 *            id of the document to download.
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "prelogin/download/document/{id}")
	public void preLoginDownloadDocumentHandler(
			@PathVariable("id") String documentId, HttpServletResponse response)
			throws Exception {
		if (StringUtils.isNotBlank(documentId)) {

			try {
				Document document = documentService.getDocumentById(documentId);
				if (document != null) {
					// Set to expire far in the past.
					response.setDateHeader("Expires", 0);
					// Set standard HTTP/1.1 no-cache headers.
					response.setHeader("Cache-Control",
							"no-store, no-cache, must-revalidate");
					// Set IE extended HTTP/1.1 no-cache headers (use
					// addHeader).
					response.addHeader("Cache-Control",
							"post-check=0, pre-check=0");
					// Set standard HTTP/1.0 no-cache header.
					response.setHeader("Pragma", "no-cache");

					// return a the content type
					response.setContentType(document.getContentType());

					response.addHeader("Content-Disposition", String.format(
							"attachment; filename=%1s.%2s", document.getName()
									.replace(" ", "_"), document
									.getDocumentExtension()));

					IOUtils.copy(
							document.getDocumentInputStreamFromDocumentUrl(),
							response.getOutputStream());
					response.getOutputStream().flush();
					response.getOutputStream().close();
					response.setStatus(HttpServletResponse.SC_OK);
				}
			} catch (IOException ex) {
				log.error(null, ex);
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} finally {
				response.getOutputStream().close();
			}
		}
	}

}
