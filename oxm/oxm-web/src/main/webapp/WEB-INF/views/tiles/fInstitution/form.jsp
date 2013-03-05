<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div style="margin: 5px; width: 100%;"><label class="uiLabel">Financial Institution >> </label><a title="Back to Finanacial Institution List" href="${baseUrl }/fInstitution/view/">Back</a></div>
	<form:form action="${baseUrl }/fInstitution/save/" commandName="fInstitution">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name</label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p>
				<p>
					<label>Address</label>
					<form:textarea path="address" cssClass="uiTextbox "/>
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavefInstitution" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>