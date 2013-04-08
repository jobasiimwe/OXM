<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:sellingplaces />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddSellingPlace" class="uiButton"
			href="${baseUrl }/sellingplace/add/">Add</a> <a
			id="lnkEditSellingPlace" class="uiButton"
			href="${baseUrl }/sellingplace/edit/">Edit</a> <a
			id="lnkDeleteSellingPlace" class="uiButton"
			href="${baseUrl }/sellingplace/delete/">Delete</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Selling-Place" />

	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Selling Types</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when
					test="${not empty sellingplaces  && fn:length(sellingplaces) > 0}">
					<c:forEach var="sellingplace" items="${sellingplaces }"
						varStatus="status">
						<tr id="${sellingplace.id }">
							<td><oxmTags:rowcheckbox
									nameOfItemOnPage="${nameOfItemOnPage }"
									id="${sellingplace.id }" /></td>
							<td>${status.count }</td>
							<td>${sellingplace.name }</td>
							<td>${sellingplace.sellingTypesString }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>