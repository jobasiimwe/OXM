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

/**
 * 
 * @author Job
 * 
 */
@Entity
@Table(name = "committee")
public class Committee extends BaseData implements Comparable<Committee> {

	private String name;

	private List<CommitteeMember> members;

	private ProducerOrganisation producerOrg;

	public Committee() {

	}

	public Committee(ProducerOrganisation producerOrg) {
		this.setProducerOrg(producerOrg);
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "committee", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<CommitteeMember> getMembers() {
		return members;
	}

	public void setMembers(List<CommitteeMember> members) {
		this.members = members;
	}

	public void addMember(CommitteeMember member) {
		if (member == null) {
			return;
		}

		if (this.getMembers() == null) {
			this.setMembers(new ArrayList<CommitteeMember>());
		}

		this.getMembers().add(member);
	}

	public void removemMember(CommitteeMember member) {
		if (member == null || this.getMembers() == null) {
			return;
		}

		this.getMembers().remove(member);
	}

	@ManyToOne
	@JoinColumn(name = "producer_organisation_id", nullable = false)
	public ProducerOrganisation getProducerOrg() {
		return producerOrg;
	}

	public void setProducerOrg(ProducerOrganisation producerOrg) {
		this.producerOrg = producerOrg;
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
		Committee other = (Committee) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Committee o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
