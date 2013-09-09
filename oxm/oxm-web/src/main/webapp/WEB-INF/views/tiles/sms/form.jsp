<%@page import="org.agric.oxm.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>

<div style="margin: 5px; width: 100%;">
	<oxmBreadcrambs:cpanel startingBreadcramb="true" />
	<oxmBreadcrambs:users />
	SMS Form
</div>

<div id="buttonStrip"></div>
<div>
	<div class="searchform group">
		<form:form id="multiBtnForm" action="${baseUrl}/user/sms/" commandName="usersearch">

			<fieldset>
				<legend>
					<span title="Search Recipients">Search Recipients</span>
				</legend>

				<form:hidden path="propertiesMap['committee'].value" />
				<form:hidden path="propertiesMap['committeemember'].value" />

				<div class="searchDiv">
					<ul>
						<li>
							<label>Name</label>
							<form:input cssClass="uiTextbox long" path="propertiesMap['name-or-username'].value" />
						</li>
						<li>
							<label>Producer org.</label>
							<form:select id="ddPOrg-TriggersDistrictChange" cssStyle="width:120px;"
								cssClass="uiDropdown long" path="propertiesMap['porg'].value"
							>
								<form:option value=""></form:option>
								<c:forEach var="porg" items="${pOrgs }">
									<form:option title="${porg.districtString }" value="${porg.id }"
										district="${porg.district.id }" county="${porg.county.id }"
										subcounty="${porg.subCounty.id }" parish="${porg.parish.id }"
										village="${porg.village.id }"
									>${porg.name }</form:option>
								</c:forEach>
								<!--<form:options items="${pOrgs }" itemLabel="nameAndSubCounty" itemValue="id" />-->
							</form:select>
						</li>
						<li>
							<label>Role</label>
							<form:select id="ddRole" cssStyle="width:120px;" cssClass="uiDropdown medium"
								path="propertiesMap['role'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${roles }" itemLabel="name" itemValue="id" />
							</form:select>
						</li>
						<li>
							<label>Gender</label>
							<form:select id="ddGender" cssStyle="width:60px;" cssClass="uiDropdown short"
								path="propertiesMap['gender'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${genders }" itemLabel="name" />
							</form:select>
						</li>
						<li>
							<label>House Hold</label>
							<form:select id="ddHouseHold" cssStyle="width:60px;" cssClass="uiDropdown medium"
								path="propertiesMap['householdcategory'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${householdcatrgories }" itemLabel="name" />
							</form:select>
						</li>

						<li>
							<label>Condition</label>
							<form:select id="ddLandSizeCondition1" cssStyle="width:60px;" cssClass="uiDropdown short"
								path="propertiesMap['landsizecondition1'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${conditions }" itemLabel="name" />
							</form:select>
						</li>
						<li>
							<label>land Size</label>
							<form:input id="ddLandSize1" cssStyle="width:60px;" cssClass="uiDropdown short"
								path="propertiesMap['landsize1'].value"
							/>
						</li>
						<li>
							<label>Condition</label>
							<form:select id="ddLandSizeCondition2" cssStyle="width:60px;" cssClass="uiDropdown short"
								path="propertiesMap['landsizecondition2'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${conditions }" itemLabel="name" />
							</form:select>
						</li>
						<li>
							<label>land Size</label>
							<form:input id="ddLandSize2" cssStyle="width:60px;" cssClass="uiDropdown short"
								path="propertiesMap['landsize2'].value"
							/>
						</li>
					</ul>


					<ul>
						<li>
							<label>District</label>
							<form:select id="jsondistrict" path="propertiesMap['district'].value"
								cssClass="uiDropdown medium" items="${districts }" itemLabel="name" itemValue="id"
								preselectedItem="${pDistrict }" url="${baseUrl}/get/json/districts" child="jsoncounty"
								firstElementBlank="true"
							/>
						</li>
						<li>
							<label>County</label>
							<form:select id="jsoncounty" path="propertiesMap['county'].value"
								cssClass="uiDropdown medium" items="${counties }" itemLabel="name" itemValue="id"
								preselectedItem="${pCounty }" url="${baseUrl}/get/json/counties/" parent="jsondistrict"
								child="jsonsubcounty" firstElementBlank="true"
							/>
						</li>
						<li>
							<label>Sub County</label>
							<form:select id="jsonsubcounty" path="propertiesMap['subCounty'].value"
								cssClass="uiDropdown medium" items="${subcounties }" itemLabel="name" itemValue="id"
								preselectedItem="${pSubCounty }" url="${baseUrl}/get/json/subcounties/" parent="jsoncounty"
								child="jsonparish" firstElementBlank="true"
							></form:select>
						</li>
						<li>
							<label>Parish</label>
							<form:select id="jsonparish" path="propertiesMap['parish'].value"
								cssClass="uiDropdown medium" items="${parishes }" itemLabel="name" itemValue="id"
								preselectedItem="${pParish }" url="${baseUrl}/get/json/parishes/" parent="jsonsubcounty"
								child="jsonvillage" firstElementBlank="true"
							></form:select>
						</li>
						<li>
							<label>Village</label>
							<form:select id="jsonvillage" path="propertiesMap['village'].value"
								cssClass="uiDropdown medium" items="${villages }" itemLabel="name" itemValue="id"
								preselectedItem="${pVillage }" url="${baseUrl}/get/json/villages/" parent="jsonparish"
								firstElementBlank="true"
							></form:select>
						</li>
						<li class="button-li">
							<input id="btnLoadRecipients" task="load" class="uiButton" type="submit"
								title="Preview Recipients" value="Preview" name="btnFilter"
							/>
						</li>
					</ul>
					<ul>
						<li>
							<label>Message</label>
							<label id="counter">(160)</label>
							<form:textarea cols="50" rows="3" cssClass="" path="propertiesMap['smstext'].value"
								placeholder="Your SMS Text here" itemName="SMS" id="limitedLengthSmsText" maxlength="160"
							/>

						</li>

						<li class="button-li">
							<input id="btnSendSms" task="send" class="uiButton" type="submit" value="Send SMS"
								name="btnFilter"
							/>
						</li>
					</ul>
				</div>
			</fieldset>

		</form:form>
	</div>
	<div style="clear: both"></div>
</div>

<div class="buttonStrip">
	<div style="float: right;">
		<%@ include file="/WEB-INF/views/navigation.jsp"%>
	</div>
</div>
<div style="clear: both"></div>
<jsp:include page="/WEB-INF/views/tiles/users/userlist.jsp"></jsp:include>


