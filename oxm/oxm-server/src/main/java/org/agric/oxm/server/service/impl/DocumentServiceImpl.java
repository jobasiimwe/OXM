package org.agric.oxm.server.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.agric.oxm.model.Document;
import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.DocumentDAO;
import org.agric.oxm.server.service.DocumentService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import com.eaio.uuid.UUID;
import com.googlecode.genericdao.search.Search;

/**
 * Implementation of the {@link DocumentService} interface. <br/>
 * This class requires that an environment variable named
 * {@code OXM_DOCUMENT_FILE_STORE} to be set. This environment variable contains
 * the path where the documents will be stored on the file system
 * 
 * @author ctumwebaze
 * 
 */
@Service("documentService")
@Transactional
public class DocumentServiceImpl implements DocumentService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * map of extensions and their mime types
	 */
	private static Map<String, String> mapMimeTypes = null;

	@Autowired
	private PlatformTransactionManager transactionalManager;

	/**
	 * static initializer
	 */
	static {
		mapMimeTypes = new HashMap<String, String>();

		mapMimeTypes.put("ai", "application/postscript");
		mapMimeTypes.put("aif", "audio/x-aiff");
		mapMimeTypes.put("aifc", "audio/x-aiff");
		mapMimeTypes.put("aiff", "audio/x-aiff");
		mapMimeTypes.put("asc", "text/plain");
		mapMimeTypes.put("asf", "video/x.ms.asf");
		mapMimeTypes.put("asx", "video/x.ms.asx");
		mapMimeTypes.put("au", "audio/basic");
		mapMimeTypes.put("avi", "video/x-msvideo");
		mapMimeTypes.put("bcpio", "application/x-bcpio");
		mapMimeTypes.put("bin", "application/octet-stream");
		mapMimeTypes.put("cab", "application/x-cabinet");
		mapMimeTypes.put("cdf", "application/x-netcdf");
		mapMimeTypes.put("class", "application/java-vm");
		mapMimeTypes.put("cpio", "application/x-cpio");
		mapMimeTypes.put("cpt", "application/mac-compactpro");
		mapMimeTypes.put("crt", "application/x-x509-ca-cert");
		mapMimeTypes.put("csh", "application/x-csh");
		mapMimeTypes.put("css", "text/css");
		mapMimeTypes.put("csv", "text/comma-separated-values");
		mapMimeTypes.put("dcr", "application/x-director");
		mapMimeTypes.put("dir", "application/x-director");
		mapMimeTypes.put("dll", "application/x-msdownload");
		mapMimeTypes.put("dms", "application/octet-stream");
		mapMimeTypes.put("doc", "application/msword");
		mapMimeTypes.put("dtd", "application/xml-dtd");
		mapMimeTypes.put("dvi", "application/x-dvi");
		mapMimeTypes.put("dxr", "application/x-director");
		mapMimeTypes.put("eps", "application/postscript");
		mapMimeTypes.put("etx", "text/x-setext");
		mapMimeTypes.put("exe", "application/octet-stream");
		mapMimeTypes.put("ez", "application/andrew-inset");
		mapMimeTypes.put("gif", "image/gif");
		mapMimeTypes.put("gtar", "application/x-gtar");
		mapMimeTypes.put("gz", "application/gzip");
		mapMimeTypes.put("gzip", "application/gzip");
		mapMimeTypes.put("hdf", "application/x-hdf");
		mapMimeTypes.put("htc", "text/x-component");
		mapMimeTypes.put("hqx", "application/mac-binhex40");
		mapMimeTypes.put("html", "text/html");
		mapMimeTypes.put("htm", "text/html");
		mapMimeTypes.put("ice", "x-conference/x-cooltalk");
		mapMimeTypes.put("ief", "image/ief");
		mapMimeTypes.put("iges", "model/iges");
		mapMimeTypes.put("igs", "model/iges");
		mapMimeTypes.put("jar", "application/java-archive");
		mapMimeTypes.put("java", "text/plain");
		mapMimeTypes.put("jnlp", "application/x-java-jnlp-file");
		mapMimeTypes.put("jpeg", "image/jpeg");
		mapMimeTypes.put("jpe", "image/jpeg");
		mapMimeTypes.put("jpg", "image/jpeg");
		mapMimeTypes.put("js", "application/x-javascript");
		mapMimeTypes.put("jsp", "text/plain");
		mapMimeTypes.put("kar", "audio/midi");
		mapMimeTypes.put("latex", "application/x-latex");
		mapMimeTypes.put("lha", "application/octet-stream");
		mapMimeTypes.put("lzh", "application/octet-stream");
		mapMimeTypes.put("man", "application/x-troff-man");
		mapMimeTypes.put("mathml", "application/mathml+xml");
		mapMimeTypes.put("me", "application/x-troff-me");
		mapMimeTypes.put("mesh", "model/mesh");
		mapMimeTypes.put("mid", "audio/midi");
		mapMimeTypes.put("midi", "audio/midi");
		mapMimeTypes.put("mif", "application/vnd.mif");
		mapMimeTypes.put("mol", "chemical/x-mdl-molfile");
		mapMimeTypes.put("movie", "video/x-sgi-movie");
		mapMimeTypes.put("mov", "video/quicktime");
		mapMimeTypes.put("mp2", "audio/mpeg");
		mapMimeTypes.put("mp3", "audio/mpeg");
		mapMimeTypes.put("mpeg", "video/mpeg");
		mapMimeTypes.put("mpe", "video/mpeg");
		mapMimeTypes.put("mpga", "audio/mpeg");
		mapMimeTypes.put("mpg", "video/mpeg");
		mapMimeTypes.put("ms", "application/x-troff-ms");
		mapMimeTypes.put("msh", "model/mesh");
		mapMimeTypes.put("msi", "application/octet-stream");
		mapMimeTypes.put("nc", "application/x-netcdf");
		mapMimeTypes.put("oda", "application/oda");
		mapMimeTypes.put("ogg", "application/ogg");
		mapMimeTypes.put("pbm", "image/x-portable-bitmap");
		mapMimeTypes.put("pdb", "chemical/x-pdb");
		mapMimeTypes.put("pdf", "application/pdf");
		mapMimeTypes.put("pgm", "image/x-portable-graymap");
		mapMimeTypes.put("pgn", "application/x-chess-pgn");
		mapMimeTypes.put("png", "image/png");
		mapMimeTypes.put("pnm", "image/x-portable-anymap");
		mapMimeTypes.put("ppm", "image/x-portable-pixmap");
		mapMimeTypes.put("ppt", "application/vnd.ms-powerpoint");
		mapMimeTypes.put("ps", "application/postscript");
		mapMimeTypes.put("qt", "video/quicktime");
		mapMimeTypes.put("ra", "audio/x-pn-realaudio");
		mapMimeTypes.put("ra", "audio/x-realaudio");
		mapMimeTypes.put("ram", "audio/x-pn-realaudio");
		mapMimeTypes.put("ras", "image/x-cmu-raster");
		mapMimeTypes.put("rdf", "application/rdf+xml");
		mapMimeTypes.put("rgb", "image/x-rgb");
		mapMimeTypes.put("rm", "audio/x-pn-realaudio");
		mapMimeTypes.put("roff", "application/x-troff");
		mapMimeTypes.put("rpm", "application/x-rpm");
		mapMimeTypes.put("rpm", "audio/x-pn-realaudio");
		mapMimeTypes.put("rtf", "application/rtf");
		mapMimeTypes.put("rtx", "text/richtext");
		mapMimeTypes.put("ser", "application/java-serialized-object");
		mapMimeTypes.put("sgml", "text/sgml");
		mapMimeTypes.put("sgm", "text/sgml");
		mapMimeTypes.put("sh", "application/x-sh");
		mapMimeTypes.put("shar", "application/x-shar");
		mapMimeTypes.put("silo", "model/mesh");
		mapMimeTypes.put("sit", "application/x-stuffit");
		mapMimeTypes.put("skd", "application/x-koan");
		mapMimeTypes.put("skm", "application/x-koan");
		mapMimeTypes.put("skp", "application/x-koan");
		mapMimeTypes.put("skt", "application/x-koan");
		mapMimeTypes.put("smi", "application/smil");
		mapMimeTypes.put("smil", "application/smil");
		mapMimeTypes.put("snd", "audio/basic");
		mapMimeTypes.put("spl", "application/x-futuresplash");
		mapMimeTypes.put("src", "application/x-wais-source");
		mapMimeTypes.put("sv4cpio", "application/x-sv4cpio");
		mapMimeTypes.put("sv4crc", "application/x-sv4crc");
		mapMimeTypes.put("svg", "image/svg+xml");
		mapMimeTypes.put("swf", "application/x-shockwave-flash");
		mapMimeTypes.put("t", "application/x-troff");
		mapMimeTypes.put("tar", "application/x-tar");
		mapMimeTypes.put("tar.gz", "application/x-gtar");
		mapMimeTypes.put("tcl", "application/x-tcl");
		mapMimeTypes.put("tex", "application/x-tex");
		mapMimeTypes.put("texi", "application/x-texinfo");
		mapMimeTypes.put("texinfo", "application/x-texinfo");
		mapMimeTypes.put("tgz", "application/x-gtar");
		mapMimeTypes.put("tiff", "image/tiff");
		mapMimeTypes.put("tif", "image/tiff");
		mapMimeTypes.put("tr", "application/x-troff");
		mapMimeTypes.put("tsv", "text/tab-separated-values");
		mapMimeTypes.put("txt", "text/plain");
		mapMimeTypes.put("ustar", "application/x-ustar");
		mapMimeTypes.put("vcd", "application/x-cdlink");
		mapMimeTypes.put("vrml", "model/vrml");
		mapMimeTypes.put("vxml", "application/voicexml+xml");
		mapMimeTypes.put("wav", "audio/x-wav");
		mapMimeTypes.put("wbmp", "image/vnd.wap.wbmp");
		mapMimeTypes.put("wmlc", "application/vnd.wap.wmlc");
		mapMimeTypes.put("wmlsc", "application/vnd.wap.wmlscriptc");
		mapMimeTypes.put("wmls", "text/vnd.wap.wmlscript");
		mapMimeTypes.put("wml", "text/vnd.wap.wml");
		mapMimeTypes.put("wrl", "model/vrml");
		mapMimeTypes.put("wtls-ca-certificate",
				"application/vnd.wap.wtls-ca-certificate");
		mapMimeTypes.put("xbm", "image/x-xbitmap");
		mapMimeTypes.put("xht", "application/xhtml+xml");
		mapMimeTypes.put("xhtml", "application/xhtml+xml");
		mapMimeTypes.put("xls", "application/vnd.ms-excel");
		mapMimeTypes.put("xml", "application/xml");
		mapMimeTypes.put("xpm", "image/x-xpixmap");
		mapMimeTypes.put("xpm", "image/x-xpixmap");
		mapMimeTypes.put("xsl", "application/xml");
		mapMimeTypes.put("xslt", "application/xslt+xml");
		mapMimeTypes.put("xul", "application/vnd.mozilla.xul+xml");
		mapMimeTypes.put("xwd", "image/x-xwindowdump");
		mapMimeTypes.put("xyz", "chemical/x-xyz");
		mapMimeTypes.put("z", "application/compress");
		mapMimeTypes.put("zip", "application/zip");
	}

	@Autowired
	private DocumentDAO documentDao = null;

	public DocumentDAO getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDAO documentDao) {
		this.documentDao = documentDao;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveDocument(Document doc, InputStream stream)
			throws ValidationException {
		if (isValid(doc)) {

			TransactionDefinition def = new DefaultTransactionDefinition(
					TransactionTemplate.PROPAGATION_REQUIRED);
			TransactionStatus status = transactionalManager.getTransaction(def);

			try {

				documentDao.save(doc);

				String filePath = doc.getDocumentUrl();
				if (stream != null) {

					filePath = generateDocumentFileStorePath(doc);
					doc.setDocumentUrl(filePath);

					if (StringUtils.isNotBlank(filePath)) {

						File file = new File(filePath);

						file.createNewFile();

						FileOutputStream fileOutputStream = new FileOutputStream(
								file, false);
						// file.
						IOUtils.copy(stream, fileOutputStream);
						fileOutputStream.close();
					}
				}

				transactionalManager.commit(status);
			} catch (Exception e) {
				log.error("Error", e);
				status.setRollbackOnly();
			}
		} else {
			throw new ValidationException(
					"the supplied document contains invalid input.");
		}
	}

	/**
	 * generates a path for the given document in the file store
	 * 
	 * @param doc
	 *            document for which to generate the file path
	 * @return
	 */
	private String generateDocumentFileStorePath(Document doc) {
		String fileStorePath = System.getProperty("user.dir") + File.separator
				+ OXMConstants.OXM_DOCUMENT_DIRECTORY;

		File directory = new File(fileStorePath);
		if (!directory.isDirectory()) {
			directory.mkdirs();
		}

		if (StringUtils.isNotBlank(fileStorePath)) {
			if (!fileStorePath.endsWith(File.separator))
				fileStorePath += File.separator;

			String fileName = fileStorePath + new UUID().toString()
					+ getDocumentExtension(doc);

			if (StringUtils.isNotBlank(fileName))
				return fileName;
		}

		return null;
	}

	/**
	 * gets the extension of the given document. If the getDocumentExtension of
	 * the document returns null, the extension is extracted from the document's
	 * content type. If the content type cannot be determined, no extension is
	 * returned.
	 * 
	 * @param doc
	 *            document for which to get the extension
	 * @return the extension of the document or the an empty string of no
	 *         extension could be determined.
	 */
	public static String getDocumentExtension(Document doc) {
		if (StringUtils.isNotBlank(doc.getDocumentExtension())) {
			if (!doc.getDocumentExtension().startsWith("."))
				return "." + doc.getDocumentExtension();

			return doc.getDocumentExtension();
		} else if (StringUtils.isNotBlank(doc.getContentType())) {
			Iterator<String> iterator = DocumentServiceImpl.mapMimeTypes
					.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = DocumentServiceImpl.mapMimeTypes.get(key);
				if (StringUtils.isNotBlank(value)
						&& value.equalsIgnoreCase(doc.getContentType())) {
					return key;
				}
			}
		}

		return "";
	}

	/**
	 * gets the mime type for the given extension
	 * 
	 * @param extension
	 *            extension for which to get the mime type
	 * @return
	 */
	public static String getMimeType(String extension) {
		if (StringUtils.isNotBlank(extension)) {
			if (extension.startsWith("."))
				extension = extension.substring(1);

			String mimeType = mapMimeTypes.get(extension);
			if (StringUtils.isNotBlank(mimeType))
				return mimeType;
		}

		return null;
	}

	/**
	 * gets the extension from the given path
	 * 
	 * @param path
	 *            path from which to extract the extension
	 * @return extension e.g. pdf, exe
	 */
	public static String getExtensionFromPath(String path) {
		if (StringUtils.isNotBlank(path)) {
			int startIndex = path.lastIndexOf(".");

			if (startIndex > 0)
				return path.substring(startIndex + 1);
		}

		return null;
	}

	/**
	 * checks whether a given document is valid.
	 * 
	 * @param doc
	 *            document to check for validity
	 * @return true if the document is valid and false of not.
	 * @throws ValidationException
	 *             if the given document contains invalid input like. missing
	 *             name, missing document url or path
	 */
	private boolean isValid(Document doc) throws ValidationException {
		if (doc == null)
			throw new ValidationException("the supplied document is null");

		if (StringUtils.isBlank(doc.getName()))
			throw new ValidationException("the supplied document has no name.");

		if (StringUtils.isBlank(doc.getContentType()))
			throw new ValidationException(
					"the supplied document has content type");

		if (StringUtils.isBlank(doc.getDocumentExtension()))
			throw new ValidationException(
					"the supplied document has no extension");

		if (StringUtils.isNotBlank(doc.getId())) {
			if (StringUtils.isBlank(doc.getDocumentUrl()))
				throw new ValidationException(
						"the supplied document does not contain the expected document url");

			if (!(new File(doc.getDocumentUrl()).exists()))
				throw new ValidationException(
						"the supplied document contains a url/path that does not exist.");
		}

		return true;
	}

	@Override
	@Transactional(readOnly = true)
	public Document getDocumentById(String id) {
		return documentDao.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Document> getDocumentsIds(List<String> ids) {
		Search search = new Search();
		search.addFilterIn("id", ids);

		return documentDao.search(search);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Document> getDocuments(User user) {
		List<Document> documents = documentDao.searchByPropertyEqual(
				"userDocumentOwner", user);
		Collections.sort(documents);
		return documents;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Document> getDocuments(ProducerOrg pOrg) {

		List<Document> documents = documentDao.searchByPropertyEqual(
				"pOrgDocumentOwner", pOrg);
		Collections.sort(documents);
		return documents;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Document> getDocuments(FinancialInstitution financialInstitution) {

		List<Document> documents = documentDao.searchByPropertyEqual(
				"fInstitutionDocumentOwner", financialInstitution);
		Collections.sort(documents);
		return documents;
	}

	@Override
	public void deleteDocument(Document doc) throws Exception {
		if (doc == null)
			throw new ValidationException("the supplied document is null");

		TransactionDefinition def = new DefaultTransactionDefinition(
				TransactionTemplate.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionalManager.getTransaction(def);
		try {
			documentDao.remove(doc);
			transactionalManager.commit(status);
			deleteDocumentFromFileStore(doc);
		} catch (TransactionException ex) {
			status.setRollbackOnly();
			throw new Exception(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.agric.oxm.server.service.DocumentService#deleteDocumentFromFileStore
	 * (java.util.List)
	 */
	@Override
	public void deleteDocumentFromFileStore(List<Document> documents) {
		if (documents != null) {
			for (Document document : documents) {
				deleteDocumentFromFileStore(document);
			}
		}
	}

	/**
	 * deletes a given document from the file store.
	 * 
	 * @param document
	 */
	@Override
	public void deleteDocumentFromFileStore(Document document) {
		if (StringUtils.isNotBlank(document.getDocumentUrl())) {
			File file = new File(document.getDocumentUrl());
			if (file != null && file.exists()) {
				file.delete();
			}
		}
	}

	@Override
	public void validate(Document document) throws ValidationException {
		if (StringUtils.isBlank(document.getName())
				|| StringUtils.isEmpty(document.getName()))
			throw new ValidationException("No Document name supplied");

	}

}