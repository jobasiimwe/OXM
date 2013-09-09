
<%@ tag body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>
<%@ variable declare="true" variable-class="java.lang.String"
	name-given="nameOfItemOnPage" scope="AT_END"%>

<c:set var="nameOfItemOnPage" scope="request" value="${name }"></c:set>
<input id="nameOfItemOnPage" type="hidden" value="${name }">

