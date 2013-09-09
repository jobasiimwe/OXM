
<%@page import="org.agric.oxm.server.security.util.OXMSecurityUtil"%>
<%@page import="org.agric.oxm.model.User"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
	User user = null;
	try {
		user = OXMSecurityUtil.getLoggedInUser();
		if (user != null) {
			pageContext.setAttribute("loggedInUser", user);
			session.setAttribute("loggedInUser", user);
		}
	} catch (Exception ex) {
	}
%>

<div class="container-header">
	<!-- <div class="header-logo logo"></div> -->
	<div class="header-logo">
		<div class="text-logo"></div>
	</div>

	<div class="status-msg"><jsp:include page="/WEB-INF/views/tiles/system-message.jsp"></jsp:include></div>
	<div style="display: inline-block; float: right;">
		<div class="user-credentials">
			<ul>
				<li>
					<span style="color: #fff; font-weight: bold; font-size: 100%;">Welcome: ${loggedInUser.username } </span>
				</li>
				<li>
					<a href="${baseUrl }/ServiceLogout">Logout</a>
			</ul>
		</div>
		<div style="clear: both;">
			<div class="header-link-dv">
				<a title="Home" href="${baseUrl}/">
					<span class="home-icon">Home</span>
				</a>
			</div>
			<%
				try {
					if (user != null && user.hasAdministrativePrivileges()) {
			%>

			<div class="header-link-dv">
				<a title="Controlpanel" href="${baseUrl}/cpanel">
					<span class="controlpanel-icon">C-panel</span>
				</a>
			</div>
			<%
				}
				} catch (Exception ex) {
				}
			%>
		</div>
	</div>
</div>