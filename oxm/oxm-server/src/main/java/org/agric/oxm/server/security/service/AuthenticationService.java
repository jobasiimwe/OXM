package org.agric.oxm.server.security.service;

import org.agric.oxm.model.User;

public interface AuthenticationService {

    User authenticate(String username, String password,
	    boolean attachUserToSecurityContext);

    Boolean isValidUserPassword(User user, String loginPassword);

    User authenticateMobileUser(String username, String password);
}
