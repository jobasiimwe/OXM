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
	<div style="margin: 5px; width: 100%;"><label class="uiLabel">Season >> </label><a title="Back to Price List" href="${baseUrl }/season/view/">Back</a></div>
	<form:form action="${baseUrl }/season/save/" commandName="season">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Start Date</label>
					<form:input path="startDate" cssClass="uiTextbox uiDateTextbox"/>
				</p>
				<p>
					<label>End Date</label>
					<form:input path="endDate" cssClass="uiTextbox uiDateTextbox"/>
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveSeason" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>