<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="errorMsg"
	<c:choose>
		<c:when test="${not empty errorMessage}">
			class="<c:out value="ui-state-error ui-corner-all"></c:out>"	
		</c:when>
		<c:otherwise>
			class="<c:out value="ui-state-error ui-corner-all hide"></c:out>"
		</c:otherwise>
	</c:choose>>
	<span class="ui-icon ui-icon-alert"
		style="float: left; margin-right: .3em;"></span> <strong>Alert:</strong>
	<span id="eMsg"> <c:if test="${not empty errorMessage }">
			<c:out value="${errorMessage }"></c:out>
		</c:if> </span>
</div>
<div id="systemMsg"
	<c:choose>
		<c:when test="${not empty systemMessage}">
			class="<c:out value="ui-state-highlight ui-corner-all"></c:out>"	
		</c:when>
		<c:otherwise>
			class="<c:out value="ui-state-highlight ui-corner-all hide"></c:out>"
		</c:otherwise>
	</c:choose>>
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> <strong>Hey!</strong>
	<span id="sMsg"> <c:if test="${not empty systemMessage }">
			<c:out value="${systemMessage }"></c:out>
		</c:if> </span>
</div>