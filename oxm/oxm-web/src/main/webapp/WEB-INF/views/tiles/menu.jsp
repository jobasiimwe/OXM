<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>

<div>
	<div class="left_menu">
		<div class="left_menu_lnk">
			<a title="Prices" href="${baseUrl }/price/view/admin/false/page/1">
				<span class="smallicon price-icon"></span><span>Prices</span>
			</a>
		</div>
		<div class="left_menu_lnk">
			<a title="Prices" href="${baseUrl }/"> <img alt="price"
				src="static/images/price-ico.png" title="Prices"><span>Others</span>
			</a>
		</div>
	</div>
</div>