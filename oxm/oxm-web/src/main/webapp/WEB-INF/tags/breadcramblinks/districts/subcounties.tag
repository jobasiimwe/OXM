<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.County" name="county" required="true"%>

<a title="List of Sub-Counties in ${county.name}" href="${baseUrl }/subcounty/view/${county.id}">${county.name
	}</a>
&gt;
