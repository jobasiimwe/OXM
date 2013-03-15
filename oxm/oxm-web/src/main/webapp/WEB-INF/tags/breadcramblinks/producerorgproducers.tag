<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrganisation"
	name="producerOrg" required="true"%>

<a title="List of Producers in ${producerOrg.name}"
	href="${baseUrl }/producerorg/producers/view/${producerOrg.id}">${producerOrg.name
	}</a>
&gt;
