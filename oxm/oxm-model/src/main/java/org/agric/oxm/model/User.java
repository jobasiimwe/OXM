package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.agric.oxm.model.enums.HouseHoldCategory;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.enums.MaritalStatus;
import org.agric.oxm.model.enums.UserStatus;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseData implements Comparable<User> {

	// user account
	private String username, password, salt, clearTextPassword;
	private UserStatus status;

	private Set<Role> roles;

	// address
	private String address, phone1, phone2;

	// personal information
	private String name;
	private Gender gender;
	private Date dateOfBirth;
	private Integer age;
	private byte[] profilePicture = new byte[1];

	// producer information
	private ProducerOrg producerOrg;
	private Date dateOfJoining;
	private Integer yearOfJoining;

	private Double landSize;
	private List<LandUse> landUses;
	private GisCordinate gisCordinates;

	private MaritalStatus maritalStatus;
	private HouseHoldCategory houseHoldCategory;

	// general hidden
	private User createdBy;
	private Date dateCreated;

	public User() {
		super();
	}

	public User(ProducerOrg pOrg) {
		super();
		this.setProducerOrg(pOrg);
		// this.setSubCounty(pOrg.getSubCounty());
		// this.setParish(pOrg.getParish());
		// this.setVillage(pOrg.getVillage());
	}

	public User(ProducerOrg pOrg, String name, Gender gender, Integer age,
			MaritalStatus maritalStatus, HouseHoldCategory categoryOfHouseHold) {
		super();
		this.setProducerOrg(pOrg);
		this.setName(name);
		this.setGender(gender);
		this.setAge(age);
		this.setMaritalStatus(maritalStatus);
		this.setHouseHoldCategory(categoryOfHouseHold);

	}

	// ===================================================================================

	@Column(name = "username", nullable = false, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "salt", nullable = false)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean hasNewPassword() {
		return (clearTextPassword != null && clearTextPassword.trim().length() > 0);
	}

	@Transient
	public String getClearTextPassword() {
		return clearTextPassword;
	}

	public void setClearTextPassword(String clearTextPassword) {
		this.clearTextPassword = clearTextPassword;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_status", nullable = false)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	// ===================================================================================

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "role_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public synchronized Set<Role> getRoles() {
		synchronized (this) {
			return roles;
		}
	}

	@Transient
	public String getRolesString() {
		String str = "";
		if (roles != null) {
			int i = 0;

			for (Iterator<Role> rit = roles.iterator(); rit.hasNext();) {
				Role role = rit.next();
				String roleNameToDisplay = role.getName();

				if (role.getName().toUpperCase().contains("ROLE_"))
					roleNameToDisplay = role.getName().substring(5);

				if (StringUtils.isBlank(str))
					str += roleNameToDisplay;
				else
					str += ", " + roleNameToDisplay;
				i++;
				if (i == 2)
					break;
			}
			if (roles.size() > 2)
				str += ", and " + (roles.size() - 2) + " more";
		}
		return str;
	}

	public synchronized void setRoles(Set<Role> roles) {
		synchronized (this) {
			this.roles = roles;
		}
	}

	public synchronized void addRole(Role role) {
		synchronized (this) {
			if (roles == null) {
				roles = new HashSet<Role>();
			}

			if (!this.roles.contains(role)) {
				roles.add(role);
				role.addUser(this);
			}
		}
	}

	public synchronized void removeRole(Role role) {
		synchronized (this) {
			if (roles != null) {
				for (Role r : roles) {
					if (r.getName().equals(role.getName())) {
						roles.remove(role);
						break;
					}
				}
			}
		}
	}

	// ===================================================================================

	@Column(name = "address", nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "phone1", nullable = true)
	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Column(name = "phone2", nullable = true)
	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	// ===================================================================================

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender", nullable = true)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_of_birth", nullable = true)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "age", nullable = true)
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Lob()
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "picture", nullable = true)
	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	// =========================================================================================

	@ManyToOne
	@JoinColumn(name = "producer_organisation_id", nullable = true)
	public ProducerOrg getProducerOrg() {
		return producerOrg;
	}

	public void setProducerOrg(ProducerOrg producerOrg) {
		this.producerOrg = producerOrg;
	}

	@Column(name = "date_of_joining", nullable = true)
	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	@Column(name = "year_of_joining", nullable = true)
	public Integer getYearOfJoining() {
		return yearOfJoining;
	}

	public void setYearOfJoining(Integer yearOfJoining) {
		this.yearOfJoining = yearOfJoining;
	}

	// =========================================================================================

	@Column(name = "land_size", nullable = true)
	public Double getLandSize() {
		return landSize;
	}

	public void setLandSize(Double landSize) {
		this.landSize = landSize;
	}

	public void addLand(LandUse landUse) {
		if (landUse == null) {
			return;
		}

		if (this.getLandUses() == null) {
			this.setLandUses(new ArrayList<LandUse>());
		}

		this.getLandUses().add(landUse);
		landUse.setProducer(this);
	}

	public void removeLand(LandUse landUse) {
		if (landUse == null || this.getLandUses() == null) {
			return;
		}

		this.getLandUses().remove(landUse);
	}

	@OneToMany(mappedBy = "producer", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<LandUse> getLandUses() {
		return landUses;
	}

	public void setLandUses(List<LandUse> landUses) {
		this.landUses = landUses;
	}

	// =================

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "marital_status", nullable = true)
	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "house_hold_category", nullable = true)
	public HouseHoldCategory getHouseHoldCategory() {
		return houseHoldCategory;
	}

	public void setHouseHoldCategory(HouseHoldCategory houseHoldCategory) {
		this.houseHoldCategory = houseHoldCategory;
	}

	@ManyToOne
	@JoinColumn(name = "gis_cordinates_id", nullable = true)
	public GisCordinate getGisCordinates() {
		return gisCordinates;
	}

	public void setGisCordinates(GisCordinate gisCordinates) {
		this.gisCordinates = gisCordinates;
	}

	// ============================================================================================

	@Column(name = "date_created", nullable = true)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = true)
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	// ============================================================================================

	public List<Permission> findPermissions() {
		List<Permission> permissions = null;
		if (roles != null && roles.size() > 0) {
			permissions = new ArrayList<Permission>();
			for (Role role : roles) {
				if (role.getPermissions() != null
						&& role.getPermissions().size() > 0) {
					for (Permission perm : role.getPermissions()) {
						permissions.add(perm);
					}
				}
			}
		}
		return permissions;
	}

	public boolean hasAdministrativePrivileges() {
		if (roles != null) {
			for (Role role : roles) {
				if (role.checkIfDefaultAdminRole()) {
					return true;
				}
			}
		}
		return false;
	}

	// ==============================================================================================

	@Override
	public int compareTo(User o) {
		if (StringUtils.isNotBlank(this.getName())
				&& StringUtils.isNotBlank(o.getName()))
			return this.getName().compareToIgnoreCase(o.getName());
		else
			;
		return this.username.compareToIgnoreCase(o.username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	// ==============================================================================================

	@Transient
	public String getAddressString() {
		if (StringUtils.isNotBlank(address))
			return address;

		if (producerOrg != null)
			return producerOrg.getDistrictString();

		return "";
	}

	// ===================================================================

	// @Transient
	// public District getDistrict() {
	// if (null != district)
	// return district;
	//
	// if (subCounty != null)
	// return subCounty.getCounty().getDistrict();
	//
	// return null;
	// }
	//
	// public void setDistrict(District district) {
	// this.setDistrict(district);
	// }
	//
	// @Transient
	// public County getCounty() {
	// if (null != county)
	// return county;
	//
	// if (subCounty != null)
	// return subCounty.getCounty();
	//
	// return null;
	// }
	//
	// public void setCounty(County county) {
	// this.setCounty(county);
	// }
	//
	// @ManyToOne
	// @JoinColumn(name = "subcounty_id", nullable = true)
	// public SubCounty getSubCounty() {
	// return subCounty;
	// }
	//
	// public void setSubCounty(SubCounty subCounty) {
	// this.subCounty = subCounty;
	// }
	//
	// @ManyToOne
	// @JoinColumn(name = "parish_id", nullable = true)
	// public Parish getParish() {
	// return parish;
	// }
	//
	// public void setParish(Parish parish) {
	// this.parish = parish;
	// }
	//
	// @ManyToOne
	// @JoinColumn(name = "village_id", nullable = true)
	// public Village getVillage() {
	// return village;
	// }
	//
	// public void setVillage(Village village) {
	// this.village = village;
	// }

}
