<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrg"
	name="producerOrg" rtexprvalue="true" required="true"%>

<a title="Details of Producer organisation - ${producerOrg.name}"
	href="${baseUrl }/porg/details/${producerOrg.id}">Details of -
	${producerOrg.name }</a>
&gt;
