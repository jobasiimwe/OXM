<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="porgs" />
</div>

<jsp:include page="/WEB-INF/views/tiles/producerorg/searchfields.jsp"></jsp:include>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons url="porg" name="POrg" child1="Members" child1Url="porgproducers" child2="Documents"
			child2Url="porgdocs" child3="Committees" child3Url="porg-committee" child4="Staff" child4Url="porg-staff"
		/>

		<a id="lnkPOrgDetails" style="display: none;" class="uiButton" href="${baseUrl }/porg/details/">Details</a>
		&emsp;&emsp;&emsp;
		<a id="lnkImportDistricts" class="uiButton spacedElement" href="${baseUrl }/import/producerorgs">Import Producer
			Orgs.</a>
		<a id="lnkImportDistricts" class="uiButton" href="${baseUrl }/import/producers">Import Producers</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>
	<sysTags:name-of-item-on-page name="Producer-Organisation" />
	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>District</th>
				<th>County</th>
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
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${pOrg.id }" />
							<td>${pOrg.name }</td>
							<td>${pOrg.subCounty.county.district.name }</td>
							<td>${pOrg.subCounty.county.name }</td>
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