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

	/**
	 * gets a user based on their username
	 * 
	 * @param username
	 *            string for login name
	 * @return User or null if no much found
	 */
	User getUserByUserName(String username);

	/**
	 * gets a user with a given Id
	 * 
	 * @param userId
	 * @return
	 */
	User getUserById(String userId);

	/**
	 * gets a list of users on a given page number
	 * 
	 * @param pageNo
	 * @return
	 */
	List<User> getUsers(Integer pageNo);

	/**
	 * gets a list of all users in the system
	 * 
	 * @return
	 */
	List<User> getUsers();

	int getTotalNumberOfUsers();

	/**
	 * gets a list of users in a given category
	 * 
	 * @return
	 */
	List<User> getUsers(List<Concept> userTypes);

	/**
	 * gets a list of users in a given category
	 * 
	 * @return
	 */
	List<User> getUsers(List<Concept> userTypes, Integer pageNo);

	/**
	 * 
	 * @return
	 */
	int getTotalNumberOfUsers(List<Concept> userTypes);

	List<User> searchWithParams(UserSearchParameters params, Integer pageNo);

	long numberOfUsersWithSearchParams(UserSearchParameters params);

	/**
	 * saves a given user
	 * 
	 * @param user
	 * @throws MohrValidationException
	 *             thrown when the validation of the user fails.
	 */
	void saveUser(User user);

	/**
	 * saves a given user
	 * 
	 * @param user
	 * @throws ValidationException
	 */
	void validate(User user) throws ValidationException;

	/**
	 * deletes a given user from the system
	 * 
	 * @param user
	 * @throws OperationFailedException
	 */
	void deleteUser(User user) throws OperationFailedException;

	/**
	 * deletes a user with the given id from the system
	 * 
	 * @param id
	 * @throws OperationFailedException
	 */
	void deleteUserById(String id) throws OperationFailedException;

	/**
	 * saves a given role
	 * 
	 * @param role
	 * @throws MohrValidationException
	 *             thrown when the validation of the user fails.
	 */
	void saveRole(Role role);

	void validate(Role role) throws ValidationException;

	/**
	 * gets a list of all
	 * 
	 * @return
	 */
	List<Role> getRoles();

	/**
	 * @param text
	 * @return
	 */
	Role getRoleById(String text);

	/**
	 * finds roles whose name(s) are like the searchString provided. This means
	 * that if a search string like 'ct' is provided, all roles whose names
	 * begin with ct characters are returned
	 * 
	 * @param searchString
	 * @return
	 */
	List<Role> findRolesByName(String searchString);

	/**
	 * gets a list of roles on a given page number
	 * 
	 * @param pageNo
	 * @return
	 */
	List<Role> getRolesByPage(Integer pageNo);

	/**
	 * deletes a given role from the system
	 * 
	 * @param role
	 * @throws OperationFailedException
	 */
	void deleteRole(Role role) throws OperationFailedException;

	/**
	 * gets a list of all permissions
	 * 
	 * @return
	 */
	List<Permission> getPermissions();

	/**
	 * saves a given {@link Permission}
	 * 
	 * @param permission
	 * @throws ValidationException
	 */
	void savePermision(Permission permission) throws ValidationException;

	/**
	 * gets the permission with the given id
	 * 
	 * @param id
	 * @return
	 */
	Permission getPermissionById(String id);

}
