package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Role;
import org.agric.oxm.model.User;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.UserSearchParameters;
import org.agric.oxm.server.OXMConstants;
import org.agric.oxm.server.dao.UserDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.UserService;
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

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserById(String userId) {
		User user = userDAO.searchUniqueByPropertyEqual("id", userId);
		return user;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User getUserByUserName(String username) {
		User user = userDAO.searchUniqueByPropertyEqual("username", username);
		return user;
	}

	@Override
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

	//
	@Override
	@Secured(PermissionConstants.VIEW_USER)
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> getUsers() {
		List<User> users = userDAO.searchByRecordStatus(RecordStatus.ACTIVE);
		return users;
	}

	@Override
	public int getTotalNumberOfUsers() {
		Search search = new Search();
		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return userDAO.count(search);
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
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Boolean usernameExists(String username) {

		User existingUserWithSimilarUsername = getUserByUserName(username);

		if (existingUserWithSimilarUsername != null) {
			return true;
		}
		return false;
	}

	// ======================================================================

	@Override
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
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUsersByIds(String[] ids) throws OperationFailedException {
		for (String id : ids) {
			User user = getUserById(id);
			if (user != null)
				deleteUser(user);
		}
	}

	// ======================================================================

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> searchWithParams(UserSearchParameters params) {
		Search search = prepareSearch(params);
		List<User> users = userDAO.search(search);
		return users;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<User> searchWithParams(UserSearchParameters params,
			Integer pageNo) {

		Search search = prepareSearch(params);
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
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public long numberOfUsersWithSearchParams(UserSearchParameters params) {
		Search search = prepareSearch(params);
		return userDAO.count(search);
	}

	public Search prepareSearch(UserSearchParameters params) {
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
		if (params.getHouseHoldCategory() != null) {
			search.addFilterEqual("houseHoldCategory",
					params.getHouseHoldCategory());
		}

		if (params.getRole() != null) {
			search.addFilterEqual("roles.name", params.getRole().getName());
		}

		if (params.getProducerOrg() != null) {
			search.addFilterEqual("producerOrg", params.getProducerOrg());
		}
		if (params.getDistrict() != null) {
			search.addFilterEqual("producerOrg.district", params.getDistrict());
		}
		if (params.getCounty() != null) {
			search.addFilterEqual("producerOrg.county", params.getCounty());
		}
		if (params.getSubCounty() != null) {
			search.addFilterEqual("producerOrg.subCounty",
					params.getSubCounty());
		}
		if (params.getParish() != null) {
			search.addFilterEqual("producerOrg.parish", params.getParish());
		}
		if (params.getVillage() != null) {
			search.addFilterEqual("producerOrg.village", params.getVillage());
		}
		if (params.getVillage() != null) {
			search.addFilterEqual("producerOrg.village", params.getVillage());
		}

		search.addSort("name", false, true);

		search.addFilterEqual("recordStatus", RecordStatus.ACTIVE);
		return search;
	}

	public static User getLoggedInUser() throws SessionExpiredException  {
		return OXMSecurityUtil.getLoggedInUser();
	}
}
