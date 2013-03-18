<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>


<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:prices />
	form
</div>

<p>
	<label>Crop <span class="required">*</span> </label> <select
		id="ddCrops">
		<c:if test="${not empty crops  && fn:length(crops) > 0}">
			<c:forEach var="crop" items="${crops }">
				<option value="${crop.id }">${crop.name }</option>

			</c:forEach>
		</c:if>

	</select> <a id="lnkAddPrice" class="uiButton" href="${baseUrl }/price/add/">Add</a>
</p>

<div>
	<c:if test="${not empty price }">
		<form:form action="${baseUrl }/price/save/" commandName="price">

			<form:hidden path="id" />
			<div class="splitcontentleft">
				<div class="box ">
					<h3>Adding Price of ${price.crop.name }</h3>
					<form:hidden path="crop" />
					<table>
						<tr>
							<td><label>Selling Place <span class="required">*</span>
							</label></td>
							<td><form:select path="sellingPlace"
									cssStyle="width: 100px;" items="${sellingPlaces }"
									itemLabel="name" itemValue="id" /></td>
							<td><label>Date</label> <form:input path="date"
									cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox" />
							</td>
						</tr>
						<tr>
							<td><label>Selling Type</label></td>
							<td><form:select path="sellType" cssStyle="width: 100px;"
									items="${sellingTypes }" itemLabel="name" itemValue="id" /></td>
						</tr>
						<tr>
							<td><label>Quantity</label></td>
							<td><form:input path="quantity" cssStyle="width: 100px;"
									cssClass="uiTextbox" /></td>
							<td><label>Units</label> <form:select
									path="unitOfMeasure" items="${unitOfMeasures }"
									itemLabel="name" itemValue="id" /></td>
						</tr>
						<tr>
							<td><label>Price</label></td>
							<td><form:input path="price" cssStyle="width: 100px;"
									cssClass="uiTextbox" /></td>
						</tr>
					</table>
				</div>
			</div>
			<div style="clear: both"></div>
			<div>
				<input id="btnSavePrice" type="submit" value="Save" class="uiButton" />
			</div>
		</form:form>
	</c:if>
</div>