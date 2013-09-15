<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="rmsTags" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:district name="districts" />
	import
</div>

<div>
	<form:form method="POST" action="${baseUrl }/import/post" enctype="multipart/form-data"
		commandName="districtProducerImportData"
	>
		<p>
			<label>Item to Import:</label>
			<form:select id="ddImportItem" path="importItem" itemLabel="name" items="${items}">
			</form:select>
		</p>
		<p>Browse for a file containing Items to import!!</p>
		<p>
			<label>File:</label>
			<input type="file" id="file" name="file" />
			<input type="submit" id="upload" value="Import" />
		</p>

	</form:form>

</div>