<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="breadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%; display: none;">
	<breadcrambs:prelogin name="prices" />
</div>

<jsp:include page="/WEB-INF/views/tiles/prelogin/pricesearchfields.jsp"></jsp:include>
<div id="buttonStrip">
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<h3>Market Prices!!!</h3>
<table class="recordTable">
	<thead>
		<tr>
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
					<tr id="${price.id }" style="height: 25px;">
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
			<c:otherwise>
				<tr>
					<td colspan="6" class="redText">Ooops no prices found</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>

<p>For a better view of prices with an option to filter, please login</p>
