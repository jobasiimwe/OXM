<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrganisation"
	name="producerOrg" rtexprvalue="true" required="true"%>

<a title="List of Producers in ${producerOrg.name}"
	href="${baseUrl }/porg/producers/${producerOrg.id}">Members of - ${producerOrg.name
	}</a>
&gt;
