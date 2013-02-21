<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<div id="buttonStrip"></div>
<div>
<div>
    <div style="float: right;">
		<form method="post" action="controlpanel?action=search&item=user" style="display: inline-block">
			<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" value="${query }" /> <input
				type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
		</form>
	</div>
	<div style="clear: both"></div>
</div>

<div class="buttonStrip">
	<div style="float:left;" class="contextual">
		<a class="uiButton" href="${baseUrl }/user/add/:">New User</a>
	</div>
	<div style="float:right;">
		<%@ include file="/WEB-INF/views/navigation.jsp" %>
	</div>
	<div style="clear:both"></div>
</div>
	<table id="recordTable" class="recordTable list" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th></th>
				<th>Login</th>
				<th>First name</th>
				<th>Last name</th>
				<th>Status</th>
				<th>Administrator</th>
				<th></th>
			</tr>
		</thead>
		
		<c:choose>
			<c:when test="${not empty users  && fn:length(users) > 0}">
				<c:forEach var="u" items="${users }">
					<tr id="${u.id }">
						<td><input type="radio" id="${u.id }" /></td>
						<td>${u.username }</td>
						<%
							User usr = (User)pageContext.getAttribute("u");
						%>
						<td>${u.firstName }</td>
						<td>${u.lastName }</td>
						<td>${u.status }</td>
						<td><span <%= usr.hasAdministrativePrivileges()? "class=\"icon icon-tick\"": ""  %> style="display:inline-block; width: 16px; height: 16px;"></span></td>
						<td>
							<a id="btnDeleteUser" class="icon icon-delete" title="delete" href="${baseUrl }/user/delete/${u.id }/">Delete</a>
							<a class="icon icon-edit" title="edit" href="${baseUrl }/user/edit/${u.id }/:">Edit</a>
						</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</div>