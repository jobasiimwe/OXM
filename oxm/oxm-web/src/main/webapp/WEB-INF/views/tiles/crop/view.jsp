<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="btnAddCrop" class="uiButton" href="${baseUrl }/crop/add/">Add</a>
		<a id="btnEditCrop" class="uiButton" href="${baseUrl }/crop/edit/">Edit</a>
		<a id="btnDeleteCrop" class="uiButton" href="${baseUrl }/crop/delete/">Delete</a>
		&emsp;<a id="btnCropDetails" class="uiButton"
			href="${baseUrl }/crop/details/view/">Details</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>
	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" />
				</th>
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
							<td><input type="checkbox" name="selectedCrop"
								value="${crop.id }" />
							</td>
							<td>${crop.name }</td>
							<td>${fn:length(crop.input.concepts) }</td>
							<td>${fn:length(crop.seedVariation.concepts) }</td>
							<td>${fn:length(crop.ploughingMethod.concepts) }</td>
							<td>${fn:length(crop.interCropingType.concepts) }</td>
							<td>${fn:length(crop.unitsOfMeasure)}</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>