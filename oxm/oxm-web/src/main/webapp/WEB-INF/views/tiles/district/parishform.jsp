<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:district name="parishes" district="${subCounty.county.district }" county="${subCounty.county }"
		subCounty="${subCounty }" isForm="true"
	/>
</div>


<div>
	<form:form action="${baseUrl }/parish/save" commandName="parish">
		<form:hidden path="id" />
		<form:hidden path="subCounty" />
		<div>
			<div class="box tabular">
				<p>
					<label>
						Parish Name
						<span class="required">*</span>
					</label>
					<form:input id="txtName" path="name" cssClass="uiTextbox" />
				</p>
			</div>
		</div>
		<div>
			<input id="btnSaveParish" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>

</div>