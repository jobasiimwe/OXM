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
		<h3>
			<a href="#">Personal Profile</a>
		</h3>
		<div class="accordion-dv">
			<div class="item" style="background: rgb(68, 145, 86);">
				<div style="float: left; clear: both;">
					<div class="profile-img">
						<c:choose>
							<c:when test="${profile_Img == 'empty' }">
								<img alt="Profile picture"
									src="${baseUrl }/static/images/no_image.jpg"
									id="profilePicture" name="profilePicture" height="120px"
									width="120px" />
							</c:when>
							<c:otherwise>
								<img alt="Profile picture"
									src="${baseUrl }/user/pic/${loggedUser.id }"
									id="profilePicture" name="profilePicture" height="120px"
									width="120px" />
							</c:otherwise>
						</c:choose>
					</div>
					<div class="profile-content">
						<p style="width: 100%; border-bottom: 1px solid rgb(224, 219, 219);">
							<span style="font-weight: bold; font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 8pt; ">FirstName:
							</span> <span style="font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 10pt;"> ${loggedUser.firstName }</span>
						</p>
						<p style="width: 100%; border-bottom: 1px solid rgb(224, 219, 219);">
							<span style="font-weight: bold; font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 8pt;">LastName:
							</span> <span style="font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 10pt;"> ${loggedUser.lastName }</span>
						</p>
						<p style="width: 100%; border-bottom: 1px solid rgb(224, 219, 219);">
							<span style="font-weight: bold; font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 8pt;">Dob:
							</span> <span style="font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 10pt;"> <fmt:formatDate value="${loggedUser.dateOfBirth }"
								pattern="dd-yyyy" /> </span>
						</p>
						<p style="width: 100%; border-bottom: 1px solid rgb(224, 219, 219);">
							<span style="font-weight: bold; font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 8pt;">Gender:
							</span> <span style="font-family: Segoe UI, Tahoma, Verdana, Arial; font-size: 10pt;">${loggedUser.gender } </span>
						</p>
					</div>
				</div>
				<div style="margin-left: 5px; margin-top: 10px;">
					<a title="Edit Profile"
						href="${baseUrl}/user/edit/${loggedUser.id}/0" class="uiButton"
						style="color: white;"><span>Edit</span> </a>
				</div>
			</div>
		</div>

		<h3>
			<a href="#">Administration</a>
		</h3>
		<div class="accordion-dv">
			<div class="menu">
				<a id="lnkUsers" title="Users" href="${baseUrl }/user/view"> <span
					class="user-icon"></span> <label
					style="margin: 10px; font-size: 12px; font-weight: bold;">Users</label>
				</a>
			</div>
			<div class="menu">
				<a class="menu-lnk" id="lnkRoles" title="Roles & Permissions"
					href="${baseUrl }/role/view"> <span class="role-icon"></span><label
					style="margin: 10px; font-size: 12px; font-weight: bold;">Roles
						& Permissions</label> </a>
			</div>

			<div class="menu">
				<a class="menu-lnk" id="lnkConcepts" title="Concepts"
					href="${baseUrl }/concept/view/x"> <span class="concept-icon"></span><label
					style="margin: 10px; font-size: 12px; font-weight: bold;">Concept</label>
				</a>
			</div>

			<div class="menu">
				<a class="menu-lnk" id="lnkConceptCategories"
					title="Concept Categories" href="${baseUrl }/category/view/"> <span
					class="conceptCategory-icon"></span><label
					style="margin: 10px; font-size: 12px; font-weight: bold;">Concept
						Category</label> </a>
			</div>

			<div class="menu">
				<a class="menu-lnk" id="lnkCountries" title="Countries"
					href="${baseUrl }/district/view/"> <span class="country-icon"></span><label
					style="margin: 10px; font-size: 12px; font-weight: bold;">District</label>
				</a>
			</div>
			<div class="menu">
					<a class="menu-lnk" id="lnkCrops" title="Crops"
						href="${baseUrl }/crop/view/"> <span
						class="crop-icon"></span><label
						style="margin: 10px; font-size: 12px; font-weight: bold;">Crops</label>
					</a>
			</div>
		</div>
		<h3>
			<a href="#">Price Monitor</a>
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