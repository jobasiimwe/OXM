<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>


<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmDistrictBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/districts"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmDistrictBreadcrambs:districts />
	<oxmDistrictBreadcrambs:counties district="${district }" />
	form
</div>

<div>
	<form:form action="${baseUrl }/county/save" commandName="county">
		<form:hidden path="id" />
		<form:hidden path="district" />
		<div class="box tabular">
			<p>
				<label>County Name <span class="required">*</span>
				</label>
				<form:input id="txtName" path="name" cssClass="uiTextbox" />
			</p>
		</div>
		<div>
			<input id="btnSaveCounty" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>

</div>