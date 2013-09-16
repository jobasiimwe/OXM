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

	private String name, contentType, documentExtension, documentUrl,
			otherInfo;

	private Concept documentType;

	private User user;

	private FinancialInstitution fInstitution;
	private ProducerOrg pOrg;
	private Season season;

	private InputStream documentStream;

	private Date dateCreated = Calendar.getInstance().getTime();

	private User createdBy = null;

	public Document() {
	}

	public Document(String id) {
		this.setId(id);
	}

	public Document(FinancialInstitution financialInstitution) {
		this.setfInstitution(financialInstitution);
	}

	public Document(ProducerOrg producerOrg) {
		this.setpOrg(producerOrg);
	}

	public Document(Season season) {
		this.setSeason(season);
	}

	public Document(String docName, Concept docType, String docUrl, String other) {
		super();
		this.name = docName;
		this.documentType = docType;
		this.documentUrl = docUrl;
		this.otherInfo = other;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "document_name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setDocumentType(Concept documentType) {
		this.documentType = documentType;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "document_type", nullable = true)
	public Concept getDocumentType() {
		return this.documentType;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	@Column(name = "document_url", nullable = false)
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	public void setOtherInfo(String other) {
		this.otherInfo = other;
	}

	@Column(name = "other_infor", nullable = true)
	public String getOtherInfo() {
		return this.otherInfo;
	}

	@Column(name = "document_content_type", nullable = true)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(name = "document_extension", nullable = true)
	public String getDocumentExtension() {
		return documentExtension;
	}

	public void setDocumentExtension(String documentExtension) {
		this.documentExtension = documentExtension;
	}

	@ManyToOne
	@JoinColumn(name = "user_document_owner", nullable = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "finstitution_doc_owner", nullable = true)
	public FinancialInstitution getfInstitution() {
		return fInstitution;
	}

	public void setfInstitution(FinancialInstitution fInstitution) {
		this.fInstitution = fInstitution;
	}

	@ManyToOne
	@JoinColumn(name = "porg_doc_owner", nullable = true)
	public ProducerOrg getpOrg() {
		return pOrg;
	}

	public void setpOrg(ProducerOrg pOrg) {
		this.pOrg = pOrg;
	}

	@ManyToOne
	@JoinColumn(name = "season_doc_owner", nullable = true)
	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	@Transient
	public InputStream getDocumentInputStream() {
		return documentStream;
	}

	public void setDocumentInputStream(InputStream documentStream) {
		this.documentStream = documentStream;
	}

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

	@Temporal(TemporalType.TIME)
	@Column(name = "date_created", nullable = true)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = true)
	public User getCreatedBy() {
		return createdBy;
	}

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
