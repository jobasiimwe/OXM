

<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>

<%@ attribute type="org.agric.oxm.model.Blog" name="blogParam"
	required="true"%>

<%@ attribute type="java.lang.Boolean" name="adminView" required="true"%>
<%@ attribute type="java.lang.Boolean" name="showPreview"
	required="true"%>

<div class="box">
	<h3>${blogParam.title }</h3>

	<p>
		<springTags:eval
			expression="T(org.agric.oxm.model.util.MyDateUtil).getDisplayDate(blogParam.dateCreated)"
			var="displayDate" />
	</p>

	<span class="light-rounded-corners blog-date-time"
		title="<fmt:formatDate type="both"
					pattern="EEEEE, dd/MMM/yyyy, HH:mm a" value="${blogParam.dateCreated}" />">${displayDate
		}</span>

	<c:if test="${ loggedInUser.id  eq blogParam.createdBy.id }">
		<span style="float: right;"> <a id="editBlog"
			href="${baseUrl}/blog/edit/${blogParam.id}" class="uiButton">Edit</a>
			<a id="deleteBlog" href="${baseUrl}/blog/delete/${blogParam.id}"
			class="uiButton">Delete</a>
		</span>
	</c:if>


	<pre>
		<c:choose>
			<c:when test="${showPreview }">
				${fn:trim(blogParam.getPreText())}...
				<a href="${baseUrl }/blog/detail/${adminView}/${blogParam.id}" style="text-decoration: none; color: #33C131;" >Read More...</a>
			</c:when>
			<c:otherwise>
				${fn:trim(blogParam.text)}
			</c:otherwise>
		</c:choose>
	</pre>

	<span class="post-type">Posted By:- ${blogParam.createdBy.name
		}, </span>
</div>