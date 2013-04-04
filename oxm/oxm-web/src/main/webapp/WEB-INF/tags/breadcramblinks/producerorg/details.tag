<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrganisation"
	name="producerOrg" required="true"%>

<a title="Details of Producer organisation - ${producerOrg.name}"
	href="${baseUrl }/porg/details/${producerOrg.id}">${producerOrg.name
	} - Details</a>
&gt;
