
<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ attribute type="org.agric.oxm.model.Post" name="post" required="true"%>

<div class="box">
	<span class="light-rounded-corners post-owner-name">${post.owner.name } </span>
	<span class="light-rounded-corners post-type">
		${post.type.name }
		<c:if test="${ not empty post.crop }">${post.crop.name }</c:if>
	</span>
	<span class="light-rounded-corners post-date-time"
		title="<fmt:formatDate type="both"
					pattern="EEEEE, dd/MMM/yyyy, HH:mm a" value="${post.datePosted}" />"
	>${post.displayDate }</span>
	<div>${post.text }</div>

</div>