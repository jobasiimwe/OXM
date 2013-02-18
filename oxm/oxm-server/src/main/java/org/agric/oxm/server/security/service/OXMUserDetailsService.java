package org.agric.oxm.server.security.service;
import org.agric.oxm.model.User;
import org.agric.oxm.server.security.OXMUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface OXMUserDetailsService extends UserDetailsService {

    OXMUserDetails getUserDetailsForUser(User user);
}
