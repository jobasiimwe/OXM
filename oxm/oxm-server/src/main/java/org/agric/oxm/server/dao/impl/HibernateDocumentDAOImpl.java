package org.agric.oxm.server.dao.impl;

import org.agric.oxm.server.dao.DocumentDAO;
import org.agric.oxm.model.Document;
import org.springframework.stereotype.Repository;

/**
 * hibernate implementation of the DocumentDAO interface
 * 
 * @author Job
 * 
 */
@Repository("documentDAO")
public class HibernateDocumentDAOImpl extends BaseDAOImpl<Document> implements
		DocumentDAO {

}
