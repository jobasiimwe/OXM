<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.Parish" name="parish"
	required="true"%>

<a title="List of Villages in ${parish.name}"
	href="${baseUrl }/village/view/${parish.id}">${parish.name }</a>
&gt;
