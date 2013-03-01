<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<div>
	<div style="margin: 5px; width: 100%;">
		<label class="uiLabel">Crops >> </label><a title="Back to Crops"
			href="${baseUrl }/crop/view/page/1">Back</a>
	</div>
	<oxmTags:conceptcategory conceptCategory="${crop.input }" />
	<oxmTags:conceptcategory conceptCategory="${crop.seedVariation }" />
	<oxmTags:conceptcategory conceptCategory="${crop.ploughingMethod }" />
	<oxmTags:conceptcategory conceptCategory="${crop.interCropingType }" />

	<div class="box">
		<table class="recordTable" width="100%" cellpadding="0"
			cellspacing="0">
			<caption>
				<b>Units of Measure for ${crop.name }</b>
			</caption>
			<thead>
				<tr>
					<th><input type="checkbox" name="cbxSelectAllItems"
						id="cbxSelectAllItems" /></th>
					<th>Name</th>
					<th>Description</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when
						test="${not empty crop.unitsOfMeasure  && fn:length(crop.unitsOfMeasure) > 0}">
						<c:forEach var="concept" items="${crop.unitsOfMeasure }">
							<tr id="${concept.id }">
								<td><input type="checkbox" name="selectedConcepts"
									value="${concept.id }" /></td>
								<td>${concept.name }</td>
								<td>${concept.description }</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="3"><span style="color: red;">none found</span>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</div>