<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div id="buttonStrip"></div>
<div>
	<div>
		<div class="contextual">
			<a class="uiButton" href="${baseUrl }/district/add/">New
				District</a>
		</div>
		<div style="float: right">
			<form method="post" action="${baseUrl }/district/search/" style="display: inline-block">
				<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" /> <input
					type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
			</form>
		</div>
		<div style="clear: both"></div>
	</div>
	<div><h4>Districts</h4></div>
	<table id="recordTable" class="recordTable list" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>Name</th>
				<th></th>
			</tr>
			<c:choose>
				<c:when test="${not empty districts  && fn:length(districts) > 0}">
					<c:forEach var="district" items="${districts }">
						<tr>
							<td>${district.name }</td>
							<td><a id="btnDeleteDistrict" class="icon icon-delete"
								title="delete" onclick="" href="${baseUrl }/district/delete/${district.id }">Delete</a>
								<a class="icon icon-edit" title="edit"
								href="${baseUrl }/district/edit/${district.id }">Edit</a>
								<a class="icon icon-list" title="view subcounty"
								href="${baseUrl }/subcounty/view/${district.id }">View Sub Counties</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</thead>
	</table>
	<!--<div id="navigation">
		<c:if test="${numberOfPages > 0 }">
			<span>No of District ${numberofItems }</span> 
			<span style="float:right;">Page ${currentPage } of <c:out value="${numberOfPages }" /></span>
			<span style="float:right;">
				<c:if test="${currentPage > 1 }">
					<a class="icon icon-back" href="controlpanel?action=view&item=district&pageNo=${currentPage -1 }"></a>				
				</c:if>
				
				<c:if test="${currentPage < numberOfPages }">
					<a class="icon icon-forward" href="controlpanel?action=view&item=district&pageNo=${currentPage + 1 }"></a>
				</c:if>
			</span>
		</c:if>
		
	</div>
--></div>