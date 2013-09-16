
<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<div>
	<sysTags:name-of-item-on-page name="User" />
	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" />No.</th>
				<th>Name</th>
				<th>Gender</th>
				<th>Roles</th>
				<th>Address</th>
				<th>Status</th>
				<th>User-name</th>
				<th>Producer-Group</th>
				<th>House Hold</th>
				<th>Admin</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty users  && fn:length(users) > 0}">
				<c:forEach var="u" items="${users }" varStatus="status">
					<tr id="${u.id }">
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${u.id }" />${status.count }</td>
						<%
							User usr = (User) pageContext.getAttribute("u");
						%>
						<td>${u.name }</td>
						<td>${u.gender }</td>
						<td>${u.rolesString }</td>
						<td title="${u.addressString }"><c:if test="${not empty u.producerOrg}">
						${u.producerOrg.village.name }</c:if></td>
						<td>${u.status }</td>
						<td>${u.username }</td>
						<td><c:if test="${ not empty u.producerOrg}">${u.producerOrg.name}</c:if></td>
						<td><c:if test="${ not empty u.houseHoldCategory }">${u.houseHoldCategory}</c:if></td>
						<td><span <%=usr.hasAdministrativePrivileges() ? "class=\"icon icon-tick\""
							: ""%>
							style="display: inline-block; width: 16px; height: 16px;"></span></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>