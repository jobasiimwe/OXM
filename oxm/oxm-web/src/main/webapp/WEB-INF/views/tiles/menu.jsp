<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<script>
	$(function() {
		$("#accordion").accordion();
	});
</script>
<div>
	<div id="accordion">
		<h3><a href="#">Price Monitor</a>
		</h3>
		<div class="adminstration-div">
			<div class="admin-items">
				<img alt="home" src="static/images/home.png" title="Home"> <a
					title="Home" href="collegeControlPanel?action=view&item=student">
					<span>Home</span> </a>
			</div>
		</div>
		<h3>
			<a href="#">Market Place</a>
		</h3>
		<div class="adminstration-div">
			<div class="admin-items">
				<img alt="home" src="static/images/home.png" title="Home"> <a
					title="Home" href="collegeControlPanel?action=view&item=student">
					<span>Home</span> </a>
			</div>
		</div>
		<h3>
			<a href="#">Forum</a>
		</h3>
		<div class="adminstration-div">
			<div class="admin-items">
				<img alt="home" src="static/images/home.png" title="Home"> <a
					title="Home" href="collegeControlPanel?action=view&item=student">
					<span>Home</span> </a>
			</div>
		</div>
		<h3>
			<a href="#">Chat</a>
		</h3>
		<div class="adminstration-div">
			<div class="admin-items">
				<img alt="home" src="static/images/home.png" title="Home"> <a
					title="Home" href="collegeControlPanel?action=view&item=student">
					<span>Home</span> </a>
			</div>
		</div>
		<h3>
			<a href="#">Close All</a>
		</h3>
		<div></div>
	</div>
</div>