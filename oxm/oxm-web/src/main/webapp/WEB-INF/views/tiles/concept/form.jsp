<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmConceptBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/concept"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmConceptBreadcrambs:concepts />
	form
</div>

<style>
form#concept input[type="text"] {
	width: 70%;
}

#conceptcategorylist span {
	display: inline-block;
	width: 50%;
}
</style>
<div class="box tabular">
	<form:form action="${baseUrl }/concept/save/" commandName="concept">

		<form:hidden path="id" />
		<div>
			<div class="splitcontentleft">
				<b>Term</b>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Description</label>
					<form:input path="description" cssClass="uiTextbox" />
				</p>
			</div>

			<div class="splitcontentright" style="width: 50%;">
				<b>Categories</b>
				<p id="conceptcategorylist">
					<form:checkboxes items="${conceptcategories }" path="categories"
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