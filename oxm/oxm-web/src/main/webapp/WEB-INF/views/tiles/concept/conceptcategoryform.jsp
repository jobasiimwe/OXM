<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
form#conceptcategory input[type="text"] {
	width: 70%;
}

#conceptcategorylist span{
	display:inline-block;
	width: 50%;
}
</style>
<div>
	<form:form action="concept?action=save&item=conceptcategory" commandName="conceptcategory">
		<form:hidden path="id" />
		<div>
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p>
				<p>
					<label>Description</label>
					<form:input path="description" cssClass="uiTextbox" />
				</p>
			</div>
		</div>
		<div>
			<input id="btnSaveConceptCategory" class="uiButton" type="submit" value="Save" />
		</div>
	</form:form>
</div>