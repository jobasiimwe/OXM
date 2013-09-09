<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>



<div class="container-header">
	<div class="prelogin-header-sub-container">
		<div class="header-logo">
			<div class="text-logo"></div>
		</div>
		<div class="status-msg"><jsp:include page="/WEB-INF/views/tiles/system-message.jsp"></jsp:include></div>
		
		<div class="login-form">
			<jsp:include page="/WEB-INF/views/tiles/prelogin/signin.jsp"></jsp:include>
		</div>
	</div>
</div>