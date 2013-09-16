


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="seasondocs" seasonParam="${season }" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons url="seasondocs" name="SeasonDocs" parentId="${season.id }" />
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>

<sysTags:name-of-item-on-page name="Document" />


<jsp:include page="/WEB-INF/views/tiles/docs/list.jsp"></jsp:include>