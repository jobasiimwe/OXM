package org.agric.oxm.server.service.impl;

import java.util.Collections;
import java.util.List;

import org.agric.oxm.model.Permission;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.PermissionDAO;
import org.agric.oxm.server.dao.RoleDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

/**
 * 
 * @author Job
 * 
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private PermissionDAO permissionDAO;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Role role) throws ValidationException {
		if (StringUtils.isBlank(role.getName())
				|| StringUtils.isEmpty(role.getName())) {
			throw new ValidationException(
					"the name of the role is not supplied");
		}

		if (StringUtils.isBlank(role.getDescription())
				|| StringUtils.isEmpty(role.getDescription())) {
			throw new ValidationException(
					"the description of the role is not supplied");
		}

		if (StringUtils.isBlank(role.getId())
				|| StringUtils.isEmpty(role.getId())) {
			Role existingRole = roleDAO.searchUniqueByPropertyEqual("name",
					role.getName());
			if (existingRole != null) {
				throw new ValidationException(
						"a role is the given name already exists");
			}
		}

		roleDAO.save(role);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> getRoles() {
		List<Role> roles = roleDAO.searchByRecordStatus(RecordStatus.ACTIVE);
		Collections.sort(roles);
		return roles;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> getRolesByPage(Integer pageNo) {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);
		search.addSort("id", false, true);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}

		List<Role> roles = roleDAO.search(search);
		Collections.sort(roles);
		return roles;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(Role role) throws OperationFailedException {
		if (StringUtils.equalsIgnoreCase(role.getName(),
				Role.DEFAULT_ADMIN_ROLE)) {
			throw new OperationFailedException(
					"cannot delete default administration role");
		}

		roleDAO.remove(role);
	}

	@Override
	@Transactional(readOnly = true)
	public Role getRoleById(String id) {
		Role role = roleDAO.searchUniqueByPropertyEqual("id", id);
		if (role != null && role.getPermissions() != null)
			Collections.sort(role.getPermissions());
		return role;
	}

	@Override
	public Role getRoleByName(String name) {
		Role role = roleDAO.searchUniqueByPropertyEqual("name", name);
		if (role != null && role.getPermissions() != null)
			Collections.sort(role.getPermissions());
		return role;
	}

	// ==========================================================================================

	@Override
	public List<Permission> getPermissions() {
		List<Permission> permissions = permissionDAO
				.searchByRecordStatus(RecordStatus.ACTIVE);
		Collections.sort(permissions);
		return permissions;
	}

	@Override
	public Permission getPermissionById(String id) {
		return permissionDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	public void save(Permission permission) throws ValidationException {
		if (StringUtils.isBlank(permission.getName())
				|| StringUtils.isEmpty(permission.getName())) {
			throw new ValidationException(
					"the name of the permission is not supplied");
		}

		if (StringUtils.isBlank(permission.getDescription())
				|| StringUtils.isEmpty(permission.getDescription())) {
			throw new ValidationException(
					"the description of the permission is not supplied");
		}

		if (StringUtils.isBlank(permission.getId())
				|| StringUtils.isEmpty(permission.getId())) {
			Permission existingPerm = permissionDAO
					.searchUniqueByPropertyEqual("name", permission.getName());
			if (existingPerm != null) {
				throw new ValidationException(
						"a permission with the given name already exists");
			}
		}

		permissionDAO.save(permission);

	}

	@Override
	public void validate(Role role) throws ValidationException {
		if (StringUtils.isBlank(role.getName())
				|| StringUtils.isEmpty(role.getName())) {
			throw new ValidationException(
					"the name of the role is not supplied");
		}

		if (StringUtils.isBlank(role.getDescription())
				|| StringUtils.isEmpty(role.getDescription())) {
			throw new ValidationException(
					"the description of the role is not supplied");
		}

		Role existingRole = roleDAO.searchUniqueByPropertyEqual("name",
				role.getName());
		if (existingRole != null) {
			if (StringUtils.isBlank(role.getId())
					|| StringUtils.isEmpty(role.getId()))
				throw new ValidationException(
						"a role is the given name already exists");
			else {
				if (!role.equals(existingRole))
					throw new ValidationException(
							"a role is the given name already exists");
			}
		}

	}

	@Override
	@Secured(PermissionConstants.DELETE_ROLE)
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRolesByIds(String[] roleIds)
			throws OperationFailedException {
		for (String id : roleIds) {
			Role role = getRoleById(id);
			if (role != null)
				deleteRole(role);
		}
	}

}
