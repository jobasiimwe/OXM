<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel-porg name="porgcommittees" porgParam="${pOrg }" isForm="true" />
</div>

<div>

	<form:form action="${baseUrl }/porg-committee/save/" commandName="committee">

		<form:hidden path="id" />
		<form:hidden path="producerOrg" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<table>
					<tr>
						<td><label>Name <span class="required">*</span>
						</label></td>
						<td><form:input id="txtName" path="name" cssClass="uiTextbox" /></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrgCommittee" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>