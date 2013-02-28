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
	<div style="margin: 5px; width: 100%;"><label class="uiLabel">Selling Places >> </label><a title="Back to Selling Places" href="${baseUrl }/sellingplace/view/">Back</a></div>
	<form:form action="${baseUrl }/sellingplace/save/" commandName="sellingprice">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p>
				<p>
					<label>Selling Type</label>
					<form:select path="type" items="${sellingTypes }" itemLabel="name" itemValue="id" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveSellingPlace" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>