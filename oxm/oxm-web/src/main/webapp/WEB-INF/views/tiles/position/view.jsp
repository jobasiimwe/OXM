<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="positions" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons name="Position" url="position" />
		<a id="lnkPositionHolders" class="uiButton hide" href="${baseUrl }/position/holders/">Holders</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Position" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Index</th>
				<th>Name</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty positions  && fn:length(positions) > 0}">
					<c:forEach var="position" items="${positions }">
						<tr id="${position.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${position.id }" /></td>
							<td>${position.index }</td>
							<td>${position.name }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>