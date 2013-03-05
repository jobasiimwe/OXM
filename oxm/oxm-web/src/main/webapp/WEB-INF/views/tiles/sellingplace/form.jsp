<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

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
		<label class="uiLabel">You are here > </label>
		<oxmBreadcrambs:cpanel />
		>
		<oxmBreadcrambs:sellingplaces />
		> form
	</div>

	<form:form action="${baseUrl }/sellingplace/save/"
		commandName="sellingprice">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Selling Types</label>
					<form:checkboxes path="sellingTypes" items="${sellingTypes }"
						itemLabel="name" itemValue="id" />
				</p>
				<p>
					<label>District</label>
					<form:select path="district">
						<form:option value="none"></form:option>
						<form:options items="${districts }" itemLabel="name"
							itemValue="id" />
					</form:select>
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveSellingPlace" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>