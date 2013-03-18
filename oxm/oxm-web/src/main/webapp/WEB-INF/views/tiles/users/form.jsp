<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:users />
	form
</div>

<style>
#staffForm .uiDropdown {
	width: 125px;
}

#staffForm .formSection span {
	width: 300px;
}
</style>
<form:form action="${baseUrl}/user/save/${qUserPage}" commandName="user"
	id="userForm" cssClass="tabular" enctype="multipart/form-data">
	<div class="box">
		<form:hidden id="id" path="id" />
		<input type="hidden" id="qUserPage" name="user" value="${qUserPage}" />
		<input type="hidden" id="baseUrl" name="baseUrl" value="${baseUrl}" />

		<jsp:include page="/WEB-INF/views/tiles/users/formfields.jsp"></jsp:include>
	</div>

	<div style="clear: both">
		<span style="display: inline-block;"> <input id="btnSave"
			type="submit" value="Save" class="uiButton" /> </span> <span
			style="display: inline-block;"> <input id="btnCancelUserEdit"
			type="button" value="Cancel" class="uiButton" /> </span>
	</div>
</form:form>
