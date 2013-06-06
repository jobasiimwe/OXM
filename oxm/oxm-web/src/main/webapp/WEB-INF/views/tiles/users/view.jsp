<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:users />
</div>

<div id="buttonStrip"></div>
<div>
	<div class="searchform group">
		<form:form action="${baseUrl}/user/search" commandName="usersearch">
			<jsp:include page="/WEB-INF/views/tiles/users/searchfields.jsp"></jsp:include>
		</form:form>
	</div>
	<div style="clear: both"></div>
</div>

<div class="buttonStrip">
	<div style="float: left;" class="contextual">
		<a id="lnkAddUser" class="uiButton" href="${baseUrl }/user/add/:">Add</a>
		<a id="lnkEditUser" class="uiButton" href="${baseUrl }/user/edit/:/">Edit</a>
		<a id="lnkDeleteUser" class="uiButton" href="${baseUrl }/user/delete/">Delete</a>
		<a id="lnkImportProducers" class="uiButton spacedElement" href="${baseUrl }/import/producers">Import
			Producers</a>
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
	<jsp:include page="/WEB-INF/views/tiles/users/userlist.jsp"></jsp:include>
</div>

