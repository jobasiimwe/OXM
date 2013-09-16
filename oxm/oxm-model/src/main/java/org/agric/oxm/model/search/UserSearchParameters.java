/**
 * 
 */
package org.agric.oxm.model.search;

import org.agric.oxm.model.County;
import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.Village;
import org.agric.oxm.model.enums.Condition;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.enums.HouseHoldCategory;

/**
 * @author Job
 * 
 */
public class UserSearchParameters {
	private String nameOrUserName = null;
	private ProducerOrg producerOrg;
	private Gender gender = null;
	private HouseHoldCategory houseHoldCategory = null;

	private Double landSize1;
	private Double landSize2;

	private Condition landSizeCondition1;
	private Condition landSizeCondition2;

	private Role role = null;

	private String staffMemberId;
	private String committeeId;
	private String committeeMemberId;

	private District district;
	private County county;
	private SubCounty subCounty;
	private Parish parish;
	private Village village;

	private String smsText, smsRecipientsText;

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

	public HouseHoldCategory getHouseHoldCategory() {
		return houseHoldCategory;
	}

	public void setHouseHoldCategory(HouseHoldCategory houseHoldCategory) {
		this.houseHoldCategory = houseHoldCategory;
	}

	// ================================================================================

	public Double getLandSize1() {
		return landSize1;
	}

	public void setLandSize1(Double landSize1) {
		this.landSize1 = landSize1;
	}

	public Double getLandSize2() {
		return landSize2;
	}

	public void setLandSize2(Double landSize2) {
		this.landSize2 = landSize2;
	}

	public Condition getLandSizeCondition1() {
		return landSizeCondition1;
	}

	public void setLandSizeCondition1(Condition landSizeCondition1) {
		this.landSizeCondition1 = landSizeCondition1;
	}

	public Condition getLandSizeCondition2() {
		return landSizeCondition2;
	}

	public void setLandSizeCondition2(Condition landSizeCondition2) {
		this.landSizeCondition2 = landSizeCondition2;
	}

	// ================================================================================

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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	public SubCounty getSubCounty() {
		return subCounty;
	}

	public void setSubCounty(SubCounty subCounty) {
		this.subCounty = subCounty;
	}

	public Parish getParish() {
		return parish;
	}

	public void setParish(Parish parish) {
		this.parish = parish;
	}

	public Village getVillage() {
		return village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getSmsRecipientsText() {
		return smsRecipientsText;
	}

	public void setSmsRecipientsText(String smsRecipientsText) {
		this.smsRecipientsText = smsRecipientsText;
	}

}
