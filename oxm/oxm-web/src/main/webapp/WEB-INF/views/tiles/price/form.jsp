<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
form#concept input[type="text"] {
	width: 70%;
}

#conceptcategorylist span {
	display: inline-block;
	width: 50%;
}
</style>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>


<div style="margin: 5px; width: 100%;">
	<label class="uiLabel">You are here >> </label>
	<oxmBreadcrambs:cpanel />
	>
	<oxmBreadcrambs:prices />
	> form
</div>

<p>
	<label>Crop <span class="required">*</span> </label>
	<select id="ddCrops">
		<c:if test="${not empty crops  && fn:length(crops) > 0}">
					<c:forEach var="crop" items="${crops }">
					<option value="${crop.id }">${crop.name }</option>
						
					</c:forEach>
				</c:if>
		
		</select>
	<a id="lnkAddPrice" class="uiButton" href="${baseUrl }/price/add/">Add</a>
</p>

<div>
	<c:if test="${not empty price }">
		<form:form action="${baseUrl }/price/save/" commandName="price">

			<form:hidden path="id" />
			<div class="splitcontentleft">
				<div class="box tabular">
					<h3>Adding Price of ${price.crop.name }</h3>
					<form:hidden path="crop" />
					<p>
						<label>Selling Place <span class="required">*</span> </label>
						<form:select path="sellingPlace" items="${sellingPlaces }"
							itemLabel="name" itemValue="id" />
					</p>
					<p>
						<label>Selling Type</label>
						<form:select path="sellType" items="${sellingTypes }"
							itemLabel="name" itemValue="id" />
					</p>
					<p>
						<label>Unit of Measure</label>
						<form:select path="unitOfMeasure" items="${unitOfMeasures }"
							itemLabel="name" itemValue="id" />
					</p>
					<p>
						<label>Price</label>
						<form:input path="price" cssClass="uiTextbox" />
					</p>
					<p>
						<label>Date</label>
						<form:input path="date" cssClass="uiTextbox uiDateTextbox" />
					</p>
					<p>
						<label>Quantity</label>
						<form:input path="quantity" cssClass="uiTextbox" />
					</p>
				</div>
			</div>
			<div style="clear: both"></div>
			<div>
				<input id="btnSavePrice" type="submit" value="Save" class="uiButton" />
			</div>
		</form:form>
	</c:if>
</div>