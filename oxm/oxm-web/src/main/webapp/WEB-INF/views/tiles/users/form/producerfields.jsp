
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div style="border-style: solid; border-width: 5px; display: block;">
	<div class="tabular">
		<p>
			<label>Producer Org. </label>
			<form:select id="ddProducerOrg" path="producerOrg" class="uiDropdown meduim">
				<form:option value="none">--</form:option>
				<form:options items="${producerOrgs }" itemLabel="name" itemValue="id"></form:options>
			</form:select>
			<span>
				<label>House Hold. </label>
				<form:select id="ddHH" path="houseHoldCategory" class="uiDropdown short">
					<form:option value="">--</form:option>
					<form:options items="${houseHoldCategories }" itemLabel="name"></form:options>
				</form:select>
			</span>
		</p>
		<p>
			<label>Date of Joining </label>
			<form:input id="txtDateOfJoining" path="dateOfJoining" class="uiDropdown medium" />
			<span>
				<label>Year of Joining </label>
				<form:input id="txtYearOfJoining" path="yearOfJoining" class="uiDropdown short" />
			</span>
		</p>
		<p>
			<label>Land Size </label>
			<form:input id="txtLandSize" path="landSize" class="uiDropdown short" />
			<span>
				<label>GIS Co-ordinates </label>
				<!--<form:input id="txtGisCordinates" path="gisCordinates" class="uiDropdown short" />-->
			</span>
		</p>
	</div>

</div>