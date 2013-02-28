<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<label class="uiLabel">Crops >> </label><a title="Back to Crops"
			href="${baseUrl }/crop/view/page/1">Back</a>
	</div>
	<form:form action="${baseUrl }/crop/save/" commandName="crop">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
			</div>
		</div>
		<div class="splitcontentright">
			<div class="box">
				<h3>Concept Categories</h3>
				<p id="conceptcategorylist">
					<form:checkboxes items="${inputs }" path="input.concepts"
						itemValue="id" itemLabel="name" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveConcept" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>