

<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="springTags" uri="http://www.springframework.org/tags"%>

<%@ attribute type="org.agric.oxm.model.Blog" name="blog"
	required="true"%>

<%@ attribute type="java.lang.Boolean" name="showPreview"
	required="true"%>

<div class="box">
	<h3>${blog.title }</h3>

	<p>
		<springTags:eval
			expression="T(org.agric.oxm.model.util.MyDateUtil).getDisplayDate(blog.dateCreated)"
			var="displayDate" />
	</p>

	<span class="light-rounded-corners blog-date-time"
		title="<fmt:formatDate type="both"
					pattern="EEEEE, dd/MMM/yyyy, HH:mm a" value="${blog.dateCreated}" />">${displayDate
		}</span>



	<pre>
		<c:choose>
			<c:when test="${showPreview }">
				${blog.getPreText()}...
				<a href="${baseUrl }/prelogin/blogs/${blog.id}" style="text-decoration: none; color: #33C131;" >Read More...</a>
			</c:when>
			<c:otherwise>
				${blog.text}
			</c:otherwise>
		</c:choose>
	</pre>

	<span class="post-type">Posted By:- ${blog.createdBy.name }, </span>
</div>