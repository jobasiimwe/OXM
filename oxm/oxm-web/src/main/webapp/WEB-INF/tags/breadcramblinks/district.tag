
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ tag body-content="empty"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>
<%@ attribute type="java.lang.Boolean" name="isForm" required="false"%>

<%@ attribute type="org.agric.oxm.model.District" name="district" required="false"%>
<%@ attribute type="org.agric.oxm.model.County" name="county" required="false"%>
<%@ attribute type="org.agric.oxm.model.SubCounty" name="subCounty" required="false"%>
<%@ attribute type="org.agric.oxm.model.Parish" name="parish" required="false"%>

<c:if
	test="${name eq 'districts' or name eq 'counties' 
	or name eq 'subcounties' or name eq 'parishes' or name eq 'villages'}"
>
	<span class="arrow-right"></span>
	<span class="arrow-right"></span>

	<a title="Go to Control-panel" href="${baseUrl }/cpanel">C-panel</a>
	<span class="arrow-right"></span>

	<a title="Districts" href="${baseUrl }/district/view/">Districts</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'counties' 
	or name eq 'subcounties' or name eq 'parishes' or name eq 'villages' }">
	<a title="Counties in ${district.name}" href="${baseUrl }/county/view/${district.id}">${district.name }</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'subcounties' or name eq 'parishes' or name eq 'villages' }">
	<a title="Sub-Counties in ${county.name}" href="${baseUrl }/subcounty/view/${county.id}">${county.name }</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${ name eq 'parishes' or name eq 'villages' }">
	<a title="Parishes in ${subcounty.name}" href="${baseUrl }/parish/view/${subcounty.id}">${subcounty.name }</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'villages' }">
	<a title="Villages in ${parish.name}" href="${baseUrl }/village/view/${parish.id}">${parish.name }</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${isForm }">
	<b>Form</b>
</c:if>
