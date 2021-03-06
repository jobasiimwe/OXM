<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel-porg name="porgcommittee-members" porgParam="${pOrg }" committeeParam="${committee }" isForm="true" />
</div>


<div>

	<form:form action="${baseUrl }/porg-committee-member/save/" commandName="committeeMember">

		<form:hidden path="id" />
		<form:hidden path="committee" />
		<div class="splitcontentleft">
			<div class="box tabular">
				<table>
					<tr>
						<td><label>Position <span class="required">*</span>
						</label></td>
						<td><form:select id="cbxPosition" path="position" itemValue="id" itemLabel="name" items="${positions }"
								cssStyle="width: 400px;">
							</form:select></td>
					</tr>
					<tr>
						<td><label>From(Date)<span class="required">*</span>
						</label></td>
						<td><form:input path="fromDate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox" /></td>
						<td><label>To (Date) <span class="required">*</span>
						</label></td>
						<td><form:input path="toDate" cssStyle="width: 100px;" cssClass="uiTextbox uiDateTextbox" /></td>
					</tr>
					<tr>
						<td><label>Position Holder<span class="required">*</span>
						</label></td>
						<td><form:hidden id="txtPositionHolder" path="user" /> <c:if test="${not empty committeeMember.user }">
								${committeeMember.user.name }
							</c:if></td>
					</tr>
				</table>
			</div>
		</div>
		<div style="clear: both"></div>
		<div>
			<input id="btnSavePOrgCommitteeMember" type="submit" value="Save" class="uiButton" />
		</div>
	</form:form>
	<jsp:include page="/WEB-INF/views/tiles/users/userlist.jsp"></jsp:include>
</div>