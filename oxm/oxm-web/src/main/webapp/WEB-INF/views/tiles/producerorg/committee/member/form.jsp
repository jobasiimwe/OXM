<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
form#concept input[type="text"] {
	width: 70%;
}

#conceptcategorylist span {
	display: inline-block;
	width: 50%;
}
</style>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>
<%@ taglib prefix="oxmProducerOrgBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks/producerorg"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmProducerOrgBreadcrambs:producerorgs />
	<oxmProducerOrgBreadcrambs:details producerOrg="${pOrg }" />
	<oxmProducerOrgBreadcrambs:committees producerOrg="${pOrg }" />
	<oxmProducerOrgBreadcrambs:committeemembers committee="${committee }" />
	Form

</div>

<div>
	<div class="searchform group">
		<form:form action="${baseUrl}/porg-committee/member/search"
			commandName="usersearch">
			<jsp:include page="/WEB-INF/views/tiles/users/searchfields.jsp"></jsp:include>
		</form:form>
	</div>
	<div style="clear: both"></div>
</div>

<div>

	<form:form action="${baseUrl }/porg-committee/member/save/"
		commandName="committeeMember">

		<form:hidden path="id" />
		<form:hidden path="committee" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<table>
					<tr>
						<td><label>Position <span class="required">*</span> </label>
						</td>
						<td><form:select id="cbxPosition" path="position"
								itemValue="id" itemLabel="name" items="${positions }"
								cssStyle="width: 400px;">
							</form:select>
						</td>
						<td><label>From date <span class="required">*</span>
						</label></td>
						<td>--</td>
						<td><label>To Date <span class="required">*</span> </label></td>
						<td>--</td>
					</tr>
					<tr>
						<td><label>Position Holder<span class="required">*</span>
						</label></td>
						<td><form:hidden id="txtPositionHolder" path="positionHolder" />
							<c:if test="${not empty committeeMember.positionHolder }">
								${committeeMember.positionHolder.name }
							</c:if></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrgCommitteeMember" type="submit" value="Save"
				class="uiButton" />
		</div>
	</form:form>
	<jsp:include page="/WEB-INF/views/tiles/users/userlist.jsp"></jsp:include>
</div>