<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmDistrictBreadcrambs:districts />
</div>

<div id="buttonStrip">
	<div class="contextual">
		<a id="lnkAddDistrict" class="uiButton" href="${baseUrl }/district/add/">Add</a>
		<a id="lnkEditDistrict" class="uiButton" href="${baseUrl }/district/edit/">Edit</a>
		<a id="lnkDeleteDistrict" class="redText uiButton" href="${baseUrl }/district/delete/">Delete</a>
		<a id="lnkDistrictCounties" class="uiButton" href="${baseUrl }/county/view/">Counties</a>
		<a id="lnkImportDistricts" class="uiButton spacedElement" href="${baseUrl }/import/districts">Import
			Districts</a>
		<a id="lnkImportDistricts" class="uiButton" href="${baseUrl }/import/producerorgs">Import
			Producer Orgs.</a>
	</div>
	<div style="float: right; display: none;">
		<form method="post" action="${baseUrl }/district/search/" style="display: inline-block">
			<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" />
			<input type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
		</form>
	</div>
	<div style="clear: both"></div>
</div>
<div>
	<sysTags:name-of-item-on-page name="District" />
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
			<c:choose>
				<c:when test="${not empty districts  && fn:length(districts) > 0}">
					<c:forEach var="district" items="${districts }" varStatus="status">
						<tr id="${district.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${district.id }" />
							</td>
							<td>${status.count }</td>
							<td>${district.name }</td>
							<td>${district.countiesString }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>