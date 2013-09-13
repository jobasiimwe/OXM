<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="seasons" isForm="true" />
</div>

<div>
	<form:form action="${baseUrl }/season/save" commandName="season">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<p>
					<label>Start Date</label>
					<form:input path="startDate" cssClass="uiTextbox medium uiDateTextbox" />
				</p>
				<p>
					<label>End Date</label>
					<form:input path="endDate" cssClass="uiTextbox medium uiDateTextbox" />
				</p>
				<p>
					<label>Weather Category</label>
					<form:select id="ddWeather" path="weather">
						<form:option value="">--</form:option>
						<form:options itemLabel="name" items="${weathers }" />
					</form:select>
				</p>
				<p>
					<label>Description</label>
					<form:textarea id="txtAreaDescription" path="weatherDescription" cols="50" rows="3" maxlength="32768" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveSeason" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>