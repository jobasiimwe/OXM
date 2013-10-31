<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>


<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<oxmBreadcrambs:cpanel name="roles" />

<div id="buttonStrip">
	<sysTags:addeditdeletebuttons name="ROLE" url="role" />
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Role" />

	<table class="recordTable list">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Description</th>
				<th>Permissions</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty roles  && fn:length(roles) > 0}">
				<c:forEach var="role" items="${roles }" varStatus="status">
					<tr>
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage }" id="${role.id }" /></td>
						<td>${status.count }</td>
						<td>${role.name }</td>
						<td>${role.description }</td>
						<td>${role.permissionsString }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>