<%@page import="org.agric.oxm.server.security.util.OXMSecurityUtil"%>
<%@page import="org.agric.oxm.model.User"%>
<%@page import="org.studentsys.model.User"%>
<%@page import="org.studentsys.api.security.util.RanchSecurityUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" />
</title>
<link type="text/css" rel="stylesheet" href="static/css/custom-theme/jquery-ui-1.8.15.custom.css" />
<link type="text/css" rel="stylesheet" href="static/css/system.css" />
<link rel="icon" type="image/png" href="static/images/xx.png" />
<script type="text/javascript" src="static/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="static/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="static/js/jquery.getUrlParam.js"></script>
<script type="text/javascript" src="static/js/system.js"></script>

<tiles:insertAttribute name="scripts" ignore="true" />
</head>
<body>
	<%
		User user = null;		
		try{
			user = OXMSecurityUtil.getLoggedInUser();
			if(user != null){
				pageContext.setAttribute("loggedInUser", user);
			}
		}catch(Exception ex){
		}
	%>
	<div id="wrapper">
		<div class="statusbar">
			<div class="sbcredentials">
				<div class="logo">
					<c:set var="baseUrl"
						value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"/>
						<a href="${baseUrl}" title="Home"></a>
				</div>
				<div class="banner">Student Registration System (SRS)</div>
				<ol>
					<li><c:if test="${not empty loggedInUser }">
							<span>Logged in as:
									${loggedInUser.username }
							</span>
						</c:if></li>
					<li><a title="logout" href="ServiceLogout"> <span style="color: #FFF">logout</span>
					</a></li>
				</ol>
				<div style="clear: both;"></div>
			</div>
			<div class="sbmessagecontainer">
				<div id="errorMsg"
					<c:choose>
						<c:when test="${not empty errorMessage}">
							class="<c:out value="ui-state-error ui-corner-all"></c:out>"	
						</c:when>
						<c:otherwise>
							class="<c:out value="ui-state-error ui-corner-all hide"></c:out>"
						</c:otherwise>
					</c:choose> 
				>
						<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
						<strong>Alert:</strong>
						<span id="eMsg">
							<c:if test="${not empty errorMessage }">
								<c:out value="${errorMessage }"></c:out>
							</c:if>
						</span>
				</div>
				<div id="systemMsg" 
					<c:choose>
						<c:when test="${not empty systemMessage}">
							class="<c:out value="ui-state-highlight ui-corner-all"></c:out>"	
						</c:when>
						<c:otherwise>
							class="<c:out value="ui-state-highlight ui-corner-all hide"></c:out>"
						</c:otherwise>
					</c:choose>					
				>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
						<strong>:: </strong>
						<span id="sMsg">
							<c:if test="${not empty systemMessage }">
								<c:out value="${systemMessage }"></c:out>
							</c:if>
						</span>	
						<strong>:: </strong>
				</div>
			</div>
			<div></div>
		</div>
		<div id="loader">
			<span>Loading please wait</span>
		</div>
		<div id="top-header"></div>
		<div id="header" class="header">
			<tiles:insertAttribute name="header" ignore="true" />
		</div>
		<div id="main">
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td valign="top" style="width: 200px;"><tiles:insertAttribute
							name="sidebarleft" ignore="true" /></td>
					<td valign="top">
						<div id="content">
							<h2 id="content-header">
								<tiles:insertAttribute name="pageTitle"
									defaultValueType="string" defaultValue="Home" />
							</h2>
							<div style="clear: both">
								<tiles:insertAttribute name="body" />
							</div>
						</div></td>
					<td valign="top"><tiles:insertAttribute name="sidebarright"
							ignore="true" /></td>
				</tr>
			</table>
			<div style="clear: both"></div>
		</div>
		<div id="footer" class="footer">
			<tiles:insertAttribute name="footer" />
		</div>
		<div id="dialog"></div>
	</div>
</body>
</html>