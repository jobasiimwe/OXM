<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="seasonTags" tagdir="/WEB-INF/tags/season"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="seasons" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<sysTags:addeditdeletebuttons name="Season" url="season" />
	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>
<div>

	<sysTags:name-of-item-on-page name="Season" />

	<table class="recordTable">

		<thead>
			<seasonTags:header printCheckBox="false" />
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty seasons  && fn:length(seasons) > 0}">
					<c:forEach var="season" items="${seasons }" varStatus="status">
						<seasonTags:row printCheckBox="false" season="${season }"></seasonTags:row>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>

	</table>
</div>