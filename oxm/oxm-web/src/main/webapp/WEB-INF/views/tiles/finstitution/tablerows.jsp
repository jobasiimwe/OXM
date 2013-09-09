
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>

<tbody>
	<c:choose>
		<c:when test="${not empty fInstitutions  && fn:length(fInstitutions) > 0}">
			<c:forEach var="fInstitution" items="${fInstitutions }" varStatus="status">
				<tr id="${fInstitution.id }">
					<c:if test="${not empty nameOfItemOnPage}">
						<td><sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}" id="${fInstitution.id }" />
						</td>
					</c:if>
					<td>${status.count }</td>
					<td>${fInstitution.name }</td>
					<td>${fInstitution.address }</td>
					<td>${fn:length(fInstitution.documents) } documents: <c:if
							test="${not empty fInstitution.documents  && fn:length(fInstitution.documents) > 0}"
						>
							<c:forEach var="document" items="${fInstitution.documents }" varStatus="status">
								<c:choose>
									<c:when test="${not empty nameOfItemOnPage}">
										<a title="Download - ${document.name }" id="lnkDocumentDownload" class="uiButton"
											href="${baseUrl }/download/document/${document.id }"
										>Download</a>
									</c:when>
									<c:otherwise>
										<a title="Download - ${document.name }" id="lnkDocumentDownload" class="uiButton spacedElement"
											href="${baseUrl }/prelogin/download/document/${document.id }"
										>Download</a>
									</c:otherwise>
								</c:choose>

							</c:forEach>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="6" class="redText">Ooops no Financial Institutions found</td>
			</tr>
		</c:otherwise>
	</c:choose>

</tbody>