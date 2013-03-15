<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<form:form action="${baseUrl}/annoymous/save"
	commandName="annoymousUser" id="userForm" cssClass="tabular"
	enctype="multipart/form-data">
	<div class="box">
		<form:hidden id="id" path="id" />
		<input type="hidden" id="qUserPage" name="user" value="${qUserPage}" />
		<input type="hidden" id="baseUrl" name="baseUrl" value="${baseUrl}" />
		<div style="display: block; margin: 10px; display: inline; clear: both;">
			<a href="${baseUrl}/" title="Back to login page" class="uiButton">Back </a>
		</div>
		<div>
		<div style="clear: both"> <jsp:include page="/WEB-INF/views/tiles/users/formfields.jsp"></jsp:include>
		</div>
		<div class="splitcontentleft" style="margin-top: 15px;">
			<div class="box">
			<h3>Location</h3>
			<p>	
			<label>Sub County </label>
			<form:select path="subCounty" items="${subcounties }" itemLabel="name"></form:select>
				</p>
				<p>	
				<label>Parish </label>
			<form:select path="parish" items="${parishes }" itemLabel="name"></form:select>
				</p>
				<p>	
				<label>Village </label>
			<form:select path="village" items="${villages }" itemLabel="name"></form:select>
				</p>
			</div>
			<button class="uiButton" type="submit">Create
				Account</button>
		</div>
		</div>
	</div>

</form:form>
