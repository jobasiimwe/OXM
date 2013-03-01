package org.agric.oxm.server.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.model.Permission;
import org.agric.oxm.model.User;
import org.agric.oxm.server.dao.PermissionDAO;
import org.agric.oxm.server.dao.UserDAO;
import org.agric.oxm.server.security.OXMUserDetails;
import org.agric.oxm.server.security.service.OXMUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class OXMUserDetailsServiceImpl implements OXMUserDetailsService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PermissionDAO permissionDAO;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public OXMUserDetails getUserDetailsForUser(User user) {
		OXMUserDetails userDetails = null;
		if (user != null) {
			List<GrantedAuthority> authorities = getUserAuthorities(user);
			if (authorities == null) {
				authorities = new ArrayList<GrantedAuthority>();
			}

			userDetails = new OXMUserDetails(user, true, true, true, true,
					authorities);
		}

		return userDetails;
	}

	protected List<GrantedAuthority> getUserAuthorities(User user) {
		List<GrantedAuthority> authorities = null;
		if (user != null) {
			authorities = new ArrayList<GrantedAuthority>();
			List<Permission> permissions = null;

			if (user.hasAdministrativePrivileges()) {
				permissions = permissionDAO.findAll();
			} else {
				permissions = user.findPermissions();
			}

			if (permissions != null && permissions.size() > 0) {
				for (Permission perm : permissions) {
					GrantedAuthority ga = new GrantedAuthorityImpl(
							perm.getName());
					authorities.add(ga);
				}
			}
		}

		return authorities;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		try {
			User user = userDAO.searchUniqueByPropertyEqual("username",
					username);
			if (user != null) {
				return getUserDetailsForUser(user);
			}
		} catch (Exception e) {
			log.error("Login error ", e);
			throw new UsernameNotFoundException(e.getMessage(), e);
		}

		return null;
	}
}
