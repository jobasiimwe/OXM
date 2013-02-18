package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "publications")
public class Publication extends BaseData implements Comparable<Publication> {
    private String title;
    private Integer publicationYear;
    private String publicationMonth;
    private Concept publicationType;
    private Concept publicationStatus;
    private String otherInformation;
    private List<Document> documents;


    public Publication() {
	super();
    }

    public Publication(String title, Integer publicationYear, String publicationMonth,
	    Concept publicationType,
	    Concept publicationStatus, String otherInformation) {
	super();
	this.title = title;
	this.publicationYear = publicationYear;
	this.publicationMonth = publicationMonth;
	this.publicationType = publicationType;
	this.publicationStatus = publicationStatus;
	this.otherInformation = otherInformation;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
	return this.title;
    }

    @Column(name = "publication_year", nullable = false)
    public Integer getPublicationYear() {
	return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
	this.publicationYear = publicationYear;
    }

    @Column(name = "publication_month", nullable = true)
    public String getPublicationMonth() {
	return publicationMonth;
    }

    public void setPublicationMonth(String publicationMonth) {
	this.publicationMonth = publicationMonth;
    }

    public void setPublicationType(Concept publicationType) {
	this.publicationType = publicationType;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "publication_type", nullable = false)
    public Concept getPublicationType() {
	return this.publicationType;
    }

    public void setPublicationStatus(Concept publicationStatus) {
	this.publicationStatus = publicationStatus;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "publication_status", nullable = false)
    public Concept getPublicationStatus() {
	return this.publicationStatus;
    }

    public void setOtherInformation(String otherInformation) {
	this.otherInformation = otherInformation;
    }

    @Column(name = "other_information", nullable = true)
    public String getOtherInformation() {
	return this.otherInformation;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "publication_documents", joinColumns = @JoinColumn(name = "publication_id"), inverseJoinColumns = @JoinColumn(name = "document_id"))
    public List<Document> getDocuments() {
        return documents;
    }
    
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
	if (document == null)
	    return;

	if (this.getDocuments() == null)
	    this.setDocuments(new ArrayList<Document>());

	this.getDocuments().add(document);
    }
    
    @Override
    public int compareTo(Publication o) {
	return this.getTitle().compareToIgnoreCase(o.getTitle());
    }
}
