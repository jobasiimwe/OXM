<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

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
		<oxmBreadcrambs:products />
		form
	</div>

	<form:form action="${baseUrl }/product/save/" commandName="product">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<p>
					<label>Name <span class="required">*</span>
					</label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Description</label>
					<form:input path="description" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Crop</label>
					<form:select path="crop" cssClass="uiDropdown">
						<form:option value="none">-</form:option>
						<form:options itemValue="id" itemLabel="name" items="${crops }"></form:options>
					</form:select>
				</p>
				<p>
					<label>Units of Measure <span class="required">*</span>
					</label>

					<form:checkboxes id="unitsOfMeasure" items="${unitsOfMeasure }" path="unitsOfMeasure"
						itemValue="id" itemLabel="name"
					/>
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveProduct" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>