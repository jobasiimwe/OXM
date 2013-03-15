<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
form#district input[type="text"] {
	width: 70%;
}
</style>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmDistrictBreadcrambs:districts />
	<oxmDistrictBreadcrambs:subcounty
		district="${parish.subCounty.district }" />
	<oxmDistrictBreadcrambs:parish subCounty="${parish.subCounty }" />
	<oxmDistrictBreadcrambs:village parish="${parish }" />
	Village form
</div>


<div>
	<form:form action="${baseUrl }/village/save/${parish.id }"
		commandName="village">
		<form:hidden path="id" />
		<form:hidden path="parish" />
		<div>
			<div class="box tabular">
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input id="txtName" path="name" cssClass="uiTextbox" />
				</p>
			</div>
		</div>
		<div>
			<input id="btnSaveVillage" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>

</div>