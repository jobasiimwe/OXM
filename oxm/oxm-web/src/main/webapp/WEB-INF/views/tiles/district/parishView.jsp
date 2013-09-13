<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:district name="parishes" district="${subCounty.county.district }" county="${subCounty.county }"
		subCounty="${subCounty }"
	/>
</div>

<div id="buttonStrip">
	<div class="contextual">
		<sysTags:addeditdeletebuttons url="parish" name="Parish" parentId="${subCounty.id }" child1="Villages"
			child1Url="village"
		/>

	</div>
	<div style="clear: both"></div>
</div>
<div>
	<sysTags:name-of-item-on-page name="Parish" />
	<table class="recordTable list">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>No.</th>
				<th>Name</th>
				<th>Villages</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty parishes  && fn:length(parishes) > 0}">
				<c:forEach var="parish" items="${parishes }" varStatus="status">
					<tr id="${parish.id }">
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${parish.id }" /></td>
						<td>${status.count }</td>
						<td>${parish.name }</td>
						<td>${parish.villagesString }</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>