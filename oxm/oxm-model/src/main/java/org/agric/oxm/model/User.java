package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseData implements Comparable<User> {

	private String username;

	private String password;

	private Set<Role> roles;

	private UserStatus status;

	private String clearTextPassword;

	private String salt;

	private String firstName;

	private String lastName;

	private Gender gender = Gender.UNKNOWN;

	private Date dateOfBirth;

	private String phone1;

	private String phone2;

	private List<Concept> userTypes;

	private byte[] profilePicture = new byte[1];

	public User() {
		super();
	}

	@Transient
	public String getClearTextPassword() {
		return clearTextPassword;
	}

	public void setClearTextPassword(String clearTextPassword) {
		this.clearTextPassword = clearTextPassword;
	}

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

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "role_users", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public synchronized Set<Role> getRoles() {
		synchronized (this) {
			return roles;
		}
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

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "user_status", nullable = false)
	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Column(name = "salt", nullable = false)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "first_name", nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "user_userTypes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "userType_id"))
	public List<Concept> getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(List<Concept> userTypes) {
		this.userTypes = userTypes;
	}

	@Lob()
	@Column(name = "picture", nullable = true)
	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public boolean hasNewPassword() {
		return (clearTextPassword != null && clearTextPassword.trim().length() > 0);
	}

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

	@Transient
	public String getFullName() {
		String name = "";
		if (!StringUtils.isEmpty(this.getFirstName())
				&& !StringUtils.isBlank(this.getFirstName()))
			name = this.getFirstName();

		if (!StringUtils.isEmpty(this.getLastName())
				&& !StringUtils.isBlank(this.getLastName()))
			name += StringUtils.isEmpty(name) ? this.getLastName() : " "
					+ this.getLastName();

		name = name.trim();

		return name;
	}

	@Override
	public int compareTo(User o) {
		if (this.getUsername() == null || o.getUsername() == null)
			return 0;
		return this.username.compareToIgnoreCase(o.username);
	}

}
