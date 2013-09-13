
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ tag body-content="empty"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>

You are here
<span class="arrow-right"></span>
<span class="arrow-right"></span>

<a title="Login page" href="${baseUrl }/">Home</a>
<span class="arrow-right"></span>

<c:if test="${name eq 'crops' }">
	<a href="${baseUrl }/prelogin/crops">Crops</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'sellingplaces' }">
	<a href="${baseUrl }/prelogin/sellingplaces">Markets</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'prices' }">
	<a href="${baseUrl }/prelogin/prices">Prices</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'finstitutions' }">
	<a href="${baseUrl }/prelogin/finstitutions">Financial services</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'seasons' }">
	<a href="${baseUrl }/prelogin/seasons">Seasons/Weather</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'blogs' }">
	<a href="${baseUrl }/prelogin/blogs">Blogs</a>
	<span class="arrow-right"></span>
</c:if>