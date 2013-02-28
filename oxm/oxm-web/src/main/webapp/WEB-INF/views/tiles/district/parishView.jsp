<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div id="buttonStrip"></div>
<div>
	<div>
		<div id="breadcrumbs" style="margin: 5px;">
	District: >> ${subCounty.district.name } >> SubCounty : ${subCounty.name } >> <a href="${baseUrl }/subcounty/view/${subCounty.district.id }">Back</a>
</div>
<div class="contextual">
			<a class="uiButton" href="${baseUrl }/parish/add/${subCounty.id }">New
				Parish</a>
		</div>
		<div style="float: right; display: none;">
			<!--<form method="post" action="controlpanel?action=search&item=district&cid=${country.id }" style="display: inline-block">
				<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" /> <input
					type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
			</form>
		--></div>
		<div style="clear: both"></div>
	</div>
	<div><h4>parishes in ${subCounty.name } subCounty</h4></div>
	<table id="recordTable" class="recordTable list" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>Name</th>
				<th>SubCounty</th>
				<th></th>
			</tr>
			<c:choose>
				<c:when test="${not empty parishes  && fn:length(parishes) > 0}">
					<c:forEach var="parish" items="${parishes }">
						<tr>
							<td>${parish.name }</td>
							<td>${parish.subCounty.name }</td>
							<td><a id="btnDeleteParish" class="icon icon-delete"
								title="delete" onclick="" href="${baseUrl }/parish/delete/${parish.id }">Delete</a>
								<a class="icon icon-edit" title="edit" href="${baseUrl }/parish/edit/${parish.id }">Edit</a>
								<a class="icon icon-list" title="edit" href="${baseUrl }/village/view/${parish.id }">View Villages</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>

				</c:otherwise>
			</c:choose>
		</thead>
	</table>
	<div id="navigation" style="display: none;"></div>
</div>