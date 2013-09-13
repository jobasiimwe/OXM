<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.ProducerOrg"
	name="producerOrg" rtexprvalue="true" required="true"%>

<a title="Staff Members of ${producerOrg.name}"
	href="${baseUrl }/porg-staff/${producerOrg.id}">Staff of - ${producerOrg.name
	}</a>
&gt;