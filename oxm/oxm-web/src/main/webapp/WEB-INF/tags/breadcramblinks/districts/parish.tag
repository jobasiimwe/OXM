<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.SubCounty" name="subCounty"
	required="true"%>

<a title="List of Parishes in ${subCounty.name}"
	href="${baseUrl }/parish/view/${subCounty.id}">${subCounty.name }</a>