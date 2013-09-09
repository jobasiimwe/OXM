<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>

<jsp:include page="/WEB-INF/views/tiles/post/searchfields.jsp"></jsp:include>
<jsp:include page="/WEB-INF/views/tiles/post/form.jsp"></jsp:include>


<div>
	<c:choose>
		<c:when test="${not empty posts  && fn:length(posts) > 0}">
			<c:forEach var="post" items="${posts }">
				<sysTags:post post="${post }" />
			</c:forEach>
		</c:when>
	</c:choose>
</div>