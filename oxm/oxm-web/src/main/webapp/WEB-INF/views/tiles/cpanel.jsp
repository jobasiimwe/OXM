<%@ page language="java" 
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<c:set var="baseUrl"
	value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}" />
<div class="cpanel">
	<div class="menu">
		<a id="lnkUsers" title="Users" href="${baseUrl }/user/view"> <span
			class="user-icon"></span> <span
			style="margin: 10px; font-size: 12px; font-weight: bold;">Users</span>
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
						href="${baseUrl }/crop/view/page/1"> <span
						class="crop-icon"></span><label
						style="margin: 10px; font-size: 12px; font-weight: bold;">Crops</label>
					</a>
			</div>
			<div class="menu">
					<a class="menu-lnk" id="lnkSellingPlace" title="Selling Place"
						href="${baseUrl }/sellingplace/view/"> <span
						class="sellingplace-icon"></span><label
						style="margin: 10px; font-size: 12px; font-weight: bold;">Selling Place</label>
					</a>
			</div>
			<div class="menu">
					<a class="menu-lnk" id="lnkProductionOrg" title="Production Organization"
						href="${baseUrl }/pOrganization/view/"> <span
						class="porganization-icon"></span><label
						style="margin: 10px; font-size: 12px; font-weight: bold;">Production Org</label>
					</a>
			</div>
			
			
</div>