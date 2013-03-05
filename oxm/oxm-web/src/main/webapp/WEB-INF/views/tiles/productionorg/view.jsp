<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddProductionOrg" class="uiButton" href="${baseUrl }/pOrganization/add/">Add</a>
		<a id="lnkEditProductionOrg" class="uiButton"
			href="${baseUrl }/pOrganization/edit/">Edit</a> 
		<a id="lnkDeleteProductionOrg" class="uiButton" href="${baseUrl }/pOrganization/delete/">Delete</a>
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
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty pOrganizations  && fn:length(pOrganizations) > 0}">
					<c:forEach var="pOrg" items="${pOrganizations }">
						<tr id="${pOrg.id }">
							<td><input type="checkbox" name="selectedPOrganization"
								value="${pOrg.id }" />
							</td>
							<td>${pOrg.name }</td>
							<td>
								<a class="icon icon-list" title="view Productions"
								href="${baseUrl }/production/view/${pOrg.id }">View Productions</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>