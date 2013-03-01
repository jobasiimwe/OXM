<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
form#concept input[type="text"] {
	width: 70%;
}

#conceptcategorylist span{
	display:inline-block;
	width: 50%;
}
</style>
<div>
	<div style="margin: 5px; width: 100%;"><label class="uiLabel">Production Organization >> </label><a title="Back to Production Organization" href="${baseUrl }/pOrganization/view/">Back</a></div>
	<form:form action="${baseUrl }/pOrganization/save/" commandName="pOrganization">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrganization" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>