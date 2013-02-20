<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<div>
	<div style="float: left">
		<form:form method="post" action="concept?action=view&item=concepts" commandName="conceptCategory" id="conceptCategoryForm">
			<label class="uiLabel">Concept in Category:</label>
			<form:select id="category" path="id" cssClass="uiDropdown" itemLabel="name" itemValue="id" items="${conceptcategories }"/>
			<input type="submit" class="uiButton" id="btnLoadConceptCategoryConcepts" value="Load" />
		</form:form>				
	</div>
	<div style="float: right">
		<form method="post" action="concept?action=search&item=concept" style="display: inline-block">
			<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" value="${query }" /> <input
				type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
		</form>
	</div>
	<div style="clear: both"></div>
</div>
<div id="buttonStrip">
	<div class="contextual" style="float:left;">
		<a id="lnkAddConcept"  class="uiButton" href="concept?action=add&item=concept">Add</a>
		<a id="lnkEditConcept" class="uiButton" href="concept?action=edit&item=concept">Edit</a>
		<a id="lnkDeleteConcept" class="uiButton" href="concept?action=delete&item=concept">Delete</a>
	</div>
	<div style="float:right;">
		<%@ include file="/WEB-INF/views/navigation.jsp" %>
	</div>
	<div style="clear: both"></div>
</div>
<div>
	<table class="recordTable" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" /></th>
				<th>Name</th>
				<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty concepts  && fn:length(concepts) > 0}">
					<c:forEach var="concept" items="${concepts }">
						<tr id="${concept.id }">
							<td><input type="checkbox" name="selectedConcepts" value="${concept.id }" /></td>
							<td>${concept.name }</td>
							<td>${concept.description }</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</div>