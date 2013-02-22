<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="buttonStrip"></div>
<div>
	<div>
		<div id="buttonStrip">
			<div class="contextual" style="float: left;">
				<a id="lnkAddConcept" class="uiButton"
					href="${baseUrl }/category/add/">Add</a> 
			</div>
		</div>
		<div style="float: right">
			<form action="concept?action=search&item=conceptcategory"
				style="display: inline-block" method="post">
				<input type="text" class="uiTextbox" id="txtSearch" size="30"
					name="query" /> <input type="submit" class="uiButton"
					id="btnSearchSubmit" value="Search" />
			</form>
		</div>
		<div style="clear: both"></div>
	</div>
	<table id="recordTable" class="recordTable list" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th></th>
			</tr>
			<c:choose>
				<c:when
					test="${not empty conceptcategories  && fn:length(conceptcategories) > 0}">
					<c:forEach var="conceptcategory" items="${conceptcategories }">
						<tr>
							<td>${conceptcategory.name }</td>
							<td>${conceptcategory.description }</td>
							<!-- removed delete from display because user is not allowed to delete Concept Categories -->
							<td><a class="icon icon-edit" title="edit"
								href="${baseUrl }/category/edit/${conceptcategory.id }">Edit</a>
								<a class="icon icon-delete" style="margin-left: 5px;" title="delete"
								href="${baseUrl }/category/delete/${conceptcategory.id }">Delete</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</thead>
	</table>
	<div id="navigation">
		<c:if test="${numberOfPages > 0 }">
			<span>No of Concept Categories ${numberofItems }</span>
			<span style="float: right;">Page ${currentPage } of <c:out
					value="${numberOfPages }" />
			</span>
			<span style="float: right;"> <c:if test="${currentPage > 1 }">
					<a class="icon icon-back"
						href="concept?action=view&item=conceptcategories&pageNo=${currentPage -1 }"></a>
				</c:if> <c:if test="${currentPage < numberOfPages }">
					<a class="icon icon-forward"
						href="concept?action=view&item=conceptcategories&pageNo=${currentPage + 1 }"></a>
				</c:if> </span>
		</c:if>

	</div>
</div>