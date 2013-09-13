<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="crops" />
</div>

<sysTags:name-of-item-on-page name="Crop" />

<div id="buttonStrip" class="group buttonStrip">
	<sysTags:addeditdeletebuttons url="crop" name="${nameOfItemOnPage}" />

	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both;"></div>
</div>

<div>

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>Inputs</th>
				<th>seed Varieties</th>
				<th>ploughing Methods</th>
				<th>inter-Croping Types</th>
				<th>units Of Measure</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty crops  && fn:length(crops) > 0}">
					<c:forEach var="crop" items="${crops }">
						<tr id="${crop.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${crop.id }" /></td>
							<td>${crop.name }</td>
							<td>${crop.getInputNames() }</td>
							<td>${crop.getSdvNames() }</td>
							<td>${crop.getPmNames() }</td>
							<td>${crop.getIctNames() }</td>
							<td>${crop.getUomNames() }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>