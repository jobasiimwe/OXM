<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="breadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%; display: none;">
	<breadcrambs:prelogin name="finstitutions" />
</div>

<h3>You can get Financial Services from :-</h3>

<table class="sysTable">
	<thead>
		<tr>
			<th>No.</th>
			<th>Name</th>
			<th>Address</th>
			<th>Available-Documents</th>
		</tr>
	</thead>

	<jsp:include page="/WEB-INF/views/tiles/finstitution/tablerows.jsp"></jsp:include>

</table>

