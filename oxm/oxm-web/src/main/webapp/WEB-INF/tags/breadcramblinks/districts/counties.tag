<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.District" name="district"
	required="true"%>

<a title="List of Counties in ${district.name}"
	href="${baseUrl }/county/view/${district.id}">${district.name }</a>
&gt;