package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Permission;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.ValidationException;

/**
 * 
 * @author Job
 * 
 */
public interface RoleService {

	void save(Role role) throws ValidationException;

	List<Role> getRoles();

	void deleteRole(Role role) throws OperationFailedException;

	Role getRoleById(String id);

	Role getRoleByName(String name);

	Permission getPermissionById(String id);

	List<Permission> getPermissions();

	void save(Permission permission) throws ValidationException;

	List<Role> getRolesByPage(Integer pageNo);

	// void saveRole(Role role);
	//
	void validate(Role role) throws ValidationException;
	//
	// List<Role> getRoles();
	//
	// Role getRoleById(String text);
	//
	// List<Role> findRolesByName(String searchString);
	//
	// List<Role> getRolesByPage(Integer pageNo);
	//
	// void deleteRole(Role role) throws OperationFailedException;
	//
	void deleteRolesByIds(String[] roleIds) throws OperationFailedException;
	//
	// List<Permission> getPermissions();
	//
	// void savePermision(Permission permission) throws ValidationException;
	//
	// Permission getPermissionById(String id);
}
