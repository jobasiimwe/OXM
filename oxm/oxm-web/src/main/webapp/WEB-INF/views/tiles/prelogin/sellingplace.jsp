<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="breadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%; display: none;">
	<breadcrambs:prelogin name="sellingplaces" />
</div>

<h3>We track prices in the following markets:-</h3>

<table class="sysTable">
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>District/Place</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty sellingplaces  && fn:length(sellingplaces) > 0}">
				<c:forEach var="sellingplace" items="${sellingplaces }" varStatus="status">
					<tr id="${sellingplace.id }">
						<td>${status.count }</td>
						<td>${sellingplace.name }</td>
						<td><c:if test="${not empty sellingplace.district}"> 
								${sellingplace.district.name }
							</c:if></td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="3" class="redText">Ooops no markets found</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
