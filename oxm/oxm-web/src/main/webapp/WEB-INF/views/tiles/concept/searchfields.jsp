<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div class="searchform group">
	<form:form action="${baseUrl}/concept/search"
		commandName="conceptsearch">
		<fieldset>
			<legend>
				<a id="search-collapser" href="javascript:void(0)"
					title="Search Panel">Search Panel</a>
			</legend>

			<div style="float: left;">
				<table>
					<tr>
						<td>Name/Descreption <form:input
								cssClass="uiTextbox searchTextbox"
								path="propertiesMap['query'].value" />
						</td>
						<td>Category <form:select id="dd"
								cssClass="uiDropdown"
								path="propertiesMap['categoryid'].value">
								<form:option value=""></form:option>
								<form:options items="${conceptcategories }" itemLabel="name"
									itemValue="id" />
							</form:select>
						</td>
						<td><input id="filter" type="submit" value="Search"
							name="btnFilter" /></td>
					</tr>
				</table>
			</div>

		</fieldset>
	</form:form>
</div>