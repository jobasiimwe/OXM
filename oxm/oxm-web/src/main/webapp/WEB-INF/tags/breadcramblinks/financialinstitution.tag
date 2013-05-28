
<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.FinancialInstitution" name="finst" rtexprvalue="true"
	required="true"
%>

<a title="Documents ${finst.name}"
	href="${baseUrl }/finstitution/docs/view/${finst.id}"
>Documents of - ${finst.name }</a>
&gt;
