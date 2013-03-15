
<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="startingBreadcramb" required="true"%>


<c:if test="${startingBreadcramb }">
	<label class="uiLabel">You are here &gt;&gt; </label>
</c:if>

<a title="Go to Control Panel" href="${baseUrl }/cpanel">C-panel</a>
&gt;
