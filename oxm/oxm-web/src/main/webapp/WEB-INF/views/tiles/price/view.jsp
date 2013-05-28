<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<c:if test="${not empty adminview and adminview }">
	<div style="margin: 5px; width: 100%;">
		<oxmBreadcrambs:cpanel startingBreadcramb="true" />
		<oxmBreadcrambs:prices />
	</div>
</c:if>
<jsp:include page="/WEB-INF/views/tiles/price/searchfields.jsp"></jsp:include>

<div id="buttonStrip">
	<c:if test="${not empty adminview and adminview }">
		<div class="contextual" style="float: left;">
			<a id="lnkAddPrice1" class="uiButton" href="${baseUrl }/price/add/">Add</a>
			<a id="lnkEditPrice" class="uiButton" href="${baseUrl }/price/edit/">Edit</a>
			<a id="lnkDeletePrice" class="uiButton" href="${baseUrl }/price/delete/">Delete</a>
		</div>
	</c:if>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<oxmTags:name-of-item-on-page name="Price" />

	<table class="recordTable">
		<thead>
			<tr>
				<c:if test="${not empty adminview and adminview }">
					<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				</c:if>
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
					<c:forEach var="price" items="${prices }">
						<tr id="${price.id }">
							<c:if test="${not empty adminview and adminview }">
								<td><oxmTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${price.id }" /></td>
							</c:if>
							<td>${price.crop.name }</td>
							<td>${price.sellingPlace.name }</td>
							<td>${price.sellType.name }</td>
							<td>${price.quantity }(${price.unitOfMeasure.name })</td>
							<td>${price.price }</td>
							<td><fmt:formatDate value="${price.date}" pattern="dd/MM/yyyy" /></td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>