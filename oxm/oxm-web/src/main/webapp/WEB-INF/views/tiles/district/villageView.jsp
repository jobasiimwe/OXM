<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div id="buttonStrip"></div>
<div>
	<div>
		<div id="breadcrumbs" style="margin: 5px;">
	District: >> ${parish.subCounty.district.name } >> SubCounty : ${parish.subCounty.name } >> Parish : ${parish.name } >> <a href="${baseUrl }/parish/view/${parish.subCounty.id }">Back</a>
</div>
<div class="contextual">
			<a class="uiButton" href="${baseUrl }/village/add/${parish.id }">New
				Village</a>
		</div>
		<div style="float: right; display: none;">
			<!--<form method="post" action="controlpanel?action=search&item=district&cid=${country.id }" style="display: inline-block">
				<input type="text" class="uiTextbox" id="txtSearch" size="30" name="query" /> <input
					type="submit" class="uiButton" id="btnSearchSubmit" value="Search" />
			</form>
		--></div>
		<div style="clear: both"></div>
	</div>
	<div><h4>Villages in ${parish.name } parish</h4></div>
	<table id="recordTable" class="recordTable list" width="100%"
		cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>Name</th>
				<th>Parish</th>
				<th></th>
			</tr>
			<c:choose>
				<c:when test="${not empty villages  && fn:length(villages) > 0}">
					<c:forEach var="village" items="${villages }">
						<tr>
							<td>${village.name }</td>
							<td>${village.parish.name }</td>
							<td><a id="btnDeleteParish" class="icon icon-delete"
								title="delete" onclick="" href="${baseUrl }/village/delete/${village.id }">Delete</a>
								<a class="icon icon-edit" title="edit" href="${baseUrl }/village/edit/${village.id }">Edit</a>
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