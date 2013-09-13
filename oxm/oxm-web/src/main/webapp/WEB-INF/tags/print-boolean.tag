
<%@ tag body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute type="java.lang.Boolean" name="value" required="true"%>

<%@ attribute type="java.lang.Boolean" name="printYes" required="false"%>
<%@ attribute type="java.lang.Boolean" name="printNo" required="false"%>
<%@ attribute type="java.lang.Boolean" name="printTrue" required="false"%>
<%@ attribute type="java.lang.Boolean" name="printFalse" required="false"%>

<c:choose>
	<c:when test="${value }">
		<c:if test="${printYes }">Yes</c:if>
		<c:if test="${printTrue }">True</c:if>
	</c:when>
	<c:otherwise>
		<c:if test="${printNo }">No</c:if>
		<c:if test="${printFalse }">false</c:if>
	</c:otherwise>
</c:choose>

