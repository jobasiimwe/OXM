<%@page language="java" isELIgnored="false" contentType="text/html"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div id="buttonStrip"></div>
<div>
	<div>
		<div class="contextual">
			<a class="uiButton" href="role?action=add&item=role">New Role</a>
		</div>
		<div style="clear: both"></div>
	</div>
	<table id="recordTable" class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th></th>
			</tr>
			<c:choose>
				<c:when test="${not empty roles  && fn:length(roles) > 0}">
					<c:forEach var="role" items="${roles }">
						<tr>
							<td>${role.name }</td>
							<td>${role.description }</td>
							<td>
								<a id="btnDeleteRole" class="icon icon-delete" title="delete" href="role?action=delete&item=role&id=${role.id }">Delete</a>
								<a class="icon icon-edit " title="edit" href="role?action=edit&item=role&id=${role.id }">Edit</a>
							</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
		</thead>
	</table>
</div>