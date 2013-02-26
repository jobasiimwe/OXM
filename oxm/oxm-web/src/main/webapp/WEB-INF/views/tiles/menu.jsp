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
			<a href="#">Profile</a>
		</h3>
		<div class="accordion-dv">
			<div class="item">
				<div style="float: left; clear: both;">
					<div class="profile-img">
						<c:choose>
							<c:when test="${profile_Img == 'empty' }">
								<img alt="Profile picture"
									src="${baseUrl }/static/images/no_image.jpg"
									id="profilePicture" name="profilePicture" height="80px"
									width="40%" />
							</c:when>
							<c:otherwise>
								<img alt="Profile picture"
									src="${baseUrl }/user/pic/${loggedUser.id }" id="profilePicture"
									name="profilePicture" height="80px" width="100%" />
							</c:otherwise>
						</c:choose>
					</div>
					<div class="profile-content">
						<label class="uiLabel" style="font-weight: bold; display: inline;">FirstName:
						</label> <label>${loggedUser.firstName }</label> <label class="uiLabel"
							style="font-weight: bold; display: inline;">LastName: </label> <label>${loggedUser.lastName
							}</label> <label class="uiLabel"
							style="font-weight: bold; display: inline;">Gender: </label> <label>${loggedUser.gender
							} </label> <label class="uiLabel" style="font-weight: bold;">Dob:
						</label> <label><fmt:formatDate value="${loggedUser.dateOfBirth }"
								pattern="dd/yyyy" /> </label>
					</div>
				</div>
				<div style="margin-left: 5px; margin-top: 10px;">
					<a title="Edit Profile" href="${baseUrl}/user/edit/${loggedUser.id}/0"
						class="uiButton"><span>Edit</span> </a>
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
						style="margin: 10px; font-size: 12px; font-weight: bold;">Roles  & Permissions</label>
					</a>
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
						style="margin: 10px; font-size: 12px; font-weight: bold;">Concept Category</label>
					</a>
			</div>
			
			<div class="menu">
					<a class="menu-lnk" id="lnkCountries" title="Countries"
						href="${baseUrl }/district/view/"> <span
						class="country-icon"></span><label
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