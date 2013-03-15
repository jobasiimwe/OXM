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
		<oxmBreadcrambs:cpanel startingBreadcramb="true" />
		<oxmBreadcrambs:crops />
		> form
	</div>

	<form:form action="${baseUrl }/crop/save/" commandName="crop">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box ">
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Inputs <span class="required">*</span> </label>
					<form:checkboxes id="cropInputs" items="${cropInputs }"
						path="inputs" itemValue="id" itemLabel="name" />
				</p>
				<p>
					<label>Seed Varieties <span class="required">*</span> </label>
					<form:checkboxes id="seedVarieties" items="${seedVarieties }"
						path="seedVarieties" itemValue="id" itemLabel="name" />
				</p>
				<p>
					<label>Ploughing Methods <span class="required">*</span> </label>
					<form:checkboxes id="ploughingMethods" items="${ploughingMethods }"
						path="ploughingMethods" itemValue="id" itemLabel="name" />
				</p>
				<p>
					<label>Inter Croping Types <span class="required">*</span>
					</label>
					<form:checkboxes id="interCropingTypes"
						items="${interCropingTypes }" path="interCroppingTypes"
						itemValue="id" itemLabel="name" />
				</p>
				<p>
					<label>Units of Measure <span class="required">*</span> </label>
					<form:checkboxes id="unitsOfMeasure" items="${unitsOfMeasure }"
						path="unitsOfMeasure" itemValue="id" itemLabel="name" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveCrop" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>