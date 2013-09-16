package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "producer_org")
public class ProducerOrg extends BaseData implements Comparable<ProducerOrg> {

	private String name;

	private District district;
	private County county;
	private SubCounty subCounty;
	private Parish parish;
	private Village village;

	private List<User> producers;

	private List<StaffMember> staffMembers;

	private List<Committee> committees;

	private List<Document> documents = Collections.emptyList();

	public ProducerOrg() {
	}

	public ProducerOrg(String id) {
		this.setId(id);
	}

	public ProducerOrg(String name, District district, County county,
			SubCounty subCounty, Parish parish, Village village) {
		this.setName(name);
		this.setDistrict(district);
		this.setCounty(county);
		this.setSubCounty(subCounty);
		this.setParish(parish);
		this.setVillage(village);
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// =======================================================================

	@Transient
	public String getNameAndSubCounty() {
		String str = getName() + "(" + getSubCounty().getName() + ")";
		return str;
	}

	@Transient
	public String getDistrictString() {
		String str = "";

		if (village != null)
			return village.getFullName();
		if (parish != null)
			return parish.getFullName();
		if (subCounty != null)
			return subCounty.getFullName();

		return str;
	}

	// ========================================================================

	@ManyToOne
	@JoinColumn(name = "district_id", nullable = false)
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@ManyToOne
	@JoinColumn(name = "county_id", nullable = false)
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@ManyToOne
	@JoinColumn(name = "sub_county_id", nullable = false)
	public SubCounty getSubCounty() {
		return subCounty;
	}

	public void setSubCounty(SubCounty subCounty) {
		this.subCounty = subCounty;
	}

	@ManyToOne
	@JoinColumn(name = "parish_id", nullable = false)
	public Parish getParish() {
		return parish;
	}

	public void setParish(Parish parish) {
		this.parish = parish;
	}

	@ManyToOne
	@JoinColumn(name = "village_id", nullable = false)
	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	// =============================================================================

	@OneToMany(mappedBy = "producerOrg", cascade = { CascadeType.ALL }, orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	public List<User> getProducers() {
		return producers;
	}

	public void setProducers(List<User> producers) {
		this.producers = producers;
	}

	public void addProducer(User producer) {
		if (producer == null) {
			return;
		}

		if (this.getProducers() == null) {
			this.setProducers(new ArrayList<User>());
		}

		this.getProducers().add(producer);
	}

	public void removeProducer(User producer) {
		if (producer == null || this.getProducers() == null) {
			return;
		}
		this.getProducers().remove(producer);
	}

	@OneToMany(mappedBy = "producerOrg", cascade = { CascadeType.ALL }, orphanRemoval = false)
	public List<StaffMember> getStaffMembers() {
		return staffMembers;
	}

	public void setStaffMembers(List<StaffMember> staffMembers) {
		this.staffMembers = staffMembers;
	}

	public void addStaffMember(StaffMember staffMember) {
		if (staffMember == null) {
			return;
		}

		if (this.getStaffMembers() == null) {
			this.setStaffMembers(new ArrayList<StaffMember>());
		}

		this.getStaffMembers().add(staffMember);
	}

	public void removeStaffMember(StaffMember staffMember) {
		if (staffMember == null || this.getStaffMembers() == null) {
			return;
		}
		this.getStaffMembers().remove(staffMember);
	}

	public void removeStaffMembersByIds(String[] idzToDelete) {
		for (String id : idzToDelete) {
			StaffMember member = new StaffMember(id);

			if (this.getStaffMembers().contains(member)) {
				getStaffMembers().remove(member);
			}
		}
	}

	@OneToMany(mappedBy = "producerOrg", cascade = { CascadeType.ALL }, orphanRemoval = false)
	public List<Committee> getCommittees() {
		return committees;
	}

	public void setCommittees(List<Committee> committees) {
		this.committees = committees;
	}

	public void addCommittee(Committee committee) {
		if (committee == null) {
			return;
		}

		if (this.getCommittees() == null) {
			this.setCommittees(new ArrayList<Committee>());
		}

		this.getCommittees().add(committee);
	}

	public void removeCommittee(Committee committee) {
		if (committee == null || this.getCommittees() == null) {
			return;
		}
		this.getCommittees().remove(committee);
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	@OneToMany(mappedBy = "pOrg", cascade = { CascadeType.ALL }, orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	public List<Document> getDocuments() {
		return this.documents;
	}

	public void addDocument(Document doc) {
		if (doc == null) {
			return;
		}

		if (this.getDocuments() == null) {
			this.setDocuments(new ArrayList<Document>());
		}

		this.getDocuments().add(doc);
	}

	public void removeDocument(Document doc) {
		if (doc == null || this.getDocuments() == null) {
			return;
		}

		this.getDocuments().remove(doc);
	}

	public void removeDocumentsByIds(String[] idzToDelete) {
		for (String id : idzToDelete) {
			Document d = new Document(id);

			if (this.getDocuments().contains(d)) {
				getDocuments().remove(d);
			}
		}
	}

	// ================================================

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
		ProducerOrg other = (ProducerOrg) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProducerOrg o) {
		return this.getName().compareTo(o.getName());
	}

}
