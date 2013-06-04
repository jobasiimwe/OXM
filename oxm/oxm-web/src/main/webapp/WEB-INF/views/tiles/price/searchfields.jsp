<!-- 
	This file should never be by it's own it should be imported in a form tag
 -->
<%@page language="java" isELIgnored="false" contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="searchform group">
	<form:form action="${baseUrl}/price/search" commandName="pricesearch">
		<fieldset>
			<legend>
				<span title="Search Prices">Search Prices</span>
			</legend>

			<form:hidden path="propertiesMap['adminview'].booleanValue" />
			<div style="float: left;">
				<table>
					<tr>
						<td>Crop <form:select id="ddCrops" cssStyle="width: 100px;" cssClass="uiDropdown"
								path="propertiesMap['cropid'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${crops }" itemLabel="name" itemValue="id" />
							</form:select>
						</td>
						<td>Place <form:select id="ddSellingPlace" cssStyle="width: 100px;" cssClass="uiDropdown"
								path="propertiesMap['sellingplaceid'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${sellingPlaces }" itemLabel="name" itemValue="id" />
							</form:select>
						</td>
						<td>Sell-Type <form:select id="ddSellType" cssStyle="width: 100px;" cssClass="uiDropdown"
								path="propertiesMap['selltypeid'].value"
							>
								<form:option value=""></form:option>
								<form:options items="${selltypes }" itemLabel="name" itemValue="id" />
							</form:select>
						</td>

						<td>From <form:input id="fromdate" cssStyle="width: 100px;"
								cssClass="uiTextbox uiDateTextbox" path="propertiesMap['fromdate'].value"
							/>
						</td>
						<td>To <form:input id="todate" cssStyle="width: 100px;"
								cssClass="uiTextbox uiDateTextbox" path="propertiesMap['todate'].value"
							/>
						</td>
						<td><input id="filter" type="submit" value="Search" name="btnFilter" /></td>
					</tr>
				</table>
			</div>

		</fieldset>
	</form:form>
</div>