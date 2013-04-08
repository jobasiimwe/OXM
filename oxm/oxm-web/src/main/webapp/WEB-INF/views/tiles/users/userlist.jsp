
<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>


<div>
	<oxmTags:name-of-item-on-page name="User" />

	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" />
				</th>
				<th>Name</th>
				<th>Gender</th>
				<th>Roles</th>
				<th>Address</th>
				<th>Status</th>
				<th>User-name</th>
				<th>Administrator</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty users  && fn:length(users) > 0}">
				<c:forEach var="u" items="${users }">
					<tr id="${u.id }">
						<td><oxmTags:rowcheckbox
								nameOfItemOnPage="${nameOfItemOnPage}" id="${u.id }" /></td>
						<%
						User usr = (User) pageContext.getAttribute("u");
					%>
						<td>${u.name }</td>
						<td>${u.gender }</td>
						<td>${u.rolesString }</td>
						<td>${u.addressString }</td>
						<td>${u.status }</td>
						<td>${u.username }</td>
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