<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmDistrictBreadcrambs:districts />
	<oxmDistrictBreadcrambs:counties district="${subCounty.county.district }" />
	<oxmDistrictBreadcrambs:subcounties county="${subCounty.county }" />
	<oxmDistrictBreadcrambs:parishes subCounty="${subCounty }" />
	form
</div>

<div id="buttonStrip">
	<div class="contextual">
		<a id="lnkAddParish" class="uiButton" href="${baseUrl }/parish/add/${subCounty.id }">Add</a>
		<a id="lnkEditParish" class="uiButton" href="${baseUrl }/parish/edit/">Edit</a>
		<a id="lnkDeleteParish" class="uiButton" href="${baseUrl }/parish/add/${subCounty.id }/">Delete</a>
		<a id="lnkParishVillages" class="uiButton" href="${baseUrl }/village/view/">Villages</a>
	</div>
	<div style="clear: both"></div>
</div>
<div>
	<sysTags:name-of-item-on-page name="Parish" />
	<table class="recordTable list" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Villages</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty parishes  && fn:length(parishes) > 0}">
				<c:forEach var="parish" items="${parishes }" varStatus="status">
					<tr id="${parish.id }">
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${parish.id }" /></td>
						<td>${status.count }</td>
						<td>${parish.name }</td>
						<td>${parish.villagesString }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>