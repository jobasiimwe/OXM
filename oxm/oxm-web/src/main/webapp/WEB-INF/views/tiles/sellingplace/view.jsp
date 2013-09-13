<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="sellingplaces" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons url="sellingplace" name="${nameOfItemOnPage }" />
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Selling-Place" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>District/Place</th>
				<th>Selling Types</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty sellingplaces  && fn:length(sellingplaces) > 0}">
					<c:forEach var="sellingplace" items="${sellingplaces }" varStatus="status">
						<tr id="${sellingplace.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage }" id="${sellingplace.id }" /></td>
							<td>${status.count }</td>
							<td>${sellingplace.name }</td>
							<td>${sellingplace.district.name }</td>
							<td>${sellingplace.sellingTypesString }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>