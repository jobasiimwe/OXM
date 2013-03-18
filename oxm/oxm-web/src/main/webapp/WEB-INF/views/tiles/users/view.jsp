<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:users />
</div>

<div id="buttonStrip"></div>
<div>
	<div>
		<div style="float: right;">
			<form method="post" action="controlpanel?action=search&item=user"
				style="display: inline-block">
				<input type="text" class="uiTextbox" id="txtSearch" size="30"
					name="query" value="${query }" /> <input type="submit"
					class="uiButton" id="btnSearchSubmit" value="Search" />
			</form>
		</div>
		<div style="clear: both"></div>
	</div>

	<div class="buttonStrip">
		<div style="float: left;" class="contextual">
			<a id="lnkAddUser" class="uiButton" href="${baseUrl }/user/add/:">Add</a>
			<a id="lnkEditUser" class="uiButton" href="${baseUrl }/user/edit/:/">Edit</a>
			<a id="lnkDeleteUser" class="uiButton"
				href="${baseUrl }/user/delete/">Delete</a>
		</div>
		<div style="float: right;">
			<%@ include file="/WEB-INF/views/navigation.jsp"%>
		</div>
		<div style="clear: both"></div>
	</div>
	<table class="recordTable" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th></th>
				<th>Login</th>
				<th>Name</th>
				<th>Gender</th>
				<th>Roles</th>
				<th>Address</th>
				<th>Status</th>
				<th>Administrator</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty users  && fn:length(users) > 0}">
				<c:forEach var="u" items="${users }">
					<tr id="${u.id }">
						<td><input type="checkbox" name="selectedUser"
							value="${u.id }" /></td>
						<td>${u.username }</td>
						<%
							User usr = (User) pageContext.getAttribute("u");
						%>
						<td>${u.name }</td>
						<td>${u.gender }</td>
						<td></td>
						<td></td>
						<td>${u.status }</td>
						<td><span
							<%=usr.hasAdministrativePrivileges() ? "class=\"icon icon-tick\""
							: ""%>
							style="display: inline-block; width: 16px; height: 16px;"></span>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>