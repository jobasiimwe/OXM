
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div class="login-box">
	<form name="loginForm" action="${baseUrl}/j_spring_security_check" method="post">
		<div class="username-dv">
			<div>
				<label class="uiLabel">Username:</label>
			</div>
			<div>
				<input name="j_username" id="j_username" type="text" class="uiTextbox" />
			</div>
		</div>
		<div class="pwd-dv">
			<div>
				<label class="uiLabel">Password:</label>
			</div>
			<div>
				<input name="j_password" id="j_password" type="password" class="uiTextbox" />
			</div>
		</div>
		<div class="btn-dv">
			<div>
				<input name="btnSubmit" id="btnSubmit" type="submit" value="Sign In" class="uiButton" />
			</div>
		</div>
		<div>
			<div class="login-error">
				<c:if test="${not empty loginErrorMessage}">
					<c:out value="${loginErrorMessage}"></c:out>
				</c:if>
			</div>
		</div>
	</form>
	<div class="sign-up-div" style="display: none;">
		<span class="sign-up-txt">New here? Sign up for free. </span>
		<a href="${baseUrl}/annoymous/create" class="uiButton">Sign Up</a>
	</div>

</div>