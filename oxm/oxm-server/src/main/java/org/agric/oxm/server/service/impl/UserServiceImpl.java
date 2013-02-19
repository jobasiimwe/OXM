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
@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PermissionDAO permissionDAO;

	@Autowired
	private RoleDAO roleDAO;

	// private Logger log = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#findUserByUsername(java.lang
	 * .String)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserByUserName(String username) {
		User user = userDAO.searchUniqueByPropertyEqual("username", username);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mak.cis.mohr.api.service.UserService#getUsers(int)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_USER)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#saveUser(org.mak.cis.mohr.model
	 * .User)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_SAVE_USER)
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) {

		// check for clear text password and
		// assign the hashedpassword and the salt from the old object
		OXMSecurityUtil.prepUserCredentials(user);

		userDAO.save(user);
	}

	@Override
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
						+ existingUserWithSimilarUsername.getFullName()
						+ "- with this username - " + user.getUsername()
						+ " already exists");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mak.cis.mohr.api.service.UserService#getUsers()
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUsers() {
		List<User> users = userDAO.searchByRecordStatus(RecordStatus.ACTIVE);
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#deleteUser(org.mak.cis.mohr.
	 * model.User)
	 */
	@Override
	@Secured(PermissionConstants.PERM_DELETE_USER)
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(User user) throws OperationFailedException {
		if (StringUtils.equalsIgnoreCase(user.getUsername(), "administrator")) {
			throw new OperationFailedException(
					"cannot delete default administrator");
		}

		userDAO.delete(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.rms.api.service.UserService#deleteUserById(java.lang.String)
	 */
	@Secured(PermissionConstants.PERM_DELETE_USER)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#saveRole(org.mak.cis.mohr.model
	 * .Role)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_SAVE_ROLE)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mak.cis.mohr.api.service.UserService#getRoles()
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_ROLE)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> getRoles() {
		List<Role> roles = roleDAO.searchByRecordStatus(RecordStatus.ACTIVE);
		Collections.sort(roles);
		return roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#getRolesByPage(java.lang.Integer
	 * )
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_ROLE)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#deleteRole(org.mak.cis.mohr.
	 * model.Role)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_DELETE_ROLE)
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(Role role) throws OperationFailedException {
		if (StringUtils.equalsIgnoreCase(role.getName(),
				Role.DEFAULT_ADMIN_ROLE)) {
			throw new OperationFailedException(
					"cannot delete default administration role");
		}

		roleDAO.remove(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mak.cis.mohr.api.service.UserService#getPermissions()
	 */
	@Override
	public List<Permission> getPermissions() {
		List<Permission> permissions = permissionDAO
				.searchByRecordStatus(RecordStatus.ACTIVE);
		Collections.sort(permissions);
		return permissions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#getUserById(java.lang.String)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserById(String userId) {
		return userDAO.searchUniqueByPropertyEqual("id", userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#getRoleById(java.lang.String)
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_ROLE)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Role getRoleById(String id) {
		Role role = roleDAO.searchUniqueByPropertyEqual("id", id);
		if (null != role.getPermissions())
			Collections.sort(role.getPermissions());
		return role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#findRolesByName(java.lang.String
	 * )
	 */
	@Override
	// @Secured(PermissionConstants.PERM_VIEW_ROLE)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Role> findRolesByName(String searchString) {
		Search search = new Search();
		search.addFilter(new Filter("name", searchString, Filter.OP_LIKE));
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		List<Role> roles = roleDAO.search(search);
		Collections.sort(roles);
		return roles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#getPermissionById(java.lang.
	 * String)
	 */
	@Override
	public Permission getPermissionById(String id) {
		return permissionDAO.searchUniqueByPropertyEqual("id", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mak.cis.mohr.api.service.UserService#savePermision(org.mak.cis.mohr
	 * .model.Permission)
	 */
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
	public List<User> searchWithParams(UserSearchParameters params,
			Integer pageNo) {

		Search search = prepareSearchUserWithParams(params);
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
	public long numberOfUsersWithSearchParams(UserSearchParameters params) {
		Search search = prepareSearchUserWithParams(params);
		return userDAO.count(search);
	}

	public Search prepareSearchUserWithParams(UserSearchParameters params) {
		Search search = new Search();
		search.setMaxResults(OXMConstants.MAX_NUM_PAGE_RECORD);

		Filter firstNameFilter;
		Filter lastNameFilter;
		Filter userNameFilter;

		if (StringUtils.isNotBlank(params.getNameOrUserName())) {
			if (params.getNameOrUserName().contains(" ")) {
				String[] names = params.getNameOrUserName().split(" ");

				Filter[] firstNameFilters = new Filter[names.length];
				Filter[] lastNameFilters = new Filter[names.length];
				Filter[] userNameFilters = new Filter[names.length];
				for (int i = 0; i < names.length; i++) {
					firstNameFilters[i] = new Filter("firstName", "%"
							+ names[i] + "%", Filter.OP_ILIKE);
					lastNameFilters[i] = new Filter("lastName", "%" + names[i]
							+ "%", Filter.OP_ILIKE);
					userNameFilters[i] = new Filter("username", "%" + names[i]
							+ "%", Filter.OP_ILIKE);
				}

				firstNameFilter = Filter.and(firstNameFilters);
				lastNameFilter = Filter.and(lastNameFilters);
				userNameFilter = Filter.and(userNameFilters);

			} else {
				firstNameFilter = new Filter("firstName", "%"
						+ params.getNameOrUserName() + "%", Filter.OP_ILIKE);

				lastNameFilter = new Filter("lastName", "%"
						+ params.getNameOrUserName() + "%", Filter.OP_ILIKE);

				userNameFilter = new Filter("username", "%"
						+ params.getNameOrUserName() + "%", Filter.OP_ILIKE);

			}

			search.addFilterOr(firstNameFilter, lastNameFilter, userNameFilter);

		}

		if (params.getGender() != null) {
			search.addFilterEqual("gender", params.getGender());
		}

		if (params.getRole() != null) {
			search.addFilterEqual("roles.name", params.getRole().getName());
		}

		if (params.getUserType() != null) {
			search.addFilterEqual("userTypes", params.getUserType());
		}

		search.addSort("firstName", false, true);
		search.addSort("lastName", false, true);
		search.addSort("username", false, true);

		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return search;
	}

}
