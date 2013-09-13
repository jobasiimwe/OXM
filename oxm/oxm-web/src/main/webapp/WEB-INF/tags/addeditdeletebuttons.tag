
<%@ tag body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>
<%@ attribute type="java.lang.String" name="url" required="true"%>
<%@ attribute type="java.lang.String" name="parentId" required="false"%>

<%@ attribute type="java.lang.String" name="child1" required="false"%>
<%@ attribute type="java.lang.String" name="child1Url" required="false"%>

<%@ attribute type="java.lang.String" name="child2" required="false"%>
<%@ attribute type="java.lang.String" name="child2Url" required="false"%>

<%@ attribute type="java.lang.String" name="child3" required="false"%>
<%@ attribute type="java.lang.String" name="child3Url" required="false"%>

<%@ attribute type="java.lang.String" name="child4" required="false"%>
<%@ attribute type="java.lang.String" name="child4Url" required="false"%>

<c:choose>

	<c:when test="${not empty parentId }">
		<a id="lnkAdd${name }" href="${baseUrl}/${url}/add/${parentId}" class="uiButton">Add</a>
		<a id="lnkEdit${name }" href="${baseUrl}/${url}/edit/" class="uiButton">Edit</a>
		<a id="lnkDelete${name }" href="${baseUrl}/${url}/delete/${parentId}/" class="uiButton redText">Delete</a>
	</c:when>
	<c:otherwise>
		<a id="lnkAdd${name }" href="${baseUrl}/${url}/add" class="uiButton">Add</a>
		<a id="lnkEdit${name }" href="${baseUrl}/${url}/edit/" class="uiButton">Edit</a>
		<a id="lnkDelete${name }" href="${baseUrl}/${url}/delete/" class="uiButton redText">Delete</a>
	</c:otherwise>
</c:choose>

<c:if test="${not empty child1 && not empty child1Url }">
	<a id="lnkChild1Of${name }" href="${baseUrl}/${child1Url}/view/" class="uiButton">${child1 }</a>
</c:if>

<c:if test="${not empty child2 && not empty child2Url }">
	<a id="lnkChild2Of${name }" href="${baseUrl}/${child2Url}/view/" class="uiButton">${child2 }</a>
</c:if>

<c:if test="${not empty child3 && not empty child3Url }">
	<a id="lnkChild3Of${name }" href="${baseUrl}/${child3Url}/view/" class="uiButton">${child3 }</a>
</c:if>

<c:if test="${not empty child4 && not empty child4Url }">
	<a id="lnkChild4Of${name }" href="${baseUrl}/${child4Url}/view/" class="uiButton">${child4 }</a>
</c:if>

