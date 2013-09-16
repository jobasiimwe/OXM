package org.agric.oxm.web.controllers.porg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.agric.oxm.model.Document;
import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.DocumentService;
import org.agric.oxm.server.service.ProducerOrgService;
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
@RequestMapping("porgdocs")
public class POrgDocsController {

	@Autowired
	private ProducerOrgService porgService;

	@Autowired
	private DocumentService documentService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	// =================================================

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id, ModelMap modelMap) {
		ProducerOrg pOrg = porgService.getProducerOrganisationById(id);
		modelMap.put("pOrg", pOrg);
		modelMap.put("documents", pOrg.getDocuments());

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Documents of " + pOrg.getName());

		return new ModelAndView("pOrgDocView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "add/{id}", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("id") String id, ModelMap modelMap) {

		ProducerOrg pOrg = porgService.getProducerOrganisationById(id);
		Document document = new Document(pOrg);
		modelMap.put("document", document);
		modelMap.put("pOrg", pOrg);

		modelMap.put(WebConstants.CONTENT_HEADER,
				"Add Document of " + pOrg.getName());
		return new ModelAndView("pOrgDocForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "edit/{docId}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("docId") String docId,
			ModelMap modelMap) {

		Document document = documentService.getDocumentById(docId);
		modelMap.put("document", document);
		modelMap.put("pOrg", document.getpOrg());

		modelMap.put(WebConstants.CONTENT_HEADER, "Edit Document of "
				+ document.getpOrg().getName());

		return new ModelAndView("pOrgDocForm", modelMap);
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

			if (document.getpOrg() == null)
				throw new ValidationException(
						"The Finantial Institution can not be null");

			ProducerOrg pOrg = document.getpOrg();

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
				existingDocument.setpOrg(document
						.getpOrg());
			} else {

				if (file == null || file.getSize() == 0)
					throw new ValidationException("File is required");

				existingDocument.setDateCreated(Calendar.getInstance()
						.getTime());
				existingDocument
						.setCreatedBy(OXMSecurityUtil.getLoggedInUser());
				existingDocument.setId(null);
				pOrg.addDocument(existingDocument);
			}

			if (file != null && file.getSize() > 0) {
				documentService.saveDocument(existingDocument,
						file.getInputStream());
				porgService.save(pOrg);
			} else
				documentService.saveDocument(existingDocument, null);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Document Saved sucessfully.");

			return view(pOrg.getId(), modelMap);

		} catch (Exception ex) {
			log.error(null, ex);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					" - " + ex.getMessage());
			modelMap.put("document", document);
			modelMap.put("pOrg", document.getpOrg());

			modelMap.put(WebConstants.CONTENT_HEADER,
					"Retry Add/Edit Document of "
							+ document.getpOrg().getName());

			return new ModelAndView("pOrgDocForm", modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "delete/{porgid}/{ids}")
	public ModelAndView delete(@PathVariable("porgid") String porgid,
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
					"Documents(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE, "Error "
					+ e.getMessage());
		}

		return view(porgid, modelMap);
	}
}
