<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<jsp:include page="/WEB-INF/views/tiles/blog/searchfields.jsp"></jsp:include>

<c:if test="${not empty adminview and adminview }">
	<div style="margin: 5px; width: 100%;">
		<oxmBreadcrambs:cpanel name="blogs" />
	</div>

	<sysTags:name-of-item-on-page name="Blog" />

	<div id="buttonStrip" class="group buttonStrip">
		<a id="lnkAddBlog" href="${baseUrl}/blog/add" class="uiButton">Create New Blog</a>

		<div style="float: right;">
			<%@ include file="/WEB-INF/views/navigation.jsp"%>
		</div>
		<div style="clear: both;"></div>
	</div>
</c:if>

<div>
	<c:choose>
		<c:when test="${not empty blogs  && fn:length(blogs) > 0}">
			<c:forEach var="blog" items="${blogs }">
				<sysTags:blog blog="${blog }" showPreview="true" />
			</c:forEach>
		</c:when>
	</c:choose>
</div>