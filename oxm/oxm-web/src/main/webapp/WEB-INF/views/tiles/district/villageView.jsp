<%@ page language="java" contentType="text/html; charset=UTF-8"
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
	<oxmDistrictBreadcrambs:districts />
	<oxmDistrictBreadcrambs:subcounty
		district="${parish.subCounty.district }" />
	<oxmDistrictBreadcrambs:parish subCounty="${parish.subCounty }" />
	<oxmDistrictBreadcrambs:village parish="${parish }" />
	Villages
</div>
<div id="buttonStrip">
	<div class="contextual">
		<a id="lnkAddVillage" class="uiButton"
			href="${baseUrl }/village/add/${parish.id }">Add</a> <a
			id="lnkEditVillage" class="uiButton" href="${baseUrl }/village/edit/">Edit</a>
		<a id="lnkDeleteVillage" class="uiButton"
			href="${baseUrl }/village/delete/${parish.id }/">Delete</a>
	</div>

	<div style="clear: both"></div>
</div>

<div>

	<oxmTags:name-of-item-on-page name="Village" />

	<table class="recordTable list" width="100%" cellpadding="0"
		cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Parish</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty villages  && fn:length(villages) > 0}">
				<c:forEach var="village" items="${villages }" varStatus="status">
					<tr id="${village.id }">
						<td><oxmTags:rowcheckbox
								nameOfItemOnPage="${nameOfItemOnPage}" id="${village.id }" /></td>
						<td>${status.count }</td>
						<td>${village.name }</td>
						<td>${village.parish.name }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>