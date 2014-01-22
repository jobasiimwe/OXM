<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${baseUrl}/prelogin/prices/search" commandName="pricesearch">
	<fieldset>
		<legend>
			<span title="Search Prices">Search Prices</span>
		</legend>

		<form:hidden path="propertiesMap['adminview'].booleanValue" />
		<div class="searchDiv">
			<ul>
				<li>
					<label>Product</label>
					<form:select id="ddProducts" cssStyle="width: 100px;" cssClass="uiDropdown"
						path="propertiesMap['product'].value"
					>
						<form:option value=""></form:option>
						<form:options items="${products }" itemLabel="name" itemValue="id" />
					</form:select>
				</li>
				<li>
					<label>Place</label>
					<form:select id="ddSellingPlace" cssStyle="width: 100px;" cssClass="uiDropdown"
						path="propertiesMap['sellingplaceid'].value"
					>
						<form:option value=""></form:option>
						<form:options items="${sellingPlaces }" itemLabel="name" itemValue="id" />
					</form:select>
				</li>
				<li>
					<label>From</label>
					<form:input id="fromdate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox"
						path="propertiesMap['fromdate'].value"
					/>
				</li>
				<li>
					<label>To</label>
					<form:input id="todate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox"
						path="propertiesMap['todate'].value"
					/>
				</li>
				<li class="button-li">
					<input id="filter" type="submit" value="Search" name="btnFilter" />
				</li>
		</div>

	</fieldset>
</form:form>
