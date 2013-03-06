<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
	

<div style="margin: 5px; width: 100%;">
	<label class="uiLabel">You are here >> </label>
	<oxmBreadcrambs:cpanel />
	>
	<oxmBreadcrambs:positionlist />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="btnAddPosition" class="uiButton" href="${baseUrl }/position/add/">Add</a>
		<a id="btnEditPosition" class="uiButton" href="${baseUrl }/position/edit/">Edit</a>
		<a id="btnDeletePosition" class="uiButton" href="${baseUrl }/position/delete/">Delete</a>
		&emsp;<a id="btnPositionHolders" class="uiButton"
			href="${baseUrl }/position/holders/">Holders</a>
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
				<th>Index</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty positions  && fn:length(positions) > 0}">
					<c:forEach var="position" items="${positions }">
						<tr id="${position.id }">
							<td><input type="checkbox" name="selectedPosition"
								value="${position.id }" /></td>
							<td>${position.index }</td>
							<td>${position.name }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>