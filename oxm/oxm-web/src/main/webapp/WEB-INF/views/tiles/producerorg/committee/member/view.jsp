<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel-porg name="porgcommittee-members" porgParam="${pOrg }" committeeParam="${committee }" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons name="POrgCommitteMember" url="porg-committee-member" parentId="${committee.id }" />
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Committee-Member" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No</th>
				<th>Position</th>
				<th>Name</th>
				<th>From (Date)</th>
				<th>To (Date)</th>
				<th>User-Roles</th>
				<th>Address</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty committee.members && fn:length(committee.members) > 0}">
					<c:forEach var="member" items="${committee.members }" varStatus="status">
						<tr id="${member.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${member.id }" /></td>
							<td>${status.count }</td>
							<td>${member.position.name }</td>
							<td>${member.user.name }</td>
							<td><sysTags:print-date date="${member.fromDate }" /></td>
							<td><c:if test="${not empty member.toDate }">
									<sysTags:print-date date="${member.fromDate }" />
								</c:if></td>
							<td>${member.user.rolesString }</td>
							<td>${member.user.address }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>