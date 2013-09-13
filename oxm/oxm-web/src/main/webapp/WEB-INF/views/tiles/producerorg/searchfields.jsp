<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="searchform group">
	<form:form action="${baseUrl}/porg/search" commandName="porgsearch">
		<fieldset>
			<legend>
				<span title="Search Producer Organisations">Search Producer Organisations</span>
			</legend>

			<div class="searchDiv">
				<ul>
					<li>
						<label>Name</label>
						<form:input id="txtName" cssClass="uiTextbox long" path="propertiesMap['name'].value" />
					</li>
					<li>
						<label>District</label>
						<form:select id="jsondistrict" path="propertiesMap['district'].value"
							cssClass="uiDropdown medium" items="${districts }" itemLabel="name" itemValue="id"
							preselectedItem="${pDistrict }" url="${baseUrl}/get/json/districts" child="jsoncounty"
							firstElementBlank="true"
						/>
					</li>
					<li>
						<label>County</label>
						<form:select id="jsoncounty" path="propertiesMap['county'].value" cssClass="uiDropdown medium"
							items="${counties }" itemLabel="name" itemValue="id" preselectedItem="${pCounty }"
							url="${baseUrl}/get/json/counties/" parent="jsondistrict" child="jsonsubcounty"
							firstElementBlank="true"
						/>
					</li>
					<li>
						<label>Sub County</label>
						<form:select id="jsonsubcounty" path="propertiesMap['subCounty'].value"
							cssClass="uiDropdown medium" items="${subcounties }" itemLabel="name" itemValue="id"
							preselectedItem="${pSubCounty }" url="${baseUrl}/get/json/subcounties/" parent="jsoncounty"
							child="jsonparish" firstElementBlank="true"
						></form:select>
					</li>
					<li>
						<label>Parish</label>
						<form:select id="jsonparish" path="propertiesMap['parish'].value" cssClass="uiDropdown medium"
							items="${parishes }" itemLabel="name" itemValue="id" preselectedItem="${pParish }"
							url="${baseUrl}/get/json/parishes/" parent="jsonsubcounty" child="jsonvillage"
							firstElementBlank="true"
						></form:select>
					</li>
					<li>
						<label>Village</label>
						<form:select id="jsonvillage" path="propertiesMap['village'].value"
							cssClass="uiDropdown medium" items="${villages }" itemLabel="name" itemValue="id"
							preselectedItem="${pVillage }" url="${baseUrl}/get/json/villages/" parent="jsonparish"
							firstElementBlank="true"
						></form:select>
					</li>
					<li class="button-li">
						<input id="filter" type="submit" value="Search" name="btnFilter" />
					</li>
				</ul>
			</div>

		</fieldset>
	</form:form>
</div>