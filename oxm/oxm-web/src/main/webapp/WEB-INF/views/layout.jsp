<%@page import="org.agric.oxm.server.security.util.OXMSecurityUtil"%>
<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="baseUrl" scope="application" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />

<title><tiles:insertAttribute name="title" ignore="true" />
</title>
<link type="text/css" rel="stylesheet" href="${baseUrl }/static/css/custom-theme/jquery-ui-1.8.15.custom.css" />
<link type="text/css" rel="stylesheet" href="${baseUrl }/static/css/oxm.css" />
<link rel="icon" type="image/png" href="${baseUrl }/static/images/plant-ico.png" />
<script type="text/javascript" src="${baseUrl }/static/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="${baseUrl }/static/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${baseUrl }/static/js/jquery.getUrlParam.js"></script>
<script type="text/javascript" src="${baseUrl }/static/js/oxm.js"></script>
<script type="text/javascript" src="${baseUrl }/static/js/oxm-admin.js"></script>
<script type="text/javascript" src="${baseUrl }/static/js/scrolltable.js"></script>

<tiles:insertAttribute name="scripts" ignore="true" />
</head>
<body style="background: white;">

<input id="baseUrl" value=${baseUrl } type="hidden"/>

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
	<div class="wrapper">
		<div class="container-header">
			<div class="logo"></div>
			<div class="status-msg"><jsp:include
					page="/WEB-INF/views/tiles/system-message.jsp"></jsp:include></div>
			<div style="display: inline-block;float: right;">
				<div class="user-credentials"><ul>
					<li><span
						style="color: #fff; font-weight: bold; font-size: 100%;">Welcome:
							${loggedInUser.username } </span>
					</li>
					<li><a href="${baseUrl }/ServiceLogout">Logout</a>
				</ul>
				</div>
				<div style="clear: both;">
					<div class="home-dv">
						<a title="Home" href="${baseUrl}/">
							<span style="margin-top: 5px;">Home</span> 
						</a>
					</div>
						<%
	    try {
			if (user != null && user.hasAdministrativePrivileges()) {
		
	%>
					
					<div class="controlpanel-dv"><a title="Controlpanel" href="${baseUrl}/cpanel"><span>C-panel</span> </a></div>
					<%			}
	    } catch (Exception ex) {
	    }
					%>
				</div>
			</div>
		</div>
		<div class="content-dv">
			<div class="left-side-dv">
				<tiles:insertAttribute name="sidebarleft" ignore="true" />
			</div>
			<div class="right-side-dv">
			<!--
				--><div style="width: 100%; clear: both; float: left;"></div>
				<div style="clear: both;">
					<h2 id="content-header">
					<tiles:insertAttribute name="pageTitle" defaultValueType="string"
						defaultValue="Home" />
				</h2>
				</div>
				<div style="clear: both">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
	</div>
	<div style="clear: both;"></div>
		<div >
			<tiles:insertAttribute name="footer" />
		</div>
</body>
</html>