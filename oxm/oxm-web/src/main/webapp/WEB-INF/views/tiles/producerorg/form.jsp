<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel-porg name="porgs" isForm="true" />
</div>

<div>

	<form:form action="${baseUrl }/porg/save/" commandName="pOrganization">

		<form:hidden path="id" />
		<div class="box splitcontentleft">
			<div class="tabular">
				<p>
					<label>
						Name
						<span class="required">*</span>
					</label>
					<form:input path="name" cssClass="uiTextbox" />
				</p>
			</div>

			<jsp:include page="/WEB-INF/views/tiles/districtfields.jsp"></jsp:include>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrganization" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
</div>