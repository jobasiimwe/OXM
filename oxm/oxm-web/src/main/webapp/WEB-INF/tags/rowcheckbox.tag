
<%@ tag body-content="empty"%>

<%@ attribute type="java.lang.String" name="id" required="true" rtexprvalue="true"%>
<%@ attribute type="java.lang.String" name="nameOfItemOnPage" required="true" rtexprvalue="true"%>

<input type="checkbox" name="selected${nameOfItemOnPage }"
							value="${id }" />