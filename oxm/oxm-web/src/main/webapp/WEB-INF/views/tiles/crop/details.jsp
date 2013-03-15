<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<%@ taglib prefix="oxmTags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="oxmBreadcrambs"
	tagdir="/WEB-INF/tags/breadcramblinks"%>

<div>
	<div style="margin: 5px; width: 100%;">
		<oxmBreadcrambs:cpanel startingBreadcramb="true" />
		<oxmBreadcrambs:crops />
	</div>

	<oxmTags:printconcepts listHeader="Inputs" concepts="${crop.inputs }" />
	<oxmTags:printconcepts listHeader="Seed Varieties"
		concepts="${crop.seedVarieties }" />
	<oxmTags:printconcepts listHeader="Ploughing methods"
		concepts="${crop.ploughingMethods }" />
	<oxmTags:printconcepts listHeader="Inter Cropping Type"
		concepts="${crop.interCroppingTypes }" />
	<oxmTags:printconcepts listHeader="Units of Measure"
		concepts="${crop.unitsOfMeasure }" />

</div>