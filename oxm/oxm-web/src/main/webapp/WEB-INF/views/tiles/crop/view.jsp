<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddCrop" class="uiButton" href="${baseUrl }/crop/add/">Add</a>
		<a id="lnkEditCrop" class="uiButton" href="${baseUrl }/crop/edit/">Edit</a>
		<a id="lnkDeleteCrop" class="uiButton" href="${baseUrl }/crop/delete/">Delete</a>
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
					id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>Inputs</th>
				<th>seedVariation</th>
				<th>ploughingMethod</th>
				<th>interCropingType</th>
				<th>unitOfMeasure</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty crops  && fn:length(crops) > 0}">
					<c:forEach var="crop" items="${crops }">
						<tr id="${crop.id }">
							<td><input type="checkbox" name="selectedConcepts"
								value="${crop.id }" /></td>
							<td>${crop.name }</td>
							<td>${crop.seedVariation.name }</td>
							<td>${crop.ploughingMethod.name }</td>
							<td>${crop.interCropingType.name }</td>
							<td>${crop.unitOfMeasure.name }</td>
							<td>${crop.input.name }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>