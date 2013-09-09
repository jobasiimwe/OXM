
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmProducerOrgBreadcrambs:producerorgs />
	<oxmProducerOrgBreadcrambs:producers producerOrg="${pOrg }" />
	form
</div>

<div>
	<form:form action="${baseUrl }/porg/producers/save" commandName="producer" method="POST"
		enctype="multipart/form-data"
	>

		<form:hidden path="id" />
		<form:hidden path="producerOrg" />
		<div class="box">
			<jsp:include page="/WEB-INF/views/tiles/users/form/formfields.jsp"></jsp:include>
			<div style="clear: both;"></div>
			<jsp:include page="/WEB-INF/views/tiles/users/form/districtfields.jsp"></jsp:include>
			<div style="clear: both;"></div>
		</div>
		<div>
			<input id="btnSavePOrgProducer" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>