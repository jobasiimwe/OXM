<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="products" />
</div>

<sysTags:name-of-item-on-page name="Product" />

<div id="buttonStrip" class="group buttonStrip">
	<sysTags:addeditdeletebuttons url="product" name="${nameOfItemOnPage}" />

	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both;"></div>
</div>

<div>


	<table class="recordTable">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>Description</th>
				<th>Crop</th>
				<th>Units Of Measure</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty products  && fn:length(products) > 0}">
					<c:forEach var="product" items="${products }" varStatus="status">
						<tr id="${product.id }">
							<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${product.id }" /> ${status.count }</td>
							<td>${product.name }</td>
							<td>${product.description }</td>
							<td><c:if test="${not empty product.crop }">${product.crop.name }</c:if></td>
							<td>${product.getUomNames() }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" class="redText">Ooops no products found</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>