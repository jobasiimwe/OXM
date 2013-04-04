<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>

<div>
	<div style="margin: 5px; width: 100%;">
		<oxmBreadcrambs:cpanel startingBreadcramb="true" />
		<oxmProducerOrgBreadcrambs:producerorgs />
		<oxmProducerOrgBreadcrambs:details producerOrg="${pOrg }" />
	</div>

	<div>
		<div style="float: left; width: 45%;">Producers</div>
		<div style="float: left; width: 45%; padding-left: 5%">
			Documents</div>

		<div style="clear: both;"></div>

		<div style="float: left; width: 45%;">Staff</div>
		<div style="float: left; width: 45%; padding-left: 5%">
			Committees</div>
	</div>

</div>