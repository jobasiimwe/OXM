<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>
<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmProducerOrgBreadcrambs:producerorgs />
</div>
<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddPOrg" class="uiButton" href="${baseUrl }/porg/add/">Add</a>
		<a id="lnkEditPOrg" class="uiButton" href="${baseUrl }/porg/edit/">Edit</a>
		<a id="lnkDeletePOrg" class="uiButton" href="${baseUrl }/porg/delete/">Delete</a>
		<a id="lnkPOrgDetails" class="uiButton" href="${baseUrl }/porg/details/">Details</a>
		&emsp;&emsp;&emsp;
		<a id="lnkPOrgMembers" class="uiButton" href="${baseUrl }/porg/producers/">Members</a>
		<a id="lnkPOrgDocs" class="uiButton" href="${baseUrl }/porg/docs/">Documents</a>
		<a id="lnkPOrgStaff" class="uiButton" href="${baseUrl }/porg-staff/">Staff</a>
		<a id="lnkPOrgCommittees" class="uiButton" href="${baseUrl }/porg-committee/">Committees</a>
		<a id="lnkImportDistricts" class="uiButton spacedElement" href="${baseUrl }/import/producerorgs">Import
			Producer Orgs.</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>
	<oxmTags:name-of-item-on-page name="Producer-Organisation" />
	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>Sub-County</th>
				<th>Parish</th>
				<th>Village</th>
				<th>No. of Producers</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty pOrganizations  && fn:length(pOrganizations) > 0}">
					<c:forEach var="pOrg" items="${pOrganizations }">
						<tr id="${pOrg.id }">
							<td><oxmTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${pOrg.id }" />
							<td>${pOrg.name }</td>
							<td>${pOrg.subCounty.name }</td>
							<td>${pOrg.parish.name }</td>
							<td>${pOrg.village.name }</td>
							<td>${fn:length(pOrg.producers) }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>