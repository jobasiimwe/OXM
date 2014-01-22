<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<c:set var="baseUrl" scope="application"
	value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"
/>
<link rel="icon" type="image/png" href="${baseUrl }/static/images/plant-ico.png" />

<link type="text/css" rel="stylesheet" href="${baseUrl}/static/css/custom-theme/jquery-ui-1.8.15.custom.css" />
<link type="text/css" rel="stylesheet" href="${baseUrl}/static/css/oxm.css" />
<link type="text/css" rel="stylesheet" href="${baseUrl }/static/css/oxm-images.css" />

<link type="text/css" rel="stylesheet" href="${baseUrl}/static/css/oxm-prelogin.css" />
<link type="text/css" rel="stylesheet" href="${baseUrl}/static/css/oxm-search.css" />

<link type="text/css" rel="stylesheet" href="${baseUrl}/static/css/bjqs.css">
<link type="text/css" rel="stylesheet" href="${baseUrl}/static/css/demo.css">

<!--<script type="text/javascript" src="${baseUrl }/static/js/jquery-1.9.1.min.js"></script>-->
<script type="text/javascript" src="${baseUrl }/static/js/jquery-1.7.1.min.js"></script>

<script type="text/javascript" src="${baseUrl}/static/js/jquery-ui-1.8.15.custom.min.js"></script>
<script type="text/javascript" src="${baseUrl}/static/js/oxm.js"></script>
<link rel="icon" type="image/png" href="${baseUrl}/static/images/oxm-footer-lg.png" />

<script type="text/javascript" src="${baseUrl}/static/js/slider-js/bjqs-1.3.min.js"></script>
<script type="text/javascript" src="${baseUrl}/static/js/slider-js/bjqs-1.3.js"></script>

</head>

<body style="background: white;">

	<jsp:include page="/WEB-INF/views/tiles/prelogin/header.jsp"></jsp:include>

	<div class="login-body-dv">

		<div id="slider">
			<ul class="bjqs">
				<li>
					<img src="${baseUrl}/static/images/img/banners/learning.jpg" title="Learning together">
				</li>
				<li>
					<img src="${baseUrl}/static/images/img/banners/planting.jpg" title="Planting">
				</li>
				<li>
					<img src="${baseUrl}/static/images/img/banners/weeding.jpg" title="Weeding">
				</li>
				<li>
					<img src="${baseUrl}/static/images/img/banners/produce.jpg" title="Produce">
				</li>
				<li>
					<img src="${baseUrl}/static/images/img/banners/produce2.jpg" title="Produce">
				</li>
				<li>
					<img src="${baseUrl}/static/images/img/banners/produce3.jpg" title="Produce">
				</li>
			</ul>
		</div>
		<!-- End outer wrapper -->

		<script class="secret-source">
			jQuery(document).ready(function($) {

				$('#slider').bjqs({
					height : 400,
					responsive : true
				});

			});
		</script>


		<div class="small-big-container">
			<div class="small-dv">
				<jsp:include page="/WEB-INF/views/tiles/prelogin/menu.jsp"></jsp:include>
			</div>
			<div class="big-dv">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>

	<div class="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>