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

.searchDiv { /*width: 100%;*/
	/*display: inline-block;*/
	
}

.searchTextbox { /*width: 180px;*/
	/*border: 1px solid #a4c3ca;*/
	
}

.searchDropdown {
	/*box-shadow: 0 1px 3px rgba(0, 0, 0, 0.25) inset, 0 1px 0 rgba(255, 255, 255, 1); 
border: 1px solid #a4c3ca;*/
	
}

.tabular ul li {
	display: inline-table;
	width: 10px;
}
-->
</style>

<div class="searchform group">
	<form:form action="${baseUrl}/crop/search"
		commandName="cropsearch">
		<fieldset>
			<legend>
				<a id="search-collapser" href="javascript:void(0)"
					title="Search Panel">Search Panel</a>
			</legend>



			<div style="float: left;">
										<table>
							<tr>
								<td>Code/Name <form:input
										cssClass="uiTextbox searchTextbox"
										path="propertiesMap['codeorname'].value" /></td>
								<td>Dept <form:select id="dd"
										cssClass="uiDropdown searchDropdown"
										path="propertiesMap['department'].value">
										<form:option value=""></form:option>
										<form:options items="${departments }" itemLabel="abbreviation"
											itemValue="id" />
									</form:select></td>
								<td>Type <form:select id="dd2"
										cssClass="uiDropdown searchDropdown"
										path="propertiesMap['coursetype'].value">
										<form:option value=""></form:option>
										<form:options items="${courseTypes }" itemLabel="name"
											itemValue="id" />
									</form:select></td>
								<td><form:checkbox id="cbxUserItemsOnly"
										path="propertiesMap['useritemsonly'].booleanValue"
										label="My Courses Only"
										title="Check to load only courses in allocated to you" /></td>
								<td><input id="filter" type="submit" value="Search"
									name="btnFilter" />
								</td>
							</tr>
						</table>
					</div>
				

		</fieldset>
	</form:form>
</div>