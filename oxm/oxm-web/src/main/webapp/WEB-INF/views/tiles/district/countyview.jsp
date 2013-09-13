<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:district name="counties" district="${district }" />
</div>

<div id="buttonStrip">
	<div class="contextual">

		<sysTags:addeditdeletebuttons url="county" name="County" parentId="${district.id }" child1="Sub-County" child1Url="subcounty"/>
			</div>
	<div style="clear: both"></div>
</div>

<div>

	<sysTags:name-of-item-on-page name="County" />

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
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${county.id }" /></td>
						<td>${status.count }</td>
						<td>${county.name }</td>
						<td>${county.subCountiesString }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>