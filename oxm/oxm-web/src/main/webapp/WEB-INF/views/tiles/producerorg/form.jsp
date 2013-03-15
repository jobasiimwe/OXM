<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
	

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:producerorgs />
</div>

<div>
	
	<form:form action="${baseUrl }/producerorg/save/"
		commandName="pOrganization">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<table>
					<tr>
						<td><label>Name <span class="required">*</span> </label></td>
						<td><form:input path="name" cssClass="uiTextbox" /></td>
					</tr>
					<tr>
						<td><label>District <span class="required">*</span> </label>
						</td>
						<td><form:select id="dddistrict" path="district"
								cssClass="uiTextbox" items="${districts }" itemLabel="name"
								itemValue="id" /></td>
					</tr>
					<tr>
						<td><label>Sub-County <span class="required">*</span>
						</label></td>
						<td><form:select id="ddsubcounty" path="subCounty"
								cssClass="uiTextbox" /></td>
					</tr>


				</table>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrganization" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>