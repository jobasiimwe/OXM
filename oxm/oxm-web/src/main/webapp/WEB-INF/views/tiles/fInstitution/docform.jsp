<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="finstitutiondocs" finstParam="${fInstitution }" isForm="true" />
</div>


<div>
	<form:form action="${baseUrl }/finstitutiondocs/save" enctype="multipart/form-data" commandName="document">

		<form:hidden path="id" />
		<form:hidden path="fInstitution" />
		<div class="splitcontentleft tabular box">
			<jsp:include page="/WEB-INF/views/tiles/docs/formfields.jsp"></jsp:include>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavefInstitutionDocument" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>