package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "producer_org")
public class ProducerOrganisation extends BaseData implements
		Comparable<ProducerOrganisation> {

	private String name;
	
	private District district;

	private SubCounty subCounty;
	
	private List<User> producers;

	private List<StaffMember> staffMembers;

	private List<Committee> committees;

	public ProducerOrganisation() {
		super();
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne
	@JoinColumn(name="district_id", nullable=false)
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@ManyToOne
	@JoinColumn(name="sub_county_id", nullable=false)
	public SubCounty getSubCounty() {
		return subCounty;
	}

	public void setSubCounty(SubCounty subCounty) {
		this.subCounty = subCounty;
	}

	@OneToMany(mappedBy = "producerOrg", cascade = { CascadeType.ALL }, orphanRemoval = false)
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
		ProducerOrganisation other = (ProducerOrganisation) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProducerOrganisation o) {
		return this.getName().compareTo(o.getName());
	}

}
