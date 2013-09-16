<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel-porg name="porgcommittees" porgParam="${pOrg }" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons name="POrgCommittee" url="porg-committee" child1="Members" child1Url="porg-committee-member"
			parentId="${pOrg.id }" />

	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Committee" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No</th>
				<th>Committee</th>
				<th>No. of Members</th>
				<th>Producer Organisation</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty pOrg.committees  && fn:length(pOrg.committees) > 0}">
					<c:forEach var="committee" items="${pOrg.committees }" varStatus="status">
						<tr id="${committee.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${committee.id }" /></td>
							<td>${status.count }</td>
							<td>${committee.name }</td>
							<td>${fn:length(committee.members) }</td>
							<td>${committee.producerOrg.name }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>