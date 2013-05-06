<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmProducerOrgBreadcrambs:producerorgs />
	<oxmProducerOrgBreadcrambs:details producerOrg="${pOrg }" />
	<oxmProducerOrgBreadcrambs:staffmembers producerOrg="${pOrg }" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddPOrgStaff" class="uiButton"
			href="${baseUrl }/porg-staff/add/${pOrg.id }">Add</a> <a
			id="lnkEditPOrgStaff" class="uiButton"
			href="${baseUrl }/porg-staff/edit/">Edit</a> <a
			id="lnkDeletePOrgStaff" class="uiButton"
			href="${baseUrl }/porg-staff/delete/${pOrg.id }/">Delete</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Staff-Member" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" /></th>
				<th>No</th>
				<th>Position</th>
				<th>Name</th>
				<th>From</th>
				<th>To</th>
				<th>User-Roles</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when
					test="${not empty pOrg.staffMembers && fn:length(pOrg.staffMembers) > 0}">
					<c:forEach var="member" items="${pOrg.staffMembers }"
						varStatus="status">
						<tr id="${member.id }">
							<td><oxmTags:rowcheckbox
									nameOfItemOnPage="${nameOfItemOnPage}" id="${member.id }" />
							</td>
							<td>${status.count }</td>
							<td>${member.position.name }</td>
							<td>${member.user.name }</td>
							<td>${member.appointmentDate }</td>
							<td>${member.dateLeft }</td>
							<td>${member.user.rolesString }</td>
							<td>${member.user.address }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>