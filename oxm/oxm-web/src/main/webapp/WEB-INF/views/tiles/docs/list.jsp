

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>

<table class="recordTable">
	<thead>
		<tr>
			<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
			<th>Title</th>
			<th>Type</th>
			<th>Description</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty documents }">
			<c:forEach var="document" items="${documents }">
				<tr>
					<td><sysTags:rowcheckbox nameOfItemOnPage="Document" id="${document.id }" />
					</td>
					<td>${document.name }</td>
					<td>${document.documentType.name }</td>
					<td>${document.otherInfo }</td>
					<td><a title="Download" id="lnkDocumentDownload" class="uiButton"
							href="${baseUrl }/download/document/${document.id }"
						>Download</a></td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>