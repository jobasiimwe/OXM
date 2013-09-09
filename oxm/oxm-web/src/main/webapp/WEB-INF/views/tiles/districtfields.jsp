
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="searchDiv">
	<ul>
		<li>
			<label>District</label>
			<form:select id="jsondistrict" path="district" cssClass="uiDropdown medium"
				items="${districts }" itemLabel="name" itemValue="id" preselectedItem="${pDistrict }"
				url="${baseUrl}/get/json/districts" child="jsoncounty" firstElementBlank="true"
			/>
		</li>
		<li>
			<label>County</label>
			<form:select id="jsoncounty" path="county" cssClass="uiDropdown medium"
				items="${counties }" itemLabel="name" itemValue="id" preselectedItem="${pCounty }"
				url="${baseUrl}/get/json/counties/" parent="jsondistrict" child="jsonsubcounty"
				firstElementBlank="true"
			/>
		</li>
		<li>
			<label>Sub County</label>
			<form:select id="jsonsubcounty" path="subCounty" cssClass="uiDropdown medium"
				items="${subcounties }" itemLabel="name" itemValue="id" preselectedItem="${pSubCounty }"
				url="${baseUrl}/get/json/subcounties/" parent="jsoncounty" child="jsonparish"
				firstElementBlank="true"
			></form:select>
		</li>
		<li>
			<label>Parish</label>
			<form:select id="jsonparish" path="parish" cssClass="uiDropdown medium"
				items="${parishes }" itemLabel="name" itemValue="id" preselectedItem="${pParish }"
				url="${baseUrl}/get/json/parishes/" parent="jsonsubcounty" child="jsonvillage"
				firstElementBlank="true"
			></form:select>
		</li>
		<li>
			<label>Village</label>
			<form:select id="jsonvillage" path="village" cssClass="uiDropdown medium"
				items="${villages }" itemLabel="name" itemValue="id" preselectedItem="${pVillage }"
				url="${baseUrl}/get/json/villages/" parent="jsonparish" firstElementBlank="true"
			></form:select>
		</li>
	</ul>
</div>