package org.agric.oxm.server.security.authentication;

import org.agric.oxm.server.security.OXMUserDetails;
import org.agric.oxm.server.security.service.AuthenticationService;
import org.agric.oxm.server.security.service.OXMUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class OXMAuthenticationProvider implements AuthenticationProvider {

    private OXMUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        UserDetails userDetails = getUserDetailsService().loadUserByUsername(
                (String) authentication.getPrincipal());
        if (userDetails != null) {
            if (authenticationService.isValidUserPassword(
                    ((OXMUserDetails) userDetails).getSystemUser(),
                    (String) authentication.getCredentials())) {
                return new UsernamePasswordAuthenticationToken(userDetails,
                        authentication.getCredentials(), userDetails.getAuthorities());
            } else {
                throw new AuthenticationServiceException("password don't match");
            }
        } else {
            throw new AuthenticationCredentialsNotFoundException("");
        }
    }

    @Override
    public boolean supports(Class<? extends Object> supportedClass) {
        if (supportedClass.getName().equalsIgnoreCase(
                "org.springframework.security.authentication.UsernamePasswordAuthenticationToken")) {
            return true;
        }

        return false;
    }

    public OXMUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Autowired
    public void setUserDetailsService(OXMUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(
            AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
