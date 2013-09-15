
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ tag body-content="empty"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>
<%@ attribute type="java.lang.Boolean" name="isForm" required="false"%>

<%@ attribute type="org.agric.oxm.model.Blog" name="blogParam" rtexprvalue="true" required="false"%>
<%@ attribute type="java.lang.Boolean" name="adminView" required="false"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrg" name="producerOrg" rtexprvalue="true" required="false"%>

<span class="arrow-right"></span>
<span class="arrow-right"></span>

<a title="Control-panel" href="${baseUrl }/cpanel">C-panel</a>
<span class="arrow-right"></span>


<c:if test="${name eq 'crop' }">
	<a href="${baseUrl }/crop/view">Crops</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'products' }">
	<a href="${baseUrl }/product/view">Products</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'finstitutions' }">
	<a href="${baseUrl }/finstitution/view">Financial Institutions</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'prices' }">
	<a href="${baseUrl }/price/view/admin/true">Prices</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'blogs' }">
	<a href="${baseUrl }/blog/view/${adminView}">Blogs</a>
	<span class="arrow-right"></span>

	<c:if test="${not empty blogParam }">
		<a href="${baseUrl }/blog/view/${adminView}/${blogParam.id}">Current Blog</a>
		<span class="arrow-right"></span>
	</c:if>
</c:if>

<c:if
	test="${name eq 'porgs' or name eq 'porgproducers' or name eq 'porgdetails' or name eq 'porgcommittees' or name eq 'porgdocs'}"
>

	<a href="${baseUrl }/porg/view">Producer Groups</a>
	<span class="arrow-right"></span>

	<c:if test="${name eq 'porgproducers' or name eq 'porgdetails' }">
		<a style="display: none;" href="${baseUrl }/porg/details/${producerOrg.id}">Details of - ${producerOrg.name }</a>
	</c:if>

	<c:if test="${name eq 'porgproducers'  }">
		<a href="${baseUrl }/porgproducers/${producerOrg.id}">Members of - ${producerOrg.name }</a>
		<span class="arrow-right"></span>
	</c:if>

	<c:if test="${name eq 'porgdocs'  }">
		<a href="${baseUrl }/porgdocs/view/${producerOrg.id}">Documents of - ${producerOrg.name }</a>
		<span class="arrow-right"></span>
	</c:if>

</c:if>

<c:if test="${name eq 'seasons' }">
	<a href="${baseUrl }/season/view">Seasons</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${name eq 'concepts' or name eq 'conceptcategories' }">

	<a title="Categories of System Terms (Concepts)" href="${baseUrl }/category/view">Concept Categories</a>
	<span class="arrow-right"></span>

	<a title="List of System Terms (Concepts)" href="${baseUrl }/concept/view">System terms</a>
	<span class="arrow-right"></span>
</c:if>

<c:if test="${isForm }">
	<b>Form</b>
</c:if>