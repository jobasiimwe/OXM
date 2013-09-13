<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${baseUrl}/price/search" commandName="postsearch">
	<fieldset>
		<legend>
			<span title="Search Posts">Search Posts</span>
		</legend>
		<form:hidden path="propertiesMap['adminview'].booleanValue" />
		<div style="float: left;">
			<table>
				<tr>
					<td>Posted By <form:input path="propertiesMap['ownerName'].value" /></td>
					<td>Crop<form:select id="ddCrops" cssStyle="width: 100px;" cssClass="uiDropdown"
							path="propertiesMap['cropid'].value"
						>
							<form:option value="" cssStyle="background: blue;"></form:option>
							<form:options items="${crops }" itemLabel="name" itemValue="id" />
						</form:select>
					</td>
					<td>Type <form:select id="ddType" cssStyle="width: 100px;" cssClass="uiDropdown"
							path="propertiesMap['typeid'].value"
						>
							<form:option value=""></form:option>
							<form:options items="${types }" itemLabel="name" itemValue="id" />
						</form:select>
					</td>
					<td>From <form:input id="fromdate" cssStyle="width: 100px;"
							cssClass="uiTextbox uiDateTextbox" path="propertiesMap['fromdate'].value"
						/>
					</td>
					<td>To <form:input id="todate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox"
							path="propertiesMap['todate'].value"
						/>
					</td>
					<td><input id="filter" type="submit" value="Search" name="btnFilter"  class="uiButton"  /></td>
				</tr>
			</table>
		</div>
	</fieldset>
</form:form>
