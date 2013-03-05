<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddfInstitution" class="uiButton" href="${baseUrl }/fInstitution/add/">Add</a>
		<a id="lnkEditfInstitution" class="uiButton" href="${baseUrl }/fInstitution/edit/">Edit</a> 
		<a id="lnkDeletefInstitution"
			class="uiButton" href="${baseUrl }/fInstitution/delete/">Delete</a>
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
				<th>Address</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty fInstitutions  && fn:length(fInstitutions) > 0}">
					<c:forEach var="fInstitution" items="${fInstitutions }">
						<tr id="${fInstitution.id }">
							<td><input type="checkbox" name="selectedfInstitution"
								value="${fInstitution.id }" />
							</td>
							<td>${fInstitution.name }</td>
							<td>${fInstitution.address }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>