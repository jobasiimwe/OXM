
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ tag body-content="empty"%>

<%@ attribute type="java.lang.String" name="name" required="true"%>
<%@ attribute type="java.lang.Boolean" name="isForm" required="false"%>

<%@ attribute type="java.lang.Boolean" name="adminView" required="false"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrg" name="porgParam" rtexprvalue="true" required="false"%>
<%@ attribute type="org.agric.oxm.model.Committee" name="committeeParam" rtexprvalue="true" required="false"%>
<%@ attribute type="org.agric.oxm.model.FinancialInstitution" name="finstParam" rtexprvalue="true" required="false"%>
<%@ attribute type="org.agric.oxm.model.Season" name="seasonParam" rtexprvalue="true" required="false"%>

<span class="arrow-right"></span>
<span class="arrow-right"></span>

<a title="Control-panel" href="${baseUrl }/cpanel">C-panel</a>
<span class="arrow-right"></span>

<c:if
	test="${name eq 'porgs' or name eq 'porgproducers' or name eq 'porgdetails' 
	or name eq 'porgcommittees' or name eq 'porgcommittee-members' 
	or name eq 'porgdocs'}">

	<a href="${baseUrl }/porg/view">Producer Groups</a>
	<span class="arrow-right"></span>

	<c:if test="${name eq 'porgproducers' or name eq 'porgdetails' }">
		<a style="display: none;" href="${baseUrl }/porg/details/${porgParam.id}">Details of - ${porgParam.name }</a>
	</c:if>

	<c:if test="${name eq 'porgproducers'  }">
		<a href="${baseUrl }/porgproducers/view/${porgParam.id}">Members of - ${porgParam.name }</a>
		<span class="arrow-right"></span>
	</c:if>

	<c:if test="${name eq 'porgdocs'  }">
		<a href="${baseUrl }/porgdocs/view/${porgParam.id}">Documents of - ${porgParam.name }</a>
		<span class="arrow-right"></span>
	</c:if>

	<c:if test="${name eq 'porgcommittees' or name eq 'porgcommittee-members' }">
		<a href="${baseUrl }/porg-committee/view/${porgParam.id}">Committees of - ${porgParam.name }</a>
		<span class="arrow-right"></span>

		<c:if test="${name eq 'porgcommittee-members' }">
			<a href="${baseUrl }/porg-committee-member/view/${committeeParam.id}">Members of - ${committeeParam.name }</a>
			<span class="arrow-right"></span>
		</c:if>

	</c:if>

</c:if>

<c:if test="${isForm }">
	<b>Form</b>
</c:if>