<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<style>
form#role input[type="text"] {
	width: 70%;
}

#permissionlist span {
	display: inline-block;
	width: 25%;
}
</style>


<oxmBreadcrambs:cpanel name="roles" isForm="true" />

<div>
	<form:form action="${baseUrl }/role/save" commandName="role">

		<form:hidden path="id" />
		<div class="splitcontentleft box tabular">
			<h3>Information</h3>
			<p>
				<label>Name <span class="required">*</span>
				</label>
				<form:input id="txtName" path="name" cssClass="uiTextbox" readonly="${ (role.name eq 'ROLE_ADMINISTRATOR') ? true: false}" />
			</p>
			<p>
				<label>Description <span class="required">*</span>
				</label>
				<form:input id="txtDescription" path="description" cssClass="uiTextbox" />
			</p>
			<input id="btnSaveRole" type="submit" value="Save" class="uiButton" />
		</div>
		<div class="box splitcontentright">
			<h3>Permissions</h3>
			<p id="permissionlist">
				<form:checkboxes items="${permissions }" path="permissions" itemValue="id" itemLabel="name" />
			</p>
		</div>
		<div style="clear: both"></div>

	</form:form>
</div>