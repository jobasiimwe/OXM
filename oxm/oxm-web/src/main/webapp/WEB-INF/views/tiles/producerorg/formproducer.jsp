<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
form#concept input[type="text"] {
	width: 70%;
}

#conceptcategorylist span {
	display: inline-block;
	width: 50%;
}
</style>
<div>
	<div style="margin: 5px; width: 100%;">
		<label class="uiLabel">Producer Organization >> </label><a
			title="Back to Production Organization"
			href="${baseUrl }/pOrganization/view/">Back</a>
	</div>
	<form:form action="${baseUrl }/producerorg/producers/save/"
		commandName="producer">

		<form:hidden path="id" />
		
		<jsp:include page="/WEB-INF/views/tiles/users/formfields.jsp"></jsp:include>
		
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrganization" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>