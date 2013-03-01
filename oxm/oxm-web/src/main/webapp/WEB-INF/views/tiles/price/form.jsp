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
	<div style="margin: 5px; width: 100%;"><label class="uiLabel">Price >> </label><a title="Back to Price List" href="${baseUrl }/price/view/1">Back</a></div>
	<form:form action="${baseUrl }/price/save/" commandName="price">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Crop <span class="required">*</span> </label>
					<form:select path="crop" items="${crops }" itemLabel="name" itemValue="id" />
				</p>
				<p>
					<label>Selling Place <span class="required">*</span> </label>
					<form:select path="sellingPlace" items="${sellingPlaces }" itemLabel="name" itemValue="id" />
				</p>
				<p>
					<label>Selling Type</label>
					<form:select path="sellType" items="${sellingTypes }" itemLabel="name" itemValue="id" />
				</p>
				<p>
					<label>Unit of Measure</label>
					<form:select path="unitOfMeasure" items="${unitOfMeasures }" itemLabel="name" itemValue="id" />
				</p>
				<p>
					<label>Price</label>
					<form:input path="price" cssClass="uiTextbox"/>
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePrice" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>