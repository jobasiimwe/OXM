/**
 * 
 */
package org.agric.oxm.model.search;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.Gender;
import org.agric.oxm.model.Role;

/**
 * @author MARKPHILIP
 * 
 */
public class UserSearchParameters {
	private String nameOrUserName = null;
	private Gender gender = null;
	private Role role = null;
	private Concept userType;

	public UserSearchParameters() {

	}

	public String getNameOrUserName() {
		return nameOrUserName;
	}

	public void setNameOrUserName(String nameOrUserName) {
		this.nameOrUserName = nameOrUserName;
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

	public Concept getUserType() {
		return userType;
	}

	public void setUserType(Concept userType) {
		this.userType = userType;
	}

}
