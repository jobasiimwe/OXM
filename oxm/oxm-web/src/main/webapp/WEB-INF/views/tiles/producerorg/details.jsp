<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div>


	<div style="margin: 5px; width: 100%;">
		<oxmBreadcrambs:cpanel-porg name="porgdetails" producerOrg="${pOrg}" />
	</div>

	<div>
		<div style="float: left; width: 45%;">
			<fieldset>
				<legend>
					<h3>Producers</h3>
				</legend>
				<jsp:include page="/WEB-INF/views/tiles/producerorg/members/view.jsp"></jsp:include>
			</fieldset>
		</div>
		<div style="float: left; width: 45%; padding-left: 5%">
			<fieldset>
				<legend>
					<h3>Documents</h3>
				</legend>
			</fieldset>
		</div>

		<div style="clear: both;"></div>

		<div style="float: left; width: 45%;">
			<fieldset>
				<legend>
					<h3>Staff</h3>
					</a>
				</legend>
			</fieldset>
		</div>
		<div style="float: left; width: 45%; padding-left: 5%">
			<fieldset>
				<legend>
					<h3>Committees</h3>
				</legend>
				<jsp:include page="/WEB-INF/views/tiles/producerorg/committee/view.jsp"></jsp:include>

			</fieldset>
		</div>
	</div>

</div>