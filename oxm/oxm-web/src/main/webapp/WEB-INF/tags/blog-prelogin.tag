

<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>

<%@ attribute type="org.agric.oxm.model.Blog" name="blog" required="true"%>

<%@ attribute type="java.lang.Boolean" name="showPreview" required="true"%>

<div class="box">
	<h3>${blog.title }</h3>

	<springTags:eval expression="T(org.agric.oxm.model.util.MyDateUtil).getDisplayDate(blog.dateCreated)" var="displayDate" />

	<span class="light-rounded-corners blog-date-time"
		title="<fmt:formatDate type="both"
					pattern="EEEEE, dd/MMM/yyyy, HH:mm a" value="${blog.dateCreated}" />"
	>${displayDate }</span>

	<div style="clear: both;"></div>

	<div class="blog-text">
		<c:choose>
			<c:when test="${showPreview }">
				${fn:trim(blog.getPreText())}...
				<a href="${baseUrl }/prelogin/blogs/${blog.id}">More...</a>
			</c:when>
			<c:otherwise>

				${fn:trim(blog.text)}

			</c:otherwise>
		</c:choose>
	</div>
	<div style="clear: both;"></div>

	<span class="post-type">Posted By:- ${blog.createdBy.name }, </span>
</div>