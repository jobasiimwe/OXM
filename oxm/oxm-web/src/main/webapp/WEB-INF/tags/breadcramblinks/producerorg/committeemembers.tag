<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.Committee"
	name="committee" rtexprvalue="true" required="true"%>

<a title="Members of ${committee.name}"
	href="${baseUrl }/porg-committee/member/${committee.id}">${committee.name
	}</a>
&gt;