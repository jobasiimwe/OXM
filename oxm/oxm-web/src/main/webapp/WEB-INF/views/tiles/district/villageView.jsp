<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:district name="villages" district="${parish.subCounty.county.district }"
		county="${parish.subCounty.county }" subCounty="${parish.subCounty }" parish="${parish }"
	/>
</div>

<div id="buttonStrip">
	<div class="contextual">
		<sysTags:addeditdeletebuttons url="village" name="Village" parentId="${parish.id }" />
	</div>

	<div style="clear: both"></div>
</div>

<div>

	<sysTags:name-of-item-on-page name="Village" />

	<table class="recordTable list" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Parish</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty villages  && fn:length(villages) > 0}">
				<c:forEach var="village" items="${villages }" varStatus="status">
					<tr id="${village.id }">
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${village.id }" /></td>
						<td>${status.count }</td>
						<td>${village.name }</td>
						<td>${village.parish.name }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>