package org.agric.oxm.server.security.service.impl;

import org.agric.oxm.model.User;
import org.agric.oxm.server.dao.UserDAO;
import org.agric.oxm.server.security.OXMUserDetails;
import org.agric.oxm.server.security.service.AuthenticationService;
import org.agric.oxm.server.security.service.OXMUserDetailsService;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service("authenticationService")
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OXMUserDetailsService oXMUserDetailsService;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public User authenticate(String username, String password,
	    boolean attachUserToSecurityContext) {
	User user = null;
	if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
	    user = userDAO.searchUniqueByPropertyEqual("username", username);
	    if (user != null && isValidUserPassword(user, password)) {
		if (attachUserToSecurityContext) {
		    OXMUserDetails userDetails = (OXMUserDetails) oXMUserDetailsService
			    .getUserDetailsForUser(user);
		    if (userDetails != null) {
			OXMSecurityUtil.setSecurityContext(userDetails);
		    }
		} else {
		    return user;
		}
	    } else {
		log.error("Access denied for " + user.getUsername());
		throw new AccessDeniedException("password and username mismatch.");
	    }
	}

	return user;
    }

    @Override
    public Boolean isValidUserPassword(User user, String password) {

	if (user == null || StringUtils.isBlank(password) || StringUtils.isBlank(user.getSalt())) {
	    return false;
	}

	String hashedPassword = OXMSecurityUtil.encodeString(password + user.getSalt());
	if (hashedPassword.equals(user.getPassword())) {
	    return true;
	} else {
	    hashedPassword = OXMSecurityUtil.encodeString2(password + user.getSalt());
	    if (hashedPassword.equals(user.getPassword())) {
		return true;
	    }
	}

	return false;
    }

    public UserDAO getUserDAO() {
	return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
	this.userDAO = userDAO;
    }

    public OXMUserDetailsService getOXMUserDetailsService() {
	return oXMUserDetailsService;
    }

    public void setOXMUserDetailsService(
	    OXMUserDetailsService oxmUserDetailsService) {
	this.oXMUserDetailsService = oxmUserDetailsService;
    }

    @Override
    public User authenticateMobileUser(String username, String password) {
	
	User user = null;
	if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
	    user = userDAO.searchUniqueByPropertyEqual("username", username);
	    if (user != null && isValidUserPassword(user, password)) {
		    return user;
	    } else{
		user = null;
		return user;
	    }
	}
	return user;
    }
}
