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
	<!--<div style="width:450px; height: 60px; float: left; background-color: #C60A11; margin-left: 25%;">
		<span class="error-icon"></span>
		<div style="margin: 20px 0px 0px 50px; font-size: 15px; color: #FFF;">
			<c:if test="${not empty errorMsg }">
				<c:out value="${errorMsg }"></c:out>
			</c:if>
		</div>
			
	</div>-->