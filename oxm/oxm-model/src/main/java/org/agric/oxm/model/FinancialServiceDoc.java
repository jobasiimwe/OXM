package org.agric.oxm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "financial_service_doc")
public class FinancialServiceDoc extends BaseData {
    
    private FinancialInstitution financialInstitution;
    
    private Date dateUploaded;
    
    private Publication publication;

    public FinancialServiceDoc() {
	super();
    }

    @ManyToOne
    @JoinColumn(name = "financial_institutution_id", nullable = false)
    public FinancialInstitution getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(FinancialInstitution financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_uploaded", nullable = false)
    public Date getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "publication_id", nullable = false)
    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
    
    
}
