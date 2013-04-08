/**
 * 
 */
package org.agric.oxm.model.search;

import org.agric.oxm.model.Gender;
import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.Role;

/**
 * @author Job
 * 
 */
public class UserSearchParameters {
	private String nameOrUserName = null;
	private ProducerOrganisation producerOrg;
	private Gender gender = null;
	private Role role = null;

	public UserSearchParameters() {

	}

	public UserSearchParameters(ProducerOrganisation pOrg, Role role) {
		this.setProducerOrg(pOrg);
		this.setRole(role);
	}

	public String getNameOrUserName() {
		return nameOrUserName;
	}

	public void setNameOrUserName(String nameOrUserName) {
		this.nameOrUserName = nameOrUserName;
	}

	public ProducerOrganisation getProducerOrg() {
		return producerOrg;
	}

	public void setProducerOrg(ProducerOrganisation producerOrg) {
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
}
