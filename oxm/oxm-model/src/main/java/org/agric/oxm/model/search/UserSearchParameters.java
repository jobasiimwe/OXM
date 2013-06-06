/**
 * 
 */
package org.agric.oxm.model.search;

import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.enums.Gender;

/**
 * @author Job
 * 
 */
public class UserSearchParameters {
	private String nameOrUserName = null;
	private ProducerOrg producerOrg;
	private Gender gender = null;
	private Role role = null;

	private String staffMemberId;
	private String committeeId;
	private String committeeMemberId;

	public UserSearchParameters() {

	}

	public UserSearchParameters(ProducerOrg pOrg, Role role) {
		this.setProducerOrg(pOrg);
		this.setRole(role);
	}

	public UserSearchParameters(String committeeId, String committeeMemberId) {
		this.setCommitteeId(committeeId);
		this.setCommitteeMemberId(committeeMemberId);
	}

	public UserSearchParameters(ProducerOrg pOrg, Role role,
			String committeeId, String committeeMemberId) {
		this.setCommitteeId(committeeId);
		this.setCommitteeMemberId(committeeMemberId);
		this.setProducerOrg(pOrg);
		this.setRole(role);
	}

	public String getNameOrUserName() {
		return nameOrUserName;
	}

	public void setNameOrUserName(String nameOrUserName) {
		this.nameOrUserName = nameOrUserName;
	}

	public ProducerOrg getProducerOrg() {
		return producerOrg;
	}

	public void setProducerOrg(ProducerOrg producerOrg) {
		this.producerOrg = producerOrg;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Gender getGender() {
		return gender;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public String getStaffMemberId() {
		return staffMemberId;
	}

	public void setStaffMemberId(String staffMemberId) {
		this.staffMemberId = staffMemberId;
	}

	public String getCommitteeId() {
		return committeeId;
	}

	public void setCommitteeId(String committeeId) {
		this.committeeId = committeeId;
	}

	public String getCommitteeMemberId() {
		return committeeMemberId;
	}

	public void setCommitteeMemberId(String committeeMemberId) {
		this.committeeMemberId = committeeMemberId;
	}

}
