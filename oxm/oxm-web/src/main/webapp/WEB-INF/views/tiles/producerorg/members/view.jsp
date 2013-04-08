<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmProducerOrgBreadcrambs:producerorgs />
	<oxmProducerOrgBreadcrambs:details producerOrg="${pOrg }" />
	<oxmProducerOrgBreadcrambs:producers producerOrg="${pOrg }" />
</div>


<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddMember" class="uiButton"
			href="${baseUrl }/porg/producers/add/${pOrg.id}">Add</a> <a
			id="lnkEditPOrgMember" class="uiButton"
			href="${baseUrl }/porg/producers/edit/${pOrg.id}/">Edit</a> <a
			id="lnkDeleteMember" class="uiButton"
			href="${baseUrl }/porg/producers/delete/${pOrg.id}/">Delete</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Producer-Organisation-Member" />

	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" />
				</th>
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
							<td><oxmTags:rowcheckbox
									nameOfItemOnPage="${nameOfItemOnPage }" id="${producer.id }" />
							</td>
							<td>${producer.name }</td>
							<td>${producer.gender.name }</td>
							<td><fmt:formatDate value="${producer.dateOfBirth}"
									pattern="dd/MM/yyyy" />
							</td>

							<td>${producer.subCounty.name }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td><span style="color: red;">no producers found</span>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>