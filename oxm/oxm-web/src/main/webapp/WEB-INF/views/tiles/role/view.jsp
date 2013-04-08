<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>


<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:roles />
</div>

<div id="buttonStrip">
	<div class="contextual">
		<a id="lnkAddRole" class="uiButton" href="${baseUrl }/role/add">Add</a>
		<a id="lnkEditRole" class="uiButton" href="${baseUrl }/role/edit/">Edit</a>
		<a id="lnkDeleteRole" class="uiButton" href="${baseUrl }/role/delete/">Delete</a>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Role" />

	<table class="recordTable list" width="100%" cellpadding="0"
		cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" />
				</th>
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
						<td><oxmTags:rowcheckbox
								nameOfItemOnPage="${nameOfItemOnPage }" id="${role.id }" /></td>
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