<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="breadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%; display: none;">
	<breadcrambs:prelogin name="crops" />
</div>

<h3>We track the following Crops:-</h3>
<table class="sysTable">
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${not empty crops  && fn:length(crops) > 0}">
				<c:forEach var="crop" items="${crops }" varStatus="status">
					<tr id="${crop.id }">
						<td>${status.count }</td>
						<td>${crop.name }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
					<td colspan="6" class="redText">Ooops no crops found</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
