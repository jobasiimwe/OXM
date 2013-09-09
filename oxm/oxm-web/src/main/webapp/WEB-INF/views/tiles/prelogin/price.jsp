<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmPreloginBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/prelogin"%>

<div style="margin: 5px; width: 100%; display: none;">
	<oxmPreloginBreadcrambs:loginpage startingBreadcramb="true" />
	<oxmPreloginBreadcrambs:prices />
</div>

<h3>Latest Prices:-</h3>
<table class="sysTable">
	<thead>
		<tr>
			<th>No.</th>
			<th>Crop</th>
			<th>Selling Place</th>
			<th>Type</th>
			<th>Quantity/Unit</th>
			<th>Price</th>
			<th>Date</th>

		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty prices  && fn:length(prices) > 0}">
				<c:forEach var="price" items="${prices }" varStatus="status">
					<tr id="${price.id }">
						<td>${status.count }</td>
						<td>${price.product.name }</td>
						<td>${price.sellingPlace.name }</td>
						<td>${price.sellType.name }</td>
						<td>${price.quantity }(${price.unitOfMeasure.name })</td>
						<td><fmt:formatNumber value="${price.price }" pattern="#0" /></td>
						<td><fmt:formatDate value="${price.date}" pattern="dd/MM/yyyy" /></td>
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
