<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:seasons />
	form
</div>

<div>
	<form:form action="${baseUrl }/season/save/" commandName="season">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<p>
					<label>Start Date</label>
					<form:input path="startDate" cssClass="uiTextbox uiDateTextbox" />
				</p>
				<p>
					<label>End Date</label>
					<form:input path="endDate" cssClass="uiTextbox uiDateTextbox" />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveSeason" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>