package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.Permission;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;

/**
 * 
 * @author Job
 */
public interface UserService {

	User getUserByUserName(String username);

	User getUserById(String userId);

	List<User> getUsers(Integer pageNo);

	List<User> getUsers();

	int getTotalNumberOfUsers();

	List<User> getUsers(List<Concept> userTypes);

	List<User> getUsers(List<Concept> userTypes, Integer pageNo);

	int getTotalNumberOfUsers(List<Concept> userTypes);

	List<User> searchWithParams(UserSearchParameters params, Integer pageNo);

	long numberOfUsersWithSearchParams(UserSearchParameters params);

	void saveUser(User user);

	void validate(User user) throws ValidationException;

	Boolean usernameExists(String username);

	void deleteUser(User user) throws OperationFailedException;

	void deleteUserById(String id) throws OperationFailedException;

	void deleteUsersByIds(String[] ids) throws OperationFailedException;

	void saveRole(Role role);

	void validate(Role role) throws ValidationException;

	List<Role> getRoles();

	Role getRoleById(String text);

	List<Role> findRolesByName(String searchString);

	List<Role> getRolesByPage(Integer pageNo);

	void deleteRole(Role role) throws OperationFailedException;

	void deleteRolesByIds(String[] roleIds) throws OperationFailedException;

	List<Permission> getPermissions();

	void savePermision(Permission permission) throws ValidationException;

	Permission getPermissionById(String id);

}
