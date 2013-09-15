<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="concepts" isForm="true" />
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
<div class="box ">
	<form:form action="${baseUrl }/concept/save/" commandName="concept">

		<form:hidden path="id" />
		<div>
			<div class="tabular splitcontentleft">
				<p>
					<label>
						Term/Concept Name
						<span class="required">*</span>
					</label>
					<form:input id="mtxtName" path="name" cssClass="uiTextbox medium" />
				</p>
				<p>
					<label>Description</label>
					<form:input id="mtxtDescription" path="description" cssClass="uiTextbox long" />
				</p>
			</div>

			<div class="splitcontentright" style="width: 50%;">
				<b>Categories</b>
				<p id="conceptcategorylist">
					<form:checkboxes items="${conceptcategories }" path="categories" itemValue="id" itemLabel="name" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveConcept" type="submit" value="Save" class="uiButton" />
		</div>

	</form:form>
</div>