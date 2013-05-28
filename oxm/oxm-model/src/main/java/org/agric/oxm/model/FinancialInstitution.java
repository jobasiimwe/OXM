package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "financial_institution")
public class FinancialInstitution extends BaseData implements
		Comparable<FinancialInstitution> {

	private String name;

	private String address;

	private List<Document> documents = Collections.emptyList();

	public FinancialInstitution() {
		super();
	}

	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "address", nullable = false)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public void addDocument(Document member) {
		if (member == null) {
			return;
		}

		if (this.getDocuments() == null) {
			this.setDocuments(new ArrayList<Document>());
		}

		this.getDocuments().add(member);
	}

	public void removemDocument(Document member) {
		if (member == null || this.getDocuments() == null) {
			return;
		}

		this.getDocuments().remove(member);
	}

	public void removeDocumentsByIds(String[] idzToDelete) {
		for (String id : idzToDelete) {
			Document member = new Document(id);

			if (this.getDocuments().contains(member)) {
				getDocuments().remove(member);
			}
		}
	}
	@OneToMany
	@JoinTable(name = "finstitution_documents", joinColumns = @JoinColumn(name = "finstitution_id"), inverseJoinColumns = @JoinColumn(name = "document_id"))
	public List<Document> getDocuments() {
		//if (this.documents == null)
		//	return new ArrayList<Document>();

		return this.documents;
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
		FinancialInstitution other = (FinancialInstitution) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(FinancialInstitution o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
