<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

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
	<div style="margin: 5px; width: 100%;"><label class="uiLabel">Concepts >> </label><a title="Back to Concepts" href="${baseUrl }/concept/view/x">Back</a></div>
	<form:form action="${baseUrl }/concept/save/" commandName="concept">

		<form:hidden path="id" />
		<div class="splitcontentleft">
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
		<div class="splitcontentright">
			<div class="box">
				<h3>Concept Categories</h3>
				<p id="conceptcategorylist">
					<form:checkboxes items="${conceptcategories }" path="categories" itemValue="id"
						itemLabel="name" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveConcept" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>