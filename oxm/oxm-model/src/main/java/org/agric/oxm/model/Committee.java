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
public class Committee extends BaseData {

	private String name;

	private List<CommitteeMember> committeeMembers;

	private ProductionOrganisation productionOrganisation;

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
	@JoinColumn(name = "production_organisation_id", nullable = false)
	public ProductionOrganisation getProductionOrganisation() {
		return productionOrganisation;
	}

	public void setProductionOrganisation(
			ProductionOrganisation productionOrganisation) {
		this.productionOrganisation = productionOrganisation;
	}
}
