<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<oxmBreadcrambs:cpanel name="prices" />

<jsp:include page="/WEB-INF/views/tiles/price/searchfields.jsp"></jsp:include>

<div id="buttonStrip">
	<c:if test="${not empty adminview and adminview }">
		<div class="contextual" style="float: left;">
			<sysTags:addeditdeletebuttons url="price" name="Price" />
		</div>
	</c:if>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Price" />

	<table class="recordTable">
		<thead>
			<tr>
				<c:if test="${not empty adminview and adminview }">
					<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				</c:if>
				<th>No.</th>
				<th>Item</th>
				<th>Selling Place</th>
				<th>Date</th>
				<th>Quantity/Unit</th>
				<th>Retail Price (UGX)</th>
				<th>Wholesale Price (UGX)</th>

			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty prices  && fn:length(prices) > 0}">
					<c:forEach var="price" items="${prices }" varStatus="status">
						<tr id="${price.id }">
							<c:if test="${not empty adminview and adminview }">
								<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${price.id }" /></td>
							</c:if>
							<td>${status.count }</td>
							<td>${price.product.name }</td>
							<td>${price.sellingPlace.name }</td>
							<td><fmt:formatDate value="${price.date}" pattern="dd-MMM-yyyy" /></td>
							<td><fmt:formatNumber value="${price.qty }" pattern="#0" />${price.unitOfMeasure.name }</td>
							<td><fmt:formatNumber value="${price.retail }" pattern="#0" /></td>
							<td><fmt:formatNumber value="${price.wholeSale }" pattern="#0" /></td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>