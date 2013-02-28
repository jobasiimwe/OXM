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
	<div class="profile-dv">
		<div class="profile-img">
			<c:choose>
							<c:when test="${profile_Img == 'empty' }">
								<img alt="Profile picture"
									src="${baseUrl }/static/images/no_image.jpg"
									id="profilePicture" name="profilePicture" height="80px"
									width="80px" />
							</c:when>
							<c:otherwise>
								<img alt="Profile picture"
									src="${baseUrl }/user/pic/${loggedUser.id }"
									id="profilePicture" name="profilePicture" height="80px"
									width="80px" />
							</c:otherwise>
						</c:choose>
		</div>
		<div class="profile-content">
			<div style="clear: both; "><span>${loggedUser.fullName }</span></div>
			<div style="clear: both; margin: 5px;">
				<a title="Edit Profile"
						href="${baseUrl}/user/edit/${loggedUser.id}/0" class="uiButton"
						style="color: black;"><span>Edit Profile</span> </a>
			</div>
		</div>
	</div>
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