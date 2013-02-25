<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>


<style>
form#district input[type="text"] {
	width: 70%;
}
</style>

<div id="breadcrumbs" style="margin: 5px;">
	${district.name } >> <a href="${baseUrl }/subcounty/view/${district.id }"> Back</a>
</div>
<div>
	<form:form action="${baseUrl }/subcounty/save/${district.id }" commandName="subcounty">
		<form:hidden path="id" />
		<form:hidden path="district"/>
		<div>
			<div class="box tabular">
				<h3>New SubCounty in District - ${district.name }</h3>
				<p>
					<label>Name <span class="required">*</span> </label>
					<form:input path="name" cssClass="uiTextbox"/>
				</p><!--
				<c:if test="${not empty countries  && fn:length(countries) > 0}">
					<p>
						<label>Country <span class="required">*</span> </label>
						<form:select items="${countries }" itemLabel="name" itemValue="id" path="country" cssClass="uiTextbox"/>
					</p>
				</c:if>
			--></div>
		</div>
		<div>
			<input id="btnSaveSubCounty" type="submit" value="Save" class="uiButton"/>
		</div>
	</form:form>
	
</div>