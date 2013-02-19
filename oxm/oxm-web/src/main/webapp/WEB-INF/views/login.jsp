<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Registration System - Login</title>
<link type="text/css" rel="stylesheet"
	href="static/css/custom-theme/jquery-ui-1.8.15.custom.css" />
<link type="text/css" rel="stylesheet" href="static/css/system.css" />
<link rel="icon" type="image/png" href="static/images/xx.png" />
<script type="text/javascript" src="static/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="static/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="static/js/system.js"></script>
</head>
<body style="background: #FFF;">
	<div class="login-container">
		<span class="grad-icon "></span>
		<div class="login-header">
			<h3>OXFARM E-SYSTEM</h3>
		</div>
		<div class="login-content">
			<div id="login-fields">
				<form name="loginForm" class="login-form" action="j_spring_security_check" method="post">
					<div class="login-error">
						<c:if test="${not empty errorMessage }">
							<c:out value="${errorMessage }"></c:out>
						</c:if>
					</div>
					<div>
						<label class="uiLabel">Username:</label> 
						<input name="j_username" id="j_username" type="text" class="uiTextbox" />
					</div>
					<div style="padding-top: 10px;">
						<label id="passwordLabel" class="uiLabel">Password:</label>
						<input	name="j_password" id="j_password" type="password" class="uiTextbox" />
					</div>
					<div>
						<input name="btnSubmit" id="btnSubmit" type="submit"
							value="Sign In" class="uiButton" />
					</div>
				</form>
			</div>
		</div>
		<div class="footer">
			<div id="copyright">© 2013 Oxfarm GB. All Rights	Reserved</div>
		</div>
		<div class="login-header">
			<h3></h3>
		</div>
		</div>
</body>
</html>