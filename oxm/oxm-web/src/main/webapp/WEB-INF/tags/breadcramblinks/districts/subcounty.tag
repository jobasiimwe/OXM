<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.District" name="district"
	required="true"%>

<a title="List of Sub-Counties in ${district.name}"
	href="${baseUrl }/subcounty/view/${district.id}">${district.name }</a>