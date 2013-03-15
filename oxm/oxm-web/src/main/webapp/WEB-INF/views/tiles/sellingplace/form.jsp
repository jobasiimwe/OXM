<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:sellingplaces />
	form
</div>

<div>
	<form:form action="${baseUrl }/sellingplace/save/"
		commandName="sellingprice">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input id="txtName" path="name" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Selling Types<span class="required">*</span>
					</label>
					<form:checkboxes id="cbxSellingTypes" path="sellingTypes"
						items="${sellingTypes }" itemLabel="name" itemValue="id" />
				</p>
				<p>
					<label>District</label>
					<form:select path="district">
						<form:option value="none">--</form:option>
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