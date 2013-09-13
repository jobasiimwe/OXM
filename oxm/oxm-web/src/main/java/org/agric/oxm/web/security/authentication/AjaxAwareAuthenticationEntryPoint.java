package org.agric.oxm.web.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AjaxAwareAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
	if (request.getHeader("X-AjaxRequest") != null
				&& request.getHeader("X-AjaxRequest").equals("1")) {
	    ((HttpServletResponse) response).sendError(601, "");
	} else {
	    super.commence(request, response, authException);
	}
    }
}
