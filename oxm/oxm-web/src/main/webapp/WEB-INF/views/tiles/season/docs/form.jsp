<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="seasondocs" seasonParam="${season }" isForm="true" />
</div>


<div>
	<form:form action="${baseUrl }/seasondocs/save" enctype="multipart/form-data" commandName="document">

		<form:hidden path="id" />
		<form:hidden path="season" />
		<div class="splitcontentleft tabular box">
			<jsp:include page="/WEB-INF/views/tiles/docs/formfields.jsp"></jsp:include>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveSeasonDocument" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>