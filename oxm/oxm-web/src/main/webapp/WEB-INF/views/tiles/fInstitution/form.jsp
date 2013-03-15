<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:financialinstitutions />
	form
</div>


<div>
	<form:form action="${baseUrl }/fInstitution/save/"
		commandName="fInstitution">

		<form:hidden path="id" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<h3>Information</h3>
				<p>
					<label>Name</label>
					<form:input id="txtName" path="name" cssClass="uiTextbox" />
				</p>
				<p>
					<label>Address</label>
					<form:textarea path="address" cssClass="uiTextbox " />
				</p>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavefInstitution" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>