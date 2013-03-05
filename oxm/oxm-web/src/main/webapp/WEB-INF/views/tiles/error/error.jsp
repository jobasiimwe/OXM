<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<style>
table.recordTable tr td {
	border-top: 1px solid #EEEEEE;
}

table#recordTable tr {
	cursor: pointer;
}

tr.selectedRow td {
	background-color: lightblue;
	color: white;
}
</style>
<script type="text/javascript">
	$('.uiButton').button();
</script>

<div id="navcontainer" style="width: 100px;">
	<a id="btnHome" class="uiButton" href="${baseUrl }/">Home</a>
</div>