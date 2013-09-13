<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="porgproducers" producerOrg="${pOrg }" />
</div>


<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons url="porgproducers" name="Producer-Org-Member" parentId="${pOrg.id}" />
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Producer-Organisation-Member" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>Gender</th>
				<th>DateOfBirth</th>
				<th>Sub county</th>
				<th>Parish</th>
				<th>Village</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty pOrg.producers  && fn:length(pOrg.producers) > 0}">
					<c:forEach var="producer" items="${pOrg.producers }">
						<tr id="${producer.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage }" id="${producer.id }" /></td>
							<td>${producer.name }</td>
							<td>${producer.gender.name }</td>
							<td><fmt:formatDate value="${producer.dateOfBirth}" pattern="dd/MM/yyyy" /></td>
							<c:choose>
								<c:when test="${not empty producer.producerOrg }">
									<td>${producer.producerOrg.subCounty.name }</td>
									<td>${producer.producerOrg.parish.name }</td>
									<td>${producer.producerOrg.village.name }</td>
								</c:when>
								<c:otherwise>
									<td></td>
									<td></td>
									<td></td>
								</c:otherwise>
							</c:choose>

						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td><span style="color: red;">no producers found</span></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>