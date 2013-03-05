package org.agric.oxm.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "documents")
public class Document extends BaseData implements Comparable<Document> {

	private String name;

	private Concept documentType;

	private String contentType;

	private String documentExtension;

	private String documentUrl;

	private String otherInfo;

	private User userDocumentOwner;

	private InputStream documentStream;

	private Date dateCreated = Calendar.getInstance().getTime();

	private User createdBy = null;

	public Document() {

	}

	public Document(String docName, Concept docType, String docUrl, String other) {
		super();
		this.name = docName;
		this.documentType = docType;
		this.documentUrl = docUrl;
		this.otherInfo = other;
	}

	/**
	 * Sets the name for this document.
	 * 
	 * @param name
	 *            the document name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name for this document.
	 * 
	 * @return the document name
	 */
	@Column(name = "document_name", nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the document-type for this entity.
	 * 
	 * @param documentType
	 *            the doc type
	 */
	public void setDocumentType(Concept documentType) {
		this.documentType = documentType;
	}

	/**
	 * Gets the documen-type for this entity.
	 * 
	 * @return the doc type
	 */
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "document_type", nullable = true)
	public Concept getDocumentType() {
		return this.documentType;
	}

	/**
	 * Sets the document url for this entity.
	 * 
	 * @param documentUrl
	 *            the doc url type
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * Gets the document url for this entity.
	 * 
	 * @return the doc type
	 */
	@Column(name = "document_url", nullable = false)
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	/**
	 * Sets any other info for this document.
	 * 
	 * @param other
	 *            {@link #otherInfo}
	 */
	public void setOtherInfo(String other) {
		this.otherInfo = other;
	}

	/**
	 * Gets any other info about this document.
	 * 
	 * @return the other information about a document
	 */
	@Column(name = "other_infor", nullable = true)
	public String getOtherInfo() {
		return this.otherInfo;
	}

	/**
	 * gets the content type of the document
	 * 
	 * @return the contentType
	 */
	@Column(name = "document_content_type", nullable = true)
	public String getContentType() {
		return contentType;
	}

	/**
	 * sets the content type of the document
	 * 
	 * @see #getContentType()
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * gets the extension of the document e.g pdf, txt etc
	 * 
	 * @return the documentExtension
	 */
	@Column(name = "document_extension", nullable = true)
	public String getDocumentExtension() {
		return documentExtension;
	}

	/**
	 * sets the extension of the document.
	 * 
	 * @see #getDocumentExtension()
	 * @param documentExtension
	 *            the documentExtension to set
	 */
	public void setDocumentExtension(String documentExtension) {
		this.documentExtension = documentExtension;
	}

	/**
	 * gets the user who owns this document
	 * 
	 * @return the userDocumentOwner
	 */
	@ManyToOne
	@JoinColumn(name = "user_document_owner", nullable = true)
	public User getUserDocumentOwner() {
		return userDocumentOwner;
	}

	/**
	 * sets the user who owns this document
	 * 
	 * @see #getUserDocumentOwner()
	 * @param userDocumentOwner
	 *            the userDocumentOwner to set
	 */
	public void setUserDocumentOwner(User userDocumentOwner) {
		this.userDocumentOwner = userDocumentOwner;
	}

	/**
	 * gets the document stream. This is a utility method to access the
	 * inputstream of the document. This method works in connection with
	 * {@link #setDocumentInputStream(InputStream)}
	 * 
	 * @return the documentStream
	 */
	@Transient
	public InputStream getDocumentInputStream() {
		return documentStream;
	}

	/**
	 * sets the document stream. This is a utility method to temporarly store
	 * the inputstream of the document. This stream is not persisted.
	 * 
	 * @param documentStream
	 *            the documentStream to set
	 */
	public void setDocumentInputStream(InputStream documentStream) {
		this.documentStream = documentStream;
	}

	/**
	 * Utility method to access the document's output stream. This method uses
	 * the document's url to create the output stream.
	 * 
	 * @see #getDocumentUrl()
	 * 
	 * @return null if {@link #getDocumentUrl()} is null or if the document file
	 *         exists and Outputstream of the document's url exists.
	 * @throws FileNotFoundException
	 *             if the document url does not exist in the file store.
	 */
	@Transient
	public InputStream getDocumentInputStreamFromDocumentUrl()
			throws FileNotFoundException {
		if (this.getDocumentUrl() != null
				&& this.getDocumentUrl().trim().length() > 0) {

			File documentFile = new File(this.getDocumentUrl());
			if (documentFile.exists()) {
				return new FileInputStream(documentFile);
			}
		}

		return null;
	}

	/**
	 * gets the date the document was created
	 * 
	 * @return the dateCreated
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "date_created", nullable = true)
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * sets the date the document was created
	 * 
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * gets the user who created this document
	 * 
	 * @return the createdBy
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = true)
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * sets the user who created the document
	 * 
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public int compareTo(Document o) {
		int x = 0;
		if (this.getDocumentType() != null && o.getDocumentType() != null) {
			x = this.getDocumentType().getName()
					.compareToIgnoreCase(o.getDocumentType().getName());

			if (x == 0)
				x = this.getName().compareToIgnoreCase(o.getName());
		}

		return x;
	}

	@Override
	public String toString() {
		if (this.getId() != null || this.getId().trim().length() > 0)
			return this.getId();

		return super.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

}
