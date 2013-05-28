package org.agric.oxm.web.controllers.settings;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.agric.oxm.model.Document;
import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.DocumentService;
import org.agric.oxm.server.service.FinancialInstitutionService;
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
@RequestMapping("finstitution")
public class FinancialInstitutionController {

	@Autowired
	private FinancialInstitutionService financialService;

	@Autowired
	private DocumentService documentService;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "/view/", method = RequestMethod.GET)
	public ModelAndView viewFiniancialInsititutionHandler(ModelMap modelMap) {
		List<FinancialInstitution> fInstitution = financialService
				.getFinancialInstitutions();
		modelMap.put("fInstitutions", fInstitution);
		return new ModelAndView("viewFinancialInstitution", modelMap);

	}

	@Secured({ PermissionConstants.ADD_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "/add/", method = RequestMethod.GET)
	public ModelAndView addFiniancialInstitutionHandler(ModelMap modelMap)
			throws SessionExpiredException {
		modelMap.put("fInstitution", new FinancialInstitution());
		return new ModelAndView("formFinancialInstitution", modelMap);

	}

	@Secured({ PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editFinancialInstitutionHandler(ModelMap modelMap,
			@PathVariable("id") String fInstitutionId) {

		FinancialInstitution fInsitution = financialService
				.getFinancialInstitutionById(fInstitutionId);

		if (fInsitution != null) {
			modelMap.put("fInstitution", fInsitution);
			return new ModelAndView("formFinancialInstitution", modelMap);
		}

		modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
				"Invalid Financial Insitution id submitted");
		return viewFiniancialInsititutionHandler(modelMap);

	}

	@Secured({ PermissionConstants.DELETE_FINANCIAL_INSTITUTION })
	@RequestMapping(method = RequestMethod.GET, value = "/delete/{ids}")
	public ModelAndView deleteFinancialInstitutionHandler(
			@PathVariable("ids") String fInstitutionIds, ModelMap modelMapMap) {

		String[] fInstitutionIdzToDelete = fInstitutionIds.split(",");
		try {
			financialService
					.deleteFinancialInstitutionsByIds(fInstitutionIdzToDelete);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Institution(s)  deleted successfully");
		} catch (Exception e) {
			log.error("Error", e);
			modelMapMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Error " + e.getMessage());
		}

		return viewFiniancialInsititutionHandler(modelMapMap);

	}

	@Secured({ PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
	@RequestMapping(method = RequestMethod.POST, value = "/save/")
	public ModelAndView saveSellingPlaceHandler(
			@ModelAttribute("fInstitution") FinancialInstitution fInstitution,
			ModelMap modelMap) {

		try {

			financialService.validate(fInstitution);

			FinancialInstitution existingFInstitution = fInstitution;

			if (StringUtils.isNotEmpty(fInstitution.getId())) {
				existingFInstitution = financialService
						.getFinancialInstitutionById(fInstitution.getId());
				existingFInstitution.setName(fInstitution.getName());
				existingFInstitution.setAddress(fInstitution.getAddress());
			} else {
				existingFInstitution.setId(null);
			}

			financialService.save(existingFInstitution);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Institution saved sucessfully.");
		} catch (ValidationException e) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
			modelMap.put("fInsititution", fInstitution);
			return new ModelAndView("formFinancialInstitution", modelMap);
		}
		return viewFiniancialInsititutionHandler(modelMap);
	}

	// =================================================

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "/docs/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewFInsititutionDocsHandler(
			@PathVariable("id") String id, ModelMap modelMap) {
		FinancialInstitution fInstitution = financialService
				.getFinancialInstitutionById(id);
		modelMap.put("fInstitution", fInstitution);
		modelMap.put("documents", fInstitution.getDocuments());
		return new ModelAndView("fInstitutionDocView", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "/docs/add/{id}", method = RequestMethod.GET)
	public ModelAndView addFInsititutionDocsHandler(
			@PathVariable("id") String id, ModelMap modelMap) {

		FinancialInstitution fInstitution = financialService
				.getFinancialInstitutionById(id);
		Document document = new Document(fInstitution);
		modelMap.put("document", document);
		modelMap.put("fInstitution", fInstitution);
		return new ModelAndView("fInstitutionDocForm", modelMap);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@RequestMapping(value = "/docs/edit/{docId}", method = RequestMethod.GET)
	public ModelAndView editFInsititutionDocsHandler(
			@PathVariable("docId") String docId, ModelMap modelMap) {

		Document document = documentService.getDocumentById(docId);
		modelMap.put("document", document);
		modelMap.put("fInstitution", document.getfInstitutionDocumentOwner());
		return new ModelAndView("fInstitutionDocForm", modelMap);
	}

	/**
	 * handles request for saving attached document
	 * 
	 * @param document
	 * @param file
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/docs/save", method = RequestMethod.POST)
	public ModelAndView saveFinancialDocumentHandler(
			@ModelAttribute("document") Document document,
			@RequestParam(value = "file", required = false) MultipartFile file,
			ModelMap modelMap) {

		try {

			if (document.getfInstitutionDocumentOwner() == null)
				throw new ValidationException(
						"The Finantial Institution can not be null");

			FinancialInstitution fInstitution = document
					.getfInstitutionDocumentOwner();

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
				existingDocument.setfInstitutionDocumentOwner(document
						.getfInstitutionDocumentOwner());
			} else {

				if (file == null || file.getSize() == 0)
					throw new ValidationException("File is required");

				existingDocument.setDateCreated(Calendar.getInstance()
						.getTime());
				existingDocument
						.setCreatedBy(OXMSecurityUtil.getLoggedInUser());
				existingDocument.setId(null);
				fInstitution.addDocument(existingDocument);
			}

			if (file != null && file.getSize() > 0) {
				documentService.saveDocument(existingDocument,
						file.getInputStream());
				financialService.save(fInstitution);
			} else
				documentService.saveDocument(existingDocument, null);

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE,
					"Financial Document Saved sucessfully.");

			return viewFInsititutionDocsHandler(fInstitution.getId(), modelMap);

		} catch (Exception ex) {
			log.error(null, ex);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					" - " + ex.getMessage());
			modelMap.put("document", document);
			modelMap.put("fInstitution",
					document.getfInstitutionDocumentOwner());
			return new ModelAndView("fInstitutionDocForm", modelMap);
		}

	}

	@Secured({ PermissionConstants.PERM_VIEW_ADMINISTRATION })
	@RequestMapping(method = RequestMethod.GET, value = "/docs/delete/{financialid}/{ids}")
	public ModelAndView deleteFinancialDocHandler(
			@PathVariable("financialid") String financialid,
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

		return viewFInsititutionDocsHandler(financialid, modelMap);
	}
}
