<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddSellingPlace" class="uiButton" href="${baseUrl }/sellingplace/add/">Add</a>
		<a id="lnkEditSellingPlace" class="uiButton"
			href="${baseUrl }/sellingplace/edit/">Edit</a> 
		<a id="lnkDeleteSellingPlace"
			class="uiButton" href="${baseUrl }/sellingplace/delete/">Delete</a>
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
					id="cbxSelectAllItems" />
				</th>
				<th>Name</th>
				<th>Selling Type</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty sellingplaces  && fn:length(sellingplaces) > 0}">
					<c:forEach var="sellingplace" items="${sellingplaces }">
						<tr id="${sellingplace.id }">
							<td><input type="checkbox" name="selectedSellingPlace"
								value="${sellingplace.id }" />
							</td>
							<td>${sellingplace.name }</td>
							<td>${sellingplace.type.name }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>