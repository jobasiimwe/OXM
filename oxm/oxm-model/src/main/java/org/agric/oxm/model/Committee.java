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

	private List<CommitteeMember> committeeMembers;

	private ProducerOrganisation producerOrganisation;

	public Committee() {

	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "committee", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<CommitteeMember> getCommitteeMembers() {
		return committeeMembers;
	}

	public void setCommitteeMembers(List<CommitteeMember> committeeMembers) {
		this.committeeMembers = committeeMembers;
	}

	public void addCommitteeMember(CommitteeMember committeeMember) {
		if (committeeMember == null) {
			return;
		}

		if (this.getCommitteeMembers() == null) {
			this.setCommitteeMembers(new ArrayList<CommitteeMember>());
		}

		this.getCommitteeMembers().add(committeeMember);
	}

	public void removeCommitteeMember(CommitteeMember committeeMember) {
		if (committeeMember == null || this.getCommitteeMembers() == null) {
			return;
		}

		this.getCommitteeMembers().remove(committeeMember);
	}

	@ManyToOne
	@JoinColumn(name = "producer_organisation_id", nullable = false)
	public ProducerOrganisation getProducerOrganisation() {
		return producerOrganisation;
	}

	public void setProducerOrganisation(
			ProducerOrganisation producerOrganisation) {
		this.producerOrganisation = producerOrganisation;
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
