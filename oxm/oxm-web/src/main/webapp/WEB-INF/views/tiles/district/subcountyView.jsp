<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:district name="subcounties" district="${county.district }" county="${county }" />
</div>

<div id="buttonStrip">
	<div class="contextual">
		<sysTags:addeditdeletebuttons url="subcounty" name="Sub-County" parentId="${county.id }" child1="Parishes"
			child1Url="parish"
		/>
	</div>
	<div style="clear: both"></div>
</div>

<div>

	<sysTags:name-of-item-on-page name="SubCounty" />

	<table class="recordTable list">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Parishes</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty subcounties  && fn:length(subcounties) > 0}">
				<c:forEach var="subcounty" items="${subcounties }" varStatus="status">
					<tr id="${subcounty.id }">
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${subcounty.id }" /></td>
						<td>${status.count }</td>
						<td>${subcounty.name }</td>
						<td>${subcounty.parishesString }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>