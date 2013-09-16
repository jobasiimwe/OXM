package org.agric.oxm.web.controllers.settings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.agric.oxm.model.Document;
import org.agric.oxm.model.Season;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.DocumentService;
import org.agric.oxm.server.service.SeasonService;
import org.agric.oxm.server.service.impl.DocumentServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("seasondocs")
public class SeasonDocController {

	@Autowired
	private SeasonService seasonService;

	@Autowired
	private DocumentService documentService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// =================================================

	@Secured({ PermissionConstants.VIEW_SEASONS })
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id, ModelMap modelMap) {
		Season season = seasonService.getById(id);
		modelMap.put("season", season);
		modelMap.put("documents", season.getDocuments());

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Documents for - " + season.getName());
		return new ModelAndView("seasonDocView", modelMap);
	}

	@Secured({ PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(value = "add/{id}", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("id") String id, ModelMap modelMap) {

		Season season = seasonService.getById(id);
		Document document = new Document(season);
		modelMap.put("document", document);
		modelMap.put("season", season);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Add Document for - " + season.getName());
		return new ModelAndView("seasonDocForm", modelMap);
	}

	@Secured({ PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(value = "edit/{docId}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("docId") String docId,
			ModelMap modelMap) {

		Document document = documentService.getDocumentById(docId);
		modelMap.put("document", document);
		modelMap.put("season", document.getSeason());

		modelMap.put(WebConstants.CONTENT_HEADER, "Edit Document for - "
				+ document.getSeason().getName());

		return new ModelAndView("seasonDocForm", modelMap);
	}

	/**
	 * handles request for saving attached document
	 * 
	 * @param document
	 * @param file
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("document") Document document,
			@RequestParam(value = "file", required = false) MultipartFile file,
			ModelMap modelMap) {

		try {

			if (document.getSeason() == null)
				throw new ValidationException("The Season can not be null");

			Season season = document.getSeason();

			documentService.validate(document);

			if (file != null && file.getSize() > 0) {

				String extension = DocumentServiceImpl
						.getExtensionFromPath(file.getOriginalFilename());
				if (StringUtils.isBlank(extension)) {
					extension = DocumentServiceImpl
							.getDocumentExtension(document);
				}

				String contentType = DocumentServiceImpl.getMimeType(extension);
				if (StringUtils.isBlank(contentType)) {
					contentType = file.getContentType();
				}

				document.setDocumentExtension(extension);
				document.setContentType(contentType);
				document.setDocumentInputStream(file.getInputStream());

			}

			Document existingDocument = document;

			if (StringUtils.isNotEmpty(document.getId())) {

				existingDocument = documentService.getDocumentById(document
						.getId());
				existingDocument.setName(document.getName());
				existingDocument.setContentType(document.getContentType());
				existingDocument.setDocumentType(document.getDocumentType());
				existingDocument.setDocumentExtension(document
						.getDocumentExtension());
				existingDocument.setDocumentUrl(document.getDocumentUrl());
				existingDocument.setOtherInfo(document.getOtherInfo());
				existingDocument.setSeason(document.getSeason());
			} else {

				if (file == null || file.getSize() == 0)
					throw new ValidationException("File is required");

				existingDocument.setDateCreated(Calendar.getInstance()
						.getTime());
				existingDocument
						.setCreatedBy(OXMSecurityUtil.getLoggedInUser());
				existingDocument.setId(null);
				season.addDocument(existingDocument);
			}

			if (file != null && file.getSize() > 0) {
				documentService.saveDocument(existingDocument,
						file.getInputStream());
				seasonService.save(season);
			} else
				documentService.saveDocument(existingDocument, null);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Season Document Saved sucessfully.");

			return view(season.getId(), modelMap);

		} catch (Exception ex) {
			log.error(null, ex);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					" - " + ex.getMessage());
			modelMap.put("document", document);
			modelMap.put("season", document.getSeason());

			modelMap.put(WebConstants.CONTENT_HEADER,
					"Retry Add/Edit Document of "
							+ document.getSeason().getName());
			return new ModelAndView("seasonDocForm", modelMap);
		}

	}

	@Secured({ PermissionConstants.MANAGE_SEASONS })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{finstid}/{ids}")
	public ModelAndView delete(@PathVariable("finstid") String finstid,
			@PathVariable("ids") String ids, ModelMap modelMap) {

		String[] arrayidzToDelete = ids.split(",");

		List<String> listIds2Delete = new ArrayList<String>();
		for (String str : arrayidzToDelete)
			listIds2Delete.add(str);

		try {
			List<Document> docsToDelete = documentService
					.getDocumentsIds(listIds2Delete);

			for (Document document : docsToDelete)
				documentService.deleteDocument(document);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Documents(s) deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return view(finstid, modelMap);
	}
}
