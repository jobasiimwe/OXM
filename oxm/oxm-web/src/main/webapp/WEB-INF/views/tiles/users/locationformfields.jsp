
<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
<!--
.shortformfield {
	width: 100px;
}
-->
</style>


<div class="box">
	<h3>Location</h3>
	<table>
		<tr>
			<td><label>District </label>
			</td>
			<td><label>Sub County </label>
			</td>
			<td><label>Parish </label>
			</td>
			<td><label>Village </label>
			</td>
		</tr>
		<tr>
			<td><c:if test="${not empty preselectedDistrict }">
					<input type="hidden" id="preselectedDistrictId"
						value="${preselectedDistrictId }" />
				</c:if> <form:select id="dddistrictwithblank" path="district"
					cssClass="uiTextBox shortformfield" items="${districts }"
					itemLabel="name" itemValue="id" />
			</td>
			<td><form:select id="ddsubcountywithblank" path="subCounty"
					cssClass="uiTextBox shortformfield" items="${subcounties }"
					itemLabel="name"></form:select></td>
			<td><form:select id="ddparishwithblank" path="parish"
					cssClass="uiTextBox shortformfield" items="${parishes }"
					itemLabel="name"></form:select></td>
			<td><form:select id="ddvillagewithblank" path="village"
					cssClass="uiTextBox shortformfield" items="${villages }"
					itemLabel="name"></form:select></td>
		</tr>
	</table>

</div>
