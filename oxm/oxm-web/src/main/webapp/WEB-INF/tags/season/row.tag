
<%@ tag body-content="empty"%>

<%@ attribute type="org.agric.oxm.model.Season" name="season"
	rtexprvalue="true" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sysTags" tagdir="/WEB-INF/tags"%>

<%@ attribute type="java.lang.Boolean" name="printCheckBox"
	required="true"%>
<%@ attribute type="java.lang.Integer" name="status" required="false"%>

<tr id="${season.id }">
	<td><c:if test="${printCheckBox }">
			<sysTags:rowcheckbox nameOfItemOnPage="${nameOfItemOnPage}"
				id="${season.id }" />
		</c:if> ${status }</td>
	<td>${season.name }</td>
	<td style="display: none;"><sysTags:print-date
			date="${season.startDate}" /></td>
	<td style="display: none;"><sysTags:print-date
			date="${season.endDate}" /></td>

	<td><c:if test="${not empty season.weather }">
			<span class="smallicon ${season.weather.name }"></span>${season.weather.name }</c:if></td>

	<td><c:if test="${not empty season.weatherDescription }">
									${season.weatherDescription }
								</c:if></td>
</tr>