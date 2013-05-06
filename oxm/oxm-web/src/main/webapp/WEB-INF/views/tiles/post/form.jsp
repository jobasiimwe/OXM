<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${baseUrl }/post/save" commandName="post">

	<fieldset>
		<legend>
			<b title="New Post">New Post</b>
		</legend>
		<div>
			<form:hidden path="owner" />
			<form:hidden path="datePosted" />
			<table>
				<tr>
					<td>Post Type<form:select path="type" items="${types }" itemLabel="name" itemValue="id"></form:select></td>
					<td rowspan="2"><form:textarea placeholder="Say something please" path="text" cols="60"
							rows="2"
						/></td>
				</tr>
				<tr>
					<td>Crop<form:select path="crop">
							<form:option value="none">--</form:option>
							<form:options items="${crops }" itemLabel="name" itemValue="id" />
						</form:select></td>
					<td><input id="btnSavePost" type="submit" value="Submit" class="uiButton" /></td>
				</tr>
			</table>
		</div>
	</fieldset>
</form:form>
