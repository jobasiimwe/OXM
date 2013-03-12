<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
	

<div style="margin: 5px; width: 100%;">
	<label class="uiLabel">You are here >> </label>
	<oxmBreadcrambs:cpanel />
	>
	<oxmBreadcrambs:producerorgs /> > producers
</div>


<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddProducer" class="uiButton"
			href="${baseUrl }/producerorg/producers/add/${pOrg.id}">Add</a> <a
			id="lnkEditProducer" class="uiButton"
			href="${baseUrl }/producerorg/producers/edit/${pOrg.id}/">Edit</a> <a
			id="lnkDeleteProducer" class="uiButton"
			href="${baseUrl }/producerorg/producers/delete/${pOrg.id}/">Delete</a> 
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
				<th>Name</th>
				<th>Gender</th>
				<th>DateOfBirth</th>
				<th>Sub county</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when
					test="${not empty pOrg.producers  && fn:length(pOrg.producers) > 0}">
					<c:forEach var="producer" items="${pOrg.producers }">
						<tr id="${producer.id }">
							<td><input type="checkbox" name="selectedPOrganization"
								value="${producer.id }" /></td>
							<td>${producer.name }</td>
							<td>${producer.gender.name }</td>
							<td><fmt:formatDate value="${producer.dateOfBirth}" pattern="dd/MM/yyyy"/></td>
							
							<td>${producer.subCounty.name }</td>
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