package org.agric.oxm.web.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.agric.oxm.server.security.OXMUserDetails;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class OXMAuthenticationSuccessHandler extends
		SavedRequestAwareAuthenticationSuccessHandler {

    private String administratorTargetUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {

	if (authentication.getPrincipal() instanceof OXMUserDetails) {
	    if (((OXMUserDetails) authentication.getPrincipal()).getSystemUser()
					.hasAdministrativePrivileges()) {

		if (StringUtils.isNotBlank(getAdministratorTargetUrl())
			&& StringUtils.isNotEmpty(getAdministratorTargetUrl())) {

		    super.setDefaultTargetUrl(getAdministratorTargetUrl());
		    super.setAlwaysUseDefaultTargetUrl(true);
		}
	    } else {
		super.setAlwaysUseDefaultTargetUrl(false);
		super.setDefaultTargetUrl("/");
	    }
	}
	super.onAuthenticationSuccess(request, response, authentication);
    }

    public String getAdministratorTargetUrl() {
	return administratorTargetUrl;
    }

    public void setAdministratorTargetUrl(String administratorTargetUrl) {
	this.administratorTargetUrl = administratorTargetUrl;
    }

}
