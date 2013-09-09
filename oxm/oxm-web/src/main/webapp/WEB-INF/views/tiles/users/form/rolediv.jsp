
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
	text-align: left;
}
</style>


<div>
	<div class="tabular">
		<h3>Roles</h3>
		<p id="userFormRoleList">
			<form:checkboxes id="cbxUserRoles" items="${roles }" path="roles" name="cbxRoles" itemValue="id"
				itemLabel="name"
			/>
		</p>
	</div>
</div>