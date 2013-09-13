<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div class="left_menu">

	<a href="${baseUrl }/">
		<span class="left_menu_lnk">
			<span class="smallicon home-icon"></span>
			<span style="padding-left: 10px;">Home</span>
		</span>
	</a>
	<a href="${baseUrl }/prelogin/prices">
		<span class="left_menu_lnk">
			<span class="smallicon price-icon"></span>
			<span style="padding-left: 10px;">Latest Prices</span>
		</span>
	</a>

	<a href="${baseUrl }/prelogin/seasons">
		<span class="left_menu_lnk">
			<span class="smallicon season-icon"></span>
			<span style="padding-left: 10px;">Seasons/Weather</span>
		</span>
	</a>

	<a href="${baseUrl }/prelogin/finstitutions">
		<span class="left_menu_lnk">
			<span class="smallicon fInstitution-icon"></span>
			<span style="padding-left: 10px;">Financial Services</span>
		</span>
	</a>

	<a href="${baseUrl }/prelogin/sellingplaces">
		<span class="left_menu_lnk">
			<span class="smallicon sellingplace-icon"></span>
			<span style="padding-left: 10px;">Market</span>
		</span>
	</a>

	<a title="" href="${baseUrl }/prelogin/crops">
		<span class="left_menu_lnk">
			<span class="smallicon crop-icon"></span>
			<span style="padding-left: 10px;">Crops</span>
		</span>
	</a>

</div>
