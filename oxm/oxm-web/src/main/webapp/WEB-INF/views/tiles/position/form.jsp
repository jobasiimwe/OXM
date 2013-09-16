<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel name="positions" isForm="true" />
</div>

<div>
	<form:form action="${baseUrl }/position/save/" commandName="position">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box">
				<table>
					<tr>
						<td><label>No. <span class="required">*</span>
						</label></td>
						<td><form:input path="index" cssClass="uiTextbox" /></td>
					<tr>
						<td><label>Name <span class="required">*</span>
						</label></td>
						<td><form:input path="name" cssClass="uiTextbox" /></td>
				</table>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePosition" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>