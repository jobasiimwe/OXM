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
	<oxmProducerOrgBreadcrambs:committees producerOrg="${pOrg }" />
	<oxmProducerOrgBreadcrambs:committeemembers committee="${committee }" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddPOrgCommitteMember" class="uiButton"
			href="${baseUrl }/porg-committee/member/add/${committee.id }">Add</a> <a
			id="lnkEditPOrgCommitteeMember" class="uiButton"
			href="${baseUrl }/porg-committee/member/edit/">Edit</a> <a
			id="lnkDeletePOrgCommitteeMember" class="uiButton"
			href="${baseUrl }/porg-committee/member/delete/${committee.id }/">Delete</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Committee-Member" />

	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" /></th>
				<th>No</th>
				<th>Position</th>
				<th>Name</th>
				<th>User-Roles</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when
					test="${not empty committee.members && fn:length(committee.members) > 0}">
					<c:forEach var="member" items="${committee.members }"
						varStatus="status">
						<tr id="${member.id }">
							<td><oxmTags:rowcheckbox
									nameOfItemOnPage="${nameOfItemOnPage}" id="${member.id }" />
							</td>
							<td>${status.count }</td>
							<td>${member.position.name }</td>
							<td>${member.positionHolder.name }</td>
							<td>${member.positionHolder.rolesString }</td>
							<td>${member.positionHolder.address }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>