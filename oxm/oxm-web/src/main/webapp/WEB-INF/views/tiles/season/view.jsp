<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:seasons />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddSeason" class="uiButton" href="${baseUrl }/season/add/">Add</a>
		<a id="lnkEditSeason" class="uiButton" href="${baseUrl }/season/edit/">Edit</a>
		<a id="lnkDeleteSeason" class="uiButton"
			href="${baseUrl }/season/delete/">Delete</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Season" />

	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Start Date</th>
				<th>End Date</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty seasons  && fn:length(seasons) > 0}">
					<c:forEach var="season" items="${seasons }" varStatus="status">
						<tr id="${season.id }">
							<td><oxmTags:rowcheckbox
									nameOfItemOnPage="${nameOfItemOnPage}" id="${season.id }" />
							</td>
							<td>${status.count }</td>
							<td>${season.name }</td>
							<td><fmt:formatDate value="${season.startDate}"
									pattern="dd/MM/yyyy" />
							</td>
							<td><fmt:formatDate value="${season.endDate}"
									pattern="dd/MM/yyyy" />
							</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>