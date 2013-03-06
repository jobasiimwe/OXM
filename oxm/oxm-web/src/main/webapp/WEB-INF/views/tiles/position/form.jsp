<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<style>
form#concept input[type="text"] {
	width: 70%;
}

#conceptcategorylist span {
	display: inline-block;
	width: 50%;
}
</style>
<div>
	<div style="margin: 5px; width: 100%;">
		<label class="uiLabel">You are here > </label>
		<oxmBreadcrambs:cpanel />
		>
		<oxmBreadcrambs:positionlist />
		> form
	</div>

	<form:form action="${baseUrl }/position/save/" commandName="position">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box">
				<table>
					<tr>
						<td><label>No. <span class="required">*</span> </label>
						</td>
						<td><form:input path="index" cssClass="uiTextbox" />
						</td>
					<tr>
						<td><label>Name <span class="required">*</span> </label>
						</td>
						<td><form:input path="name" cssClass="uiTextbox" />
						</td>
				</table>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePosition" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>