<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:financialinstitutions />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddfInstitution" class="uiButton" href="${baseUrl }/finstitution/add/">Add</a>
		<a id="lnkEditfInstitution" class="uiButton" href="${baseUrl }/finstitution/edit/">Edit</a>
		<a id="lnkDeletefInstitution" class="uiButton" href="${baseUrl }/finstitution/delete/">Delete</a>
		<a id="lnkfInstitutionDocuments" style="margin-left: 20px" class="uiButton"
			href="${baseUrl }/finstitution/docs/view/"
		>Documents</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Financial-Institution" />

	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Address</th>
				<th>Available-Documents</th>
			</tr>
		</thead>

		<jsp:include page="/WEB-INF/views/tiles/finstitution/tablerows.jsp"></jsp:include>

	</table>
</div>