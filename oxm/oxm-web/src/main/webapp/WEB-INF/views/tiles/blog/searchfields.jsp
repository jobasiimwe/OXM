<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${baseUrl}/blog/search" commandName="blogsearch">
	<fieldset>
		<legend>
			<span title="Search Posts">Search Blogs</span>
		</legend>
		<form:hidden path="propertiesMap['adminview'].booleanValue" />
		<form:hidden path="propertiesMap['createdBy'].value" />
		<div style="float: left;">
			<table>
				<tr>
					<td>Posted By <form:input path="propertiesMap['createdByName'].value" /></td>
					<td>Title/Text <form:input path="propertiesMap['text'].value" /></td>

					<td>From <form:input id="fromdate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox"
							path="propertiesMap['fromdate'].value"
						/>
					</td>
					<td>To <form:input id="todate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox"
							path="propertiesMap['todate'].value"
						/>
					</td>
					<td><input id="filter" type="submit" value="Search" name="btnFilter" class="uiButton" /></td>
				</tr>
			</table>
		</div>
	</fieldset>
</form:form>
