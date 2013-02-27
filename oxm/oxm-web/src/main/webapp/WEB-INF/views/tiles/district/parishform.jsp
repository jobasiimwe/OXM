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
	District: >> ${subCounty.district.name } >> SubCounty : ${subCounty.name } >> <a href="${baseUrl }/parish/view/${subCounty.id }">Back</a>
</div>
<div>
	<form:form action="${baseUrl }/parish/save/${subCounty.id }" commandName="parish">
		<form:hidden path="id" />
		<div>
			<div class="box tabular">
				<h3>New Parish</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p>
			</div>
		</div>
		<div>
			<input id="btnSaveParish" type="submit" value="Save" class="uiButton"/>
		</div>
	</form:form>
	
</div>