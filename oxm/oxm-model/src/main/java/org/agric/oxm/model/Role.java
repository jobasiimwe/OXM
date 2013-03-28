package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "roles")
public class Role extends BaseData implements Comparable<Role> {

	public static final String DEFAULT_ADMIN_ROLE = "ROLE_ADMINISTRATOR";

	private String name;
	private String description;
	private List<Permission> permissions;
	private List<User> users;

	public Role() {

	}

	public Role(String name, String description, List<Permission> permissions,
			List<User> users) {
		super();
		this.name = name;
		this.description = description;
		this.permissions = permissions;
		this.users = users;
	}

	@Column(name = "role_name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany
	@JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Transient
	public String getPermissionsString() {
		String returnString = "";

		if (permissions != null) {
			if (permissions.size() > 0) {
				for (int i = 0; i < permissions.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += permissions.get(i).getName();
					else
						returnString += ", " + permissions.get(i).getName();
				}
				if (permissions.size() > 3) {
					int x = permissions.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		if (StringUtils.isBlank(returnString))
			returnString = "--";
		return returnString;
	}

	public void addPermission(Permission permission) {
		this.getPermissions().add(permission);
	}

	public void removePermission(Permission permission) {
		if (this.getPermissions().contains(permission)) {
			this.getPermissions().remove(permission);
		}
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "roles", targetEntity = User.class)
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		if (users == null) {
			users = new ArrayList<User>();
		}

		if (user != null) {
			if (!this.users.contains(user)) {
				users.add(user);
				user.addRole(this);
			}
		}
	}

	public void removeUser(User user) {
		if (user == null || users == null || users.size() == 0) {
			return;
		}

		if (this.getUsers().contains(user)) {
			getUsers().remove(user);
			user.removeRole(this);
		}
	}

	public boolean checkIfDefaultAdminRole() {
		return this.getName().equals(Role.DEFAULT_ADMIN_ROLE);
	}

	@Override
	public String toString() {
		if (this.getName() != null && this.getName().trim().length() > 0)
			return this.getName();

		return super.toString();
	}

	@Override
	public int compareTo(Role o) {
		return this.getName().compareToIgnoreCase(o.getName());
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
		Role other = (Role) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

}
