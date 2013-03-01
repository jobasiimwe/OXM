
<%@ tag body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ attribute type="org.agric.oxm.model.ConceptCategory"
	name="conceptCategory" required="true"%>

<div class="box">
	<table class="recordTable" width="100%" cellpadding="0" cellspacing="0">
	<caption><b>${conceptCategory.description }</b></caption>
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
				<c:when test="${not empty conceptCategory.concepts  && fn:length(conceptCategory.concepts) > 0}">
					<c:forEach var="concept" items="${conceptCategory.concepts }">
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
				<tr><td colspan="3"><span style="color: red;">none found</span></td>
						</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>