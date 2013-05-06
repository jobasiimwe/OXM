package org.agric.oxm.server.service.impl;

import java.util.Collections;
import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.Permission;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.PermissionDAO;
import org.agric.oxm.server.dao.RoleDAO;
import org.agric.oxm.server.dao.UserDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.UserService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;

/**
 * Implements the {@link UserService} interface.
 * 
 * @author Job
 * 
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PermissionDAO permissionDAO;

	@Autowired
	private RoleDAO roleDAO;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserByUserName(String username) {
		User user = userDAO.searchUniqueByPropertyEqual("username", username);
		return user;
	}

	@Override
	@Secured(PermissionConstants.VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUsers(Integer pageNo) {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);
		search.addSort("firstName", false, true);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo != null && pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}

		List<User> users = userDAO.search(search);
		return users;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) {
		OXMSecurityUtil.prepUserCredentials(user);
		userDAO.save(user);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validate(User user) throws ValidationException {

		User existingUserWithSimilarUsername = getUserByUserName(user
				.getUsername());

		if (existingUserWithSimilarUsername != null) {
			if (StringUtils.isBlank(user.getId())
					|| StringUtils.isEmpty(user.getId()))
				throw new ValidationException("A user with this username - "
						+ user.getUsername() + " already exists");

			else if (!existingUserWithSimilarUsername.getId().equalsIgnoreCase(
					user.getId()))
				throw new ValidationException("A user -"
						+ existingUserWithSimilarUsername.getName()
						+ "- with this username - " + user.getUsername()
						+ " already exists");
		}

		if (user.getProducerOrg() == null
				&& user.getRoles().contains(
						new Role("4836AFAB-3D62-482c-BA9A-D9D15839C68A"))) {
			throw new ValidationException(
					"You must indicate Producer Organisation for users with ROLE-PRODUCER");
		}
		
		if (user.getProducerOrg() != null
				&& !user.getRoles().contains(
						new Role("4836AFAB-3D62-482c-BA9A-D9D15839C68A"))) {
			throw new ValidationException(
					"User can not belong to a producer Organisation if he doesn't have ROLE-PRODUCER");
		}
	}

	@Override
	@Secured(PermissionConstants.VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUsers() {
		List<User> users = userDAO.searchByRecordStatus(RecordStatus.ACTIVE);
		return users;
	}

	@Override
	@Secured(PermissionConstants.DELETE_USER)
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(User user) throws OperationFailedException {
		if (StringUtils.equalsIgnoreCase(user.getUsername(), "administrator")) {
			throw new OperationFailedException(
					"cannot delete default administrator");
		}

		userDAO.delete(user);
	}

	@Secured(PermissionConstants.DELETE_USER)
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteUserById(String id) throws OperationFailedException {
		User user = getUserById(id);

		if (StringUtils.equalsIgnoreCase(user.getUsername(), "administrator")) {
			throw new OperationFailedException(
					"cannot delete default administrator");
		}

		userDAO.delete(user);
	}

	@Override
	@Secured(PermissionConstants.DELETE_ROLE)
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUsersByIds(String[] ids) throws OperationFailedException {
		for (String id : ids) {
			User user = getUserById(id);
			if (user != null)
				deleteUser(user);
		}
	}

	@Override
	@Secured({ PermissionConstants.ADD_ROLE, PermissionConstants.EDIT_ROLE })
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveRole(Role role) {
		roleDAO.save(role);
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
	// @Secured(PermissionConstants.VIEW_ROLE)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> getRoles() {
		List<Role> roles = roleDAO.searchByRecordStatus(RecordStatus.ACTIVE);
		Collections.sort(roles);
		return roles;
	}

	@Override
	@Secured(PermissionConstants.VIEW_ROLE)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
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
	@Secured(PermissionConstants.DELETE_ROLE)
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(Role role) throws OperationFailedException {
		if (StringUtils.equalsIgnoreCase(role.getName(),
				Role.DEFAULT_ADMIN_ROLE)) {
			throw new OperationFailedException(
					"cannot delete default administration role");
		}

		if (StringUtils.equalsIgnoreCase(role.getName(), "ROLE_ANNOYMOUS_USER")) {
			throw new OperationFailedException(
					"cannot delete default user role");
		}

		roleDAO.remove(role);
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

	@Override
	public List<Permission> getPermissions() {
		List<Permission> permissions = permissionDAO
				.searchByRecordStatus(RecordStatus.ACTIVE);
		Collections.sort(permissions);
		return permissions;
	}

	@Override
	@Secured(PermissionConstants.VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserById(String userId) {
		return userDAO.searchUniqueByPropertyEqual("id", userId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Role getRoleById(String id) {
		Role role = roleDAO.searchUniqueByPropertyEqual("id", id);
		if (null != role.getPermissions())
			Collections.sort(role.getPermissions());
		return role;
	}

	@Override
	@Secured(PermissionConstants.VIEW_ROLE)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> findRolesByName(String searchString) {
		Search search = new Search();
		search.addFilter(new Filter("name", searchString, Filter.OP_LIKE));
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		List<Role> roles = roleDAO.search(search);
		Collections.sort(roles);
		return roles;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Permission getPermissionById(String id) {
		return permissionDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Override
	public void savePermision(Permission permission) throws ValidationException {
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
	public int getTotalNumberOfUsers() {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return userDAO.count(search);
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param userDAO
	 *            the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * Prepares the search to be used in searching users
	 * 
	 * @param query
	 * @return
	 */
	public static Search prepareUserSearchByLoginName(String query) {

		Search search = new Search();
		String param = new StringBuilder().append("%")
				.append(StringEscapeUtils.escapeSql(query)).append("%")
				.toString();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		search.addFilterLike("username", param);
		search.addSort("username", false, true);

		return search;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUsers(List<Concept> userTypes) {
		Search search = prepareConceptSearch(userTypes);
		List<User> users = userDAO.search(search);

		return users;
	}

	private Search prepareConceptSearch(List<Concept> Concepts) {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);

		Filter[] ConceptFilters = new Filter[Concepts.size()];

		for (int i = 0; i < Concepts.size(); i++) {
			ConceptFilters[i] = new Filter("Concept", Concepts.get(i),
					Filter.OP_EQUAL);
		}

		search.addFilterOr(ConceptFilters);

		search.addSort("firstName", false, true);

		return search;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUsers(List<Concept> Concepts, Integer pageNo) {
		Search search = prepareConceptSearch(Concepts);

		if (pageNo != null && pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}

		List<User> users = userDAO.search(search);
		return users;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public int getTotalNumberOfUsers(List<Concept> Concepts) {
		Search search = prepareConceptSearch(Concepts);
		return userDAO.count(search);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> searchWithParams(UserSearchParameters params,
			Integer pageNo) {

		Search search = prepareSearchUserWithParams(params);
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);
		/*
		 * if the page number is less than or equal to zero, no need for paging
		 */
		if (pageNo > 0) {
			search.setPage(pageNo - 1);
		} else {
			search.setPage(0);
		}

		List<User> users = userDAO.search(search);
		return users;
	}

	@Override
	@Secured({ PermissionConstants.VIEW_USER })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long numberOfUsersWithSearchParams(UserSearchParameters params) {
		Search search = prepareSearchUserWithParams(params);
		return userDAO.count(search);
	}

	public Search prepareSearchUserWithParams(UserSearchParameters params) {
		Search search = new Search();

		Filter nameFilter;

		if (StringUtils.isNotBlank(params.getNameOrUserName())) {

			Filter userNameFilter = new Filter("username", "%"
					+ params.getNameOrUserName() + "%", Filter.OP_ILIKE);

			if (params.getNameOrUserName().contains(" ")) {
				String[] names = params.getNameOrUserName().split(" ");

				Filter[] nameFilters = new Filter[names.length];
				for (int i = 0; i < names.length; i++) {
					nameFilters[i] = new Filter("name", "%" + names[i] + "%",
							Filter.OP_ILIKE);
				}

				nameFilter = Filter.and(nameFilters);
			} else {
				nameFilter = new Filter("name", "%"
						+ params.getNameOrUserName() + "%", Filter.OP_ILIKE);
			}

			search.addFilterOr(nameFilter, userNameFilter);

		}

		if (params.getGender() != null) {
			search.addFilterEqual("gender", params.getGender());
		}

		if (params.getRole() != null) {
			search.addFilterEqual("roles.name", params.getRole().getName());
		}

		search.addSort("name", false, true);

		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return search;
	}

}
