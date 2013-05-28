


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:financialinstitutions />
	<oxmBreadcrambs:financialinstitution finst="${fInstitution }" />
</div>

<div id="buttonStrip">
	<div class="contextual" style="float: left;">
		<a id="lnkAddFInstitutionDoc" class="uiButton"
			href="${baseUrl }/finstitution/docs/add/${fInstitution.id }"
		>Add</a>
		<a id="lnkEditFInstitutionDoc" class="uiButton" href="${baseUrl }/finstitution/docs/edit/">Edit</a>
		<a id="lnkDeleteFInstitutionDoc" class="uiButton"
			href="${baseUrl }/finstitution/docs/delete/${fInstitution.id }/"
		>Delete</a>

	</div>
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
	<div style="clear: both"></div>
</div>

<oxmTags:name-of-item-on-page name="Document" />


<jsp:include page="/WEB-INF/views/tiles/docs/list.jsp"></jsp:include>