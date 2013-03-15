<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<label class="uiLabel">You are here >> </label>
	<oxmBreadcrambs:cpanel />
	>
	<oxmDistrictBreadcrambs:districts />
	> form
</div>

<style>
form#district input[type="text"] {
	width: 70%;
}
</style>

<div>
	<form:form action="${baseUrl }/district/save/" commandName="district">
		<form:hidden path="id" />
		<div>
			<div class="box tabular">
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
			</div>
		</div>
		<div>
			<input id="btnSaveDistrict" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>

</div>