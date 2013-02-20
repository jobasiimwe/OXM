<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Oxfarm GB: E-System</title>
<c:set var="baseUrl"
	value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<link type="text/css" rel="stylesheet"
	href="static/css/custom-theme/jquery-ui-1.8.15.custom.css" />
<link type="text/css" rel="stylesheet"
	href="${baseUrl}/static/css/system.css" />

<script type="text/javascript" src="static/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript"
	src="static/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="static/js/system.js"></script>

<link rel="icon" type="image/png" href="/static/images/oxm-footer-lg.png" />

</head>
<body>
	<div class="container-header">
		<div class="logo"></div>
		<form name="loginForm" action="j_spring_security_check" method="post">
			<div class="login-box">
				<div class="username-dv">
					<div style="font-weight: bold; margin-bottom: 2px;">
						<label class="uiLabel">Username:</label>
					</div>
					<div>
						<input name="j_username" id="j_username" type="text"
							class="uiTextbox" />
					</div>
					<div>
						<input name="rememberme" type="checkbox" class="uiCheckbox"
							value="Remember Me" /> <label class="uiLabel">Keep me
							logged in</label>
					</div>
				</div>
				<div class="pwd-dv">
					<div style="font-weight: bold; margin-bottom: 2px;">
						<label class="uiLabel">Password:</label>
					</div>
					<div>
						<input name="j_password" id="j_password" type="password"
							class="uiTextbox" />
					</div>
					<div>
						<div class="login-error">
							<c:if test="${not empty errorMessage }">
								<c:out value="${errorMessage }"></c:out>
							</c:if>
						</div>
					</div>
				</div>
				<div class="btn-dv">
					<input name="btnSubmit" id="btnSubmit" type="submit"
						value="Sign In" class="uiButton" />
				</div>
			</div>
		</form>
	</div>

	<div class="login-signup-dv">
		<div class="left-side-dv"></div>
		<div class="right-side-dv">
			<div style="display: block; margin-top: 5px;">
				<label class="sign-up-txt">Sign up </label>
			</div>
			<div class="sign-up-content">

				<div class="box tabular">
					<form name="registerUser" action="" method="post">
						<p>
							<label class="uiLabel">FirstName:</label> <input
								class="uiTextbox" onkeypress="checkChar(event)" />
						</p>
						<p>
							<label class="uiLabel">LastName:</label> <input class="uiTextbox"
								onkeypress="checkChar(event)" />
						</p>
						<p>
							<label class="uiLabel">Gender:</label> <input class="uiTextbox"
								onkeypress="checkChar(event)" />
						</p>
						<p>
							<label class="uiLabel">Date Of Birth:</label> <input
								class="uiTextbox" onkeypress="checkChar(event)" />
						</p>
					</form>
				</div>
				<div>
					<button class="sign-up-button-dv" type="submit">Sign Up</button>			
				</div>
			</div>
		</div>
	</div>
	<div class="login-footer">
		<div class="oxm-item"></div>
		<div class="oxm-copyright">
			<label>© 2013</label>
		</div>
	</div>
</body>
</html>