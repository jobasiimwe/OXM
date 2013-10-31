
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ tag body-content="empty"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>
<%@ attribute type="java.lang.Boolean" name="isForm" required="false"%>

<%@ attribute type="org.agric.oxm.model.Blog" name="blogParam"
	rtexprvalue="true" required="false"%>
<%@ attribute type="java.lang.Boolean" name="adminView" required="false"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrg" name="porgParam"
	rtexprvalue="true" required="false"%>
<%@ attribute type="org.agric.oxm.model.Committee" name="committeeParam"
	rtexprvalue="true" required="false"%>
<%@ attribute type="org.agric.oxm.model.FinancialInstitution"
	name="finstParam" rtexprvalue="true" required="false"%>
<%@ attribute type="org.agric.oxm.model.Season" name="seasonParam"
	rtexprvalue="true" required="false"%>

<span class="arrow-right"></span>
<span class="arrow-right"></span>

<a title="Control-panel" href="${baseUrl }/cpanel">C-panel</a>
<span class="arrow-right"></span>


<c:if test="${name eq 'crops' }">
	<a href="${baseUrl }/crop/view">Crops</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'products' }">
	<a href="${baseUrl }/product/view">Products</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'finstitutions' or name eq 'finstitutiondocs'}">
	<a href="${baseUrl }/finstitution/view">Financial Institutions</a>
	<span class="arrow-right"></span>

	<c:if test="${name eq 'finstitutiondocs'  }">
		<a href="${baseUrl }/finstitutiondocs/view/${finstParam.id}">Documents
			of - ${finstParam.name }</a>
		<span class="arrow-right"></span>
	</c:if>
</c:if>

<c:if test="${name eq 'prices' }">
	<a href="${baseUrl }/price/view/admin/true">Prices</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'blogs' }">
	<a href="${baseUrl }/blog/view/${adminView}">Blogs</a>
	<span class="arrow-right"></span>

	<c:if test="${not empty blogParam }">
		<a href="${baseUrl }/blog/view/${adminView}/${blogParam.id}">Current
			Blog</a>
		<span class="arrow-right"></span>
	</c:if>
</c:if>

<c:if test="${name eq 'seasons' or name eq 'seasondocs' }">
	<a href="${baseUrl }/season/view">Seasons</a>
	<span class="arrow-right"></span>

	<c:if test="${name eq 'seasondocs'  }">
		<a href="${baseUrl }/seasondocs/view/${seasonParam.id}">Documents
			of - ${seasonParam.name }</a>
		<span class="arrow-right"></span>
	</c:if>

</c:if>

<c:if test="${name eq 'concepts' or name eq 'conceptcategories' }">

	<a title="Categories of System Terms (Concepts)"
		href="${baseUrl }/category/view">Concept Categories</a>
	<span class="arrow-right"></span>

	<a title="List of System Terms (Concepts)"
		href="${baseUrl }/concept/view">System terms</a>
	<span class="arrow-right"></span>
</c:if>


<c:if test="${name eq 'positions' }">
	<a href="${baseUrl }/position/view">Positions</a>
	<span class="arrow-right"></span>
</c:if>


<c:if test="${name eq 'users' or name eq 'sms'}">
	<a href="${baseUrl }/user/view">Users</a>
	<span class="arrow-right"></span>

	<c:if test="${name eq 'sms'  }">
		<a href="${baseUrl }/user/sms">SEND SMS</a>
		<span class="arrow-right"></span>
	</c:if>
</c:if>

<c:if test="${name eq 'roles' }">
	<a href="${baseUrl }/role/view">Roles</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${isForm }">
	<b>Form</b>
</c:if>