<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>	
	#staffForm .uiDropdown{
		width: 125px;
	}
	
	#staffForm .formSection span{
		width: 300px;
	}
</style>
<form:form action="${baseUrl}/user/save/${qUserPage}" commandName="user" id="userForm" cssClass="tabular" enctype="multipart/form-data">
	<div class="box">
		<form:hidden id="id" path="id"/>
		<input type="hidden" id="qUserPage" name="user" value="${qUserPage}" />
		<input type="hidden" id="baseUrl" name="baseUrl" value="${baseUrl}" />
		<div class="splitcontentleft">	
			<h3>Personal Information</h3>
			<p>
				<label class="uiLabel">First Name:<span class="required">*</span></label>
				<form:input path="firstName" cssClass="uiTextbox"/>
			</p>
			<p>
				<label class="uiLabel">Last Name:<span class="required">*</span></label>
				<form:input path="lastName" cssClass="uiTextbox"/>
			</p>
			<p>
				<label class="uiLabel">Gender:</label>
				<form:select cssClass="uiDropdown" path="gender" itemLabel="name" items="${gender }"/>
			</p>
			<p>
				<label class="uiLabel">Date Of Birth:<span class="required">*</span></label>
				<form:input path="dateOfBirth" cssClass="uiTextbox uiDateTextbox"/>
			</p>
			<p>
				<label class="uiLabel">Phone 1<span class="required">*</span></label>
				<form:input path="phone1" cssClass="uiTextbox"/>
			</p>
			<p>
				<label class="uiLabel">Phone 2</label>
				<form:input path="phone2" cssClass="uiTextbox"/>
			</p>
			<p>
				<label class="uiLabel">Profile Picture:</label>
				<input type="file" name="userPic" />
			</p>
			<p>
				<label class="uiLabel">User Type:</label>
			</p>
		</div>
		
		<div class="splitcontentright">
			<h3>Account Details</h3>
				<p>
					<label>Username <span class="required">*</span> </label>
					<form:input path="username" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Password <span class="required">*</span> </label>
					<form:password id="txtPassword" path="clearTextPassword" cssClass="uiTextbox"
						size="25" />
				</p>

				<p>
					<label>Confirm Password <span class="required">*</span> </label>
					<input type="password" class="uiTextbox" 
					id="txtPasswordConfirmation" size="25" />
				</p>
				<p>
					<label>Status </label>
					<form:select path="status" items="${userstatus }" itemLabel="name"></form:select>
				</p>
		</div>
		<div style="clear: both"></div>
		<div class="splitcontentright">
			<div class="box">
				<h3>Roles</h3>
				<p>
					<form:checkboxes items="${roles }" path="roles" itemValue="id" itemLabel="name" />
				</p>
			</div>
		</div>
	</div>
	
	<div style="clear: both">
		<span style="display:inline-block;">
			<input id="btnSave" type="submit" value="Save" class="uiButton"/>
		</span>
		<span style="display:inline-block;">
			<input id="btnCancel" type="button" value="Cancel" class="uiButton"/>
		</span>
	</div>
</form:form>
