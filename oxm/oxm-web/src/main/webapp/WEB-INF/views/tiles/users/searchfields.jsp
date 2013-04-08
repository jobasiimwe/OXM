<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<style>
<!--
.tabular .searchPanel {
	display: table-cell;
	margin-top: 5px;
	/*width: 20%;*/
}

.tabular .searchPanel label {
	width: 60px;
	text-align: right;
	vertical-align: top;
}
-->
</style>


<fieldset>
	<legend>
		<a id="search-collapser" href="javascript:void(0)"
			title="Search Panel">Search Panel</a>
	</legend>

	<form:hidden path="propertiesMap['committee'].value" />
	<form:hidden path="propertiesMap['committeemember'].value" />
	
	<div style="float: left;">
		<table>
			<tr>
				<td>Name/User-name <form:input
						cssClass="uiTextbox searchTextbox"
						path="propertiesMap['name-or-username'].value" />
				</td>
				<td>Producer org. <form:select id="ddPOrg"
						cssStyle="width:120px;" cssClass="uiDropdown searchDropdown"
						path="propertiesMap['porg'].value">
						<form:option value=""></form:option>
						<form:options items="${pOrgs }" itemLabel="nameAndSubCounty"
							itemValue="id" />
					</form:select>
				</td>
				<td>Role <form:select id="ddRole" cssStyle="width:120px;"
						cssClass="uiDropdown searchDropdown"
						path="propertiesMap['role'].value">
						<form:option value=""></form:option>
						<form:options items="${roles }" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>Gender <form:select id="ddGender" cssStyle="width:60px;"
						cssClass="uiDropdown searchDropdown"
						path="propertiesMap['gender'].value">
						<form:option value=""></form:option>
						<form:options items="${genders }" itemLabel="name" />
					</form:select>
				</td>
				<td><input id="filter" type="submit" value="Search"
					name="btnFilter" /></td>
			</tr>
		</table>
	</div>


</fieldset>
