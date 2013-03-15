<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmDistrictBreadcrambs:districts />
</div>

<div id="buttonStrip">
	<div class="contextual">
		<a id="lnkAddDistrict" class="uiButton"
			href="${baseUrl }/district/add/">Add</a> <a id="lnkEditDistrict"
			class="uiButton" href="${baseUrl }/district/edit/">Edit</a> <a
			id="lnkDeleteDistrict" class="uiButton"
			href="${baseUrl }/district/delete/">Delete</a> <a
			id="lnkDistrictSubCounties" class="uiButton"
			href="${baseUrl }/subcounty/view/">Sub Counties</a>
	</div>
	<div style="float: right">
		<form method="post" action="${baseUrl }/district/search/"
			style="display: inline-block">
			<input type="text" class="uiTextbox" id="txtSearch" size="30"
				name="query" /> <input type="submit" class="uiButton"
				id="btnSearchSubmit" value="Search" />
		</form>
	</div>
	<div style="clear: both"></div>
</div>

<div>
	<table class="recordTable list" width="100%" cellpadding="0"
		cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" />
				</th>
				<th>No.</th>
				<th>Name</th>
				<th>Sub-Counties</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty districts  && fn:length(districts) > 0}">
					<c:forEach var="district" items="${districts }" varStatus="status">
						<tr id="${district.id }">
							<td><input type="checkbox" name="selectedDistrict"
								value="${district.id }" /></td>
							<td>${status.count }</td>
							<td>${district.name }</td>
							<td>${district.subCountiesString }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</tbody>

	</table>
</div>