package org.agric.oxm.server.service;

import java.io.InputStream;
import java.util.List;

import org.agric.oxm.model.Document;
import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.ValidationException;

/**
 * Service interface for accessing document related functionality
 * 
 * @author Job
 * 
 */
public interface DocumentService {

	void validate(Document document) throws ValidationException;

	void saveDocument(Document doc, InputStream stream)
			throws ValidationException;

	Document getDocumentById(String id);

	List<Document> getDocumentsIds(List<String> ids);

	List<Document> getDocuments(User user);

	List<Document> getDocuments(ProducerOrganisation pOrg);

	List<Document> getDocuments(FinancialInstitution financialInstitution);

	void deleteDocument(Document doc) throws ValidationException, Exception;

	void deleteDocumentFromFileStore(List<Document> documents);

	void deleteDocumentFromFileStore(Document document);
}
