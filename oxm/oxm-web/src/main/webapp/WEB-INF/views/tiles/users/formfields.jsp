
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
#userFormRoleList input[type="checkbox"] {
	float: left;
}

#userFormRoleList span {
	display: inline-block;
	width: 30%;
}

#userFormRoleList label {
	display: inline-block;
}
</style>
<script>
	
</script>
<div class="tabular splitcontentleft">
	<h3>Personal Information</h3>
	<p>
		<label class="uiLabel">Name:<span class="required">*</span>
		</label>
		<form:input path="name" cssClass="uiTextbox" />
	</p>
	<p>
		<label class="uiLabel">Gender:</label>
		<form:select cssClass="uiDropdown" path="gender" itemLabel="name" items="${gender }" />
	</p>
	<p>
		<label class="uiLabel">Date Of Birth:<span class="required">*</span>
		</label>
		<form:input path="dateOfBirth" cssClass="uiTextbox uiDateTextbox" />
	</p>
	<p>
		<label class="uiLabel">Phone 1<span class="required">*</span>
		</label>
		<form:input path="phone1" cssClass="uiTextbox" />
	</p>
	<p>
		<label class="uiLabel">Phone 2</label>
		<form:input path="phone2" cssClass="uiTextbox" />
	</p>
	<p>
		<label class="uiLabel">Profile Picture:</label> <input type="file" name="userPic" />
	</p>
</div>
<div style="padding-left: 20px;" class="tabular splitcontentleft">
	<h3>Account Details</h3>
	<p>
		<label>Username <span class="required">*</span>
		</label>
		<form:input path="username" cssClass="uiTextbox" />
	</p>
	<p>
		<label>Password <span class="required">*</span>
		</label>
		<form:password id="txtPassword" path="clearTextPassword" cssClass="uiTextbox" size="25" />
	</p>
	<p>
		<label>Confirm Password <span class="required">*</span>
		</label> <input type="password" class="uiTextbox" id="txtPasswordConfirmation" size="25" />
	</p>
	<p>
		<label>Status </label>
		<form:select path="status" items="${userstatus }" itemLabel="name"></form:select>
	</p>
	<p>
		<label>Producer Org. </label>
		<form:select id="ddProducerOrg" path="producerOrg">
			<form:option value="none">--</form:option>
			<form:options items="${producerOrgs }" itemLabel="name" itemValue="id"></form:options>
		</form:select>
	</p>
	<p>
		<label>Land Size </label>
		<form:input id="txtLandSize" path="landSize" />
	</p>
</div>
<div style="clear: both;"></div>
<div>
	<div style="clear: both;"></div>
	<div class="box">
		<h3>Roles</h3>
		<p id="userFormRoleList">
			<form:checkboxes id="cbxUserRoles" items="${roles }" path="roles" name="cbxRoles" itemValue="id"
				itemLabel="name"
			/>
		</p>
	</div>
</div>
