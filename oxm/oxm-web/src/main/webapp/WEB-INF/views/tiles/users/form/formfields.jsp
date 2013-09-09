
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="tabular splitcontentleft">
	<h3>Personal Information</h3>
	<p>
		<label class="uiLabel">Name:<span class="required">*</span>
		</label>
		<form:input path="name" cssClass="uiTextbox long" />
		<span>
			<label class="uiLabel">Gender:</label>
			<form:select cssClass="uiDropdown short" path="gender" items="${gender}" itemLabel="name">
			</form:select>
		</span>
	</p>
	<p>
		<label class="uiLabel">Date Of Birth:<span class="required">*</span>
		</label>
		<form:input path="dateOfBirth" cssClass="uiTextbox uiDateTextbox medium" />
		<span>
			<label class="uiLabel">Age:</label>
			<form:input cssClass="uiTextbox short" path="age" items="${gender}" itemLabel="name" />
		</span>
	</p>
	<p>
		<label class="uiLabel">Profile Picture:</label> <input type="file" name="userPic" />
	</p>
	<h3>Address</h3>
	<p>
		<label class="uiLabel">Address<span class="required">*</span>
		</label>
		<form:input path="address" cssClass="uiTextbox long" />
	</p>
	<p>
		<label class="uiLabel">Phone 1<span class="required">*</span>
		</label>
		<form:input path="phone1" cssClass="uiTextbox medium" />
		<span>
			<label class="uiLabel">Phone 2</label>
			<form:input path="phone2" cssClass="uiTextbox medium" />
		</span>
	</p>

</div>
<div style="padding-left: 20px;" class="tabular splitcontentleft">
	<h3>Account Details</h3>
	<p>
		<label>Username <span class="required">*</span>
		</label>
		<form:input path="username" cssClass="uiTextbox long" />
		<span>
			<label>Status </label>
			<form:select path="status" items="${userstatus }" itemLabel="name" class="uiDropdown short2"></form:select>
		</span>
	</p>
	<p>
		<label>Password <span class="required">*</span>
		</label>
		<form:password id="txtPassword" path="clearTextPassword" cssClass="uiTextbox short2" />
		<span>
			<label>Confirm Password <span class="required">*</span>
			</label> <input id="txtPasswordConfirmation" type="password" class="uiTextbox  short2" />
		</span>
	</p>

</div>
<div style="clear: both;"></div>

<jsp:include page="/WEB-INF/views/tiles/users/form/rolediv.jsp"></jsp:include>

<jsp:include page="/WEB-INF/views/tiles/users/form/producerfields.jsp"></jsp:include>

