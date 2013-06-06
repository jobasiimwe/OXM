<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmDistrictBreadcrambs:districts />
	<oxmDistrictBreadcrambs:subcounty district="${district }" />
	Sub Counties
</div>

<div id="buttonStrip">
	<div class="contextual">
		<a id="lnkAddCounty" class="uiButton" href="${baseUrl }/county/add/${district.id }">Add</a>
		<a id="lnkEditCounty" class="uiButton" href="${baseUrl }/county/edit/">Edit</a>
		<a id="lnkDeleteCounty" class="uiButton" href="${baseUrl }/county/delete/${district.id }/">Delete</a>
		<a id="lnkCountySubCounties" class="uiButton" href="${baseUrl }/">Sub-County</a>
	</div>
	<div style="clear: both"></div>
</div>

<div>

	<oxmTags:name-of-item-on-page name="County" />

	<table class="recordTable list">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Sub-Counties</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty counties  && fn:length(counties) > 0}">
				<c:forEach var="county" items="${counties }" varStatus="status">
					<tr id="${county.id }">
						<td><oxmTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${county.id }" /></td>
						<td>${status.count }</td>
						<td>${county.name }</td>
						<td>${county.subCountiesString }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>