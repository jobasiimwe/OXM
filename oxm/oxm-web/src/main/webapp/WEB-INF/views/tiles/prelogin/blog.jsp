<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="breadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%; display: none;">
	<breadcrambs:prelogin name="blogs" />
</div>

<div>
	<p style="padding: 5px">Acholi Farmers Portal is an Information System for farmers especially in Acholi sub-region. Here, you can find
		latest prices, weather fore-casts and advice on several other agricultural topics...</p>
	<c:choose>
		<c:when test="${not empty blogs  && fn:length(blogs) > 0}">
			<c:forEach var="blog" items="${blogs }">
				<sysTags:blog-prelogin blog="${blog }" showPreview="true" />
			</c:forEach>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty blog}">
				<sysTags:blog-prelogin blog="${blog }" showPreview="false" />
			</c:if>
		</c:otherwise>
	</c:choose>
</div>