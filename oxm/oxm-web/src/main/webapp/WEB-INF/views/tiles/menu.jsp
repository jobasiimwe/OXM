<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<div>
	<div class="left_menu">

		<a title="Prices" href="${baseUrl }/price/view/admin/false">
			<span class="left_menu_lnk">
				<span class="smallicon price-icon"></span>
				<span style="padding-left: 10px;">Prices</span>
			</span>
		</a>
		<a href="${baseUrl }/blog/view/false">
			<span class="left_menu_lnk">
				<span class="smallicon blog-icon"></span>
				<span style="padding-left: 10px;">Blog</span>
			</span>
		</a>
		<a style="display: none;" title="Buyers and Sellers forum to advertise and order for produce or farm inputs"
			href="${baseUrl }/post/view"
		>
			<span class="left_menu_lnk">
				<span class="smallicon forum-icon"></span>
				<span style="padding-left: 10px;">Forum (Buy/Sell)</span>
			</span>
		</a>

	</div>
</div>