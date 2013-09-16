
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>


<c:if test="${not empty document.documentType }">
	<form:hidden path="documentType" />
</c:if>


<form:hidden path="contentType" />
<form:hidden path="documentExtension" />
<form:hidden path="documentUrl" />
<form:hidden path="dateCreated" />

<c:if test="${not empty document.createdBy }">
	<form:hidden path="createdBy" />
</c:if>

<p>
	<label>Name:</label>
	<form:input type="text" class="uiTextbox long" id="txtName" path="name" />
</p>
<p>
	<label>Attach File: </label> <input type="file" name="file">
</p>
<p>
	<label>Other Info</label>
	<form:textarea rows="3" cols="50" path="otherInfo" style="width: 380px; height: 50px;" />
</p>
