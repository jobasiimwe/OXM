<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrg" name="producerOrg" rtexprvalue="true"
	required="true"
%>

<a title="Committes of ${producerOrg.name}" href="${baseUrl }/porg-committee/${producerOrg.id}">Committees
	of - ${producerOrg.name }</a>
&gt;
