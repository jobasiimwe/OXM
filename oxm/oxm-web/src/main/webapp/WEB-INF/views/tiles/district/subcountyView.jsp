<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div id="buttonStrip"></div>
<div>
	<div style="margin: 5px"> Districts >> <a href="${baseUrl }/district/view/">Back </a></div>
	<div>
		<div class="contextual">
			<a class="uiButton" href="${baseUrl }/subcounty/add/${district.id }">New
				Sub County</a>
		</div>
		<div style="float: right; display: none;">
			<!--<form method="post" action="controlpanel?action=search&item=district&cid=${country.id }" style="display: inline-block">
				<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" /> <input
					type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
			</form>
		--></div>
		<div style="clear: both"></div>
	</div>
	<div><h4>SubCounties in ${district.name } district</h4></div>
	<table id="recordTable" class="recordTable list" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>Name</th>
				<th>District</th>
				<th></th>
			</tr>
			<c:choose>
				<c:when test="${not empty subcounties  && fn:length(subcounties) > 0}">
					<c:forEach var="subcounty" items="${subcounties }">
						<tr>
							<td>${subcounty.name }</td>
							<td>${subcounty.district.name }</td>
							<td><a id="btnDeleteSubcounty" class="icon icon-delete"
								title="delete" onclick="" href="${baseUrl }/subcounty/delete/${subcounty.id }">Delete</a>
								<a class="icon icon-edit" title="edit" href="${baseUrl }/subcounty/edit/${subcounty.id }">Edit</a>
								<a class="icon icon-list" title="edit" href="${baseUrl }/parish/view/${subcounty.id }">View Parishes</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</thead>
	</table>
	<div id="navigation" style="display: none;">
		<!--<c:if test="${numberOfPages > 0 }">
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
		
	--></div>
</div>