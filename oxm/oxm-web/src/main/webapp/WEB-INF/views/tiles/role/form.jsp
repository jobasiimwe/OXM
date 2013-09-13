<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<style>
form#role input[type="text"] {
	width: 70%;
}

#permissionlist span {
	display: inline-block;
	width: 25%;
}
</style>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:roles />
	Form
</div>

<div>
	<form:form action="${baseUrl }/role/save" commandName="role">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input id="txtName" path="name" cssClass="uiTextbox"
						readonly="${ (role.name eq 'ROLE_ADMINISTRATOR') ? true: false}" />
				</p>
				<p>
					<label>Description <span class="required">*</span>
					</label>
					<form:input id="txtDescription" path="description" cssClass="uiTextbox" />
				</p>
			</div>
		</div>
		<div class="splitcontentright">
			<div class="box">
				<h3>Permissions</h3>
				<p id="permissionlist">
					<form:checkboxes items="${permissions }" path="permissions"
						itemValue="id" itemLabel="name" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveRole" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>