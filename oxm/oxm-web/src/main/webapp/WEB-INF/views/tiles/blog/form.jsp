<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="oxmBreadcrambs" tagdir="/WEB-INF/tags/breadcramblinks"%>
<div style="margin: 5px; width: 100%;">
	<c:if test="${not empty blog.id }">
		<oxmBreadcrambs:cpanel name="blogs" blogParam="${blog }" isForm="true" adminView="true" />
	</c:if>

	<c:if test="${empty blog.id }">
		<oxmBreadcrambs:cpanel name="blogs" isForm="true" adminView="true" />
	</c:if>
</div>

<form:form action="${baseUrl }/blog/save" commandName="blog">

	<div class="box tabular">
		<form:hidden path="id" />
		<form:hidden path="createdBy" />
		<form:hidden path="dateCreated" />

		<p>
			<label>
				Title
				<span class="required">*</span>
			</label>
			<form:input id="mtxtTitle" path="title" cssClass="uiTextbox" placeHolder="Blog title..." />
		</p>

		<p>
			<label>
				Draft
				<span class="required">*</span>
			</label>
			<form:checkbox id="cbxDraft" path="draft" />
			(Un-check to publish this blog )
		</p>


		<p>
			<label>
				Blog-Text <br>(Note: Use the bottom right corner to resize this text-area)
				<span class="required">*</span>
			</label>
			<form:textarea id="mtxtBlog-Text" cols="70" rows="10" path="text" cssClass="" placeHolder="Blog away here..." />
		</p>
		<div style="clear: both"></div>
		<div>
			<input id="btnSaveCrop" type="submit" value="Save" class="uiButton" />
		</div>
	</div>
</form:form>
