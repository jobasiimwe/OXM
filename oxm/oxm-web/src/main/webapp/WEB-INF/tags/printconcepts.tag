
<%@ tag body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ attribute type="java.util.List" name="concepts" required="true"%>
<%@ attribute name="listHeader" required="true"%>

<div class="box">
	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
		<caption>
			<b>${listHeader }</b>
		</caption>
		<thead>
			<tr>
				<th><input type="checkbox" name="cbxSelectAllItems"
					id="cbxSelectAllItems" />
				</th>
				<th>Name</th>
				<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${not empty concepts  && fn:length(concepts) > 0}">
					<c:forEach var="concept" items="${concepts }">
						<tr id="${concept.id }">
							<td><input type="checkbox" name="selectedConcepts"
								value="${concept.id }" />
							</td>
							<td>${concept.name }</td>
							<td>${concept.description }</td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="3"><span style="color: red;">none found</span></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>