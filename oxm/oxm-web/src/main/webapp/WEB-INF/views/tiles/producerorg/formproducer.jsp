<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>


<div>

	<div style="margin: 5px; width: 100%;">
		<label class="uiLabel">You are here >> </label>
		<oxmBreadcrambs:cpanel />
		>
		<oxmBreadcrambs:producerorgs />
		>
		<oxmBreadcrambs:producerorgproducers producerOrg="${pOrg }" />
		> form
	</div>
	<form:form action="${baseUrl }/producerorg/producers/save"
		commandName="producer" method="POST" enctype="multipart/form-data">

		<form:hidden path="id" />
		<form:hidden path="producerOrg" />
		<jsp:include page="/WEB-INF/views/tiles/users/formfields.jsp"></jsp:include>
		<div style="clear: both"></div>
		<jsp:include page="/WEB-INF/views/tiles/users/locationformfields.jsp"></jsp:include>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrgProducer" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
</div>