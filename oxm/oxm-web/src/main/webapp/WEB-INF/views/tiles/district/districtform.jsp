<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>


<style>
form#district input[type="text"] {
	width: 70%;
}
</style>

<div id="breadcrumbs" style="margin: 5px;">
	District >> <a href="${baseUrl }/district/view/">Back</a>
</div>
<div>
	<form:form action="${baseUrl }/district/save/" commandName="district">
		<form:hidden path="id" />
		<div>
			<div class="box tabular">
				<h3>New District</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p>
			</div>
		</div>
		<div>
			<input id="btnSaveDistrict" type="submit" value="Save" class="uiButton"/>
		</div>
	</form:form>
	
</div>