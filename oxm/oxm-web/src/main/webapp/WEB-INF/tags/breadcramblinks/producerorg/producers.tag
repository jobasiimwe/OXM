<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrganisation"
	name="producerOrg" rtexprvalue="true" required="true"%>

<a title="List of Producers in ${producerOrg.name}"
	href="${baseUrl }/porg/producers/view/${producerOrg.id}">${producerOrg.name
	}</a>
&gt;
