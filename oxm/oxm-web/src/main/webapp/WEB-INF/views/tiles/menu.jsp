<%@ page language="java" import="org.studentsys.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
	
<script>
	$(function() {
		$( "#accordion" ).accordion();
	});
	
</script>
<div>
	<div id="accordion">
		<h3>
			<a href="#">Adminstration</a>
		</h3>
		<div class="adminstration-div">
		<div class="admin-items">
				<img alt="home" src="static/images/home.png" title="Home">
				<a title="Home" href="collegeControlPanel?action=view&item=student">
					<span>Home</span>
				</a>
			</div>
			<div class="admin-items">
				<img alt="add Collecge" src="static/images/concept.png" title="Add College">
				<a title="Add College" href="collegeControlPanel?action=view&item=college">
					<span>Add College</span>
				</a>
			</div>
			<div class="admin-items">
				<img alt="add user" src="static/images/user.png" title="Add users">
				<a title="Add Users" href="controlpanel?action=view&item=users">
					<span>Add Users</span>
				</a>
			</div>
			<div class="admin-items" style="display: none;">
				<img alt="add role & permission" src="static/images/role.png" title="Add Roles & Permissions">
				<a title="Add Roles & Permissions" href="menu?action=view&item=roles">
					<span>Add Roles & Permissions</span>
				</a>
			</div>
			<!--<div class="admin-items">
				<img alt="add concept" src="static/images/concept.png" title="Add Concept">
				<a title="Add Concepts" href="menu?action=view&item=concepts">
					<span>Add Concepts</span>
				</a>
			</div>
			<div class="admin-items">
				<img alt="add concept cateogry" src="static/images/concept_category.png" title="Add Concept Category">
				<a title="Add Concept Cateory" href="menu?action=view&item=conceptCategory">
					<span>Add Concept Category</span>
				</a>
			</div>
			<div class="admin-items">
				<img alt="View Reports" src="static/images/report.png" title="View Report">
				<a title="View Reports" href="menu?action=view&item=report">
					<span>Reports</span>
				</a>
			</div>-->
		</div>
		
		<h3>
			<a href="#">Welcome to SRS</a>
		</h3>
		<div>
			
		</div>
	</div>
</div>