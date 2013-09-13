package org.agric.oxm.server.service;

import java.util.List;

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

	void saveUser(User user);

	void validate(User user) throws ValidationException;

	Boolean usernameExists(String username);

	void deleteUser(User user) throws OperationFailedException;

	void deleteUserById(String id) throws OperationFailedException;

	void deleteUsersByIds(String[] ids) throws OperationFailedException;

	// ===========================================================================

	List<User> searchWithParams(UserSearchParameters params);

	List<User> searchWithParams(UserSearchParameters params, Integer pageNo);

	long numberOfUsersWithSearchParams(UserSearchParameters params);

}
