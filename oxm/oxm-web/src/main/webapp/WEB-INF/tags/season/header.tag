
<%@ tag body-content="empty"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute type="java.lang.Boolean" name="printCheckBox" required="true"%>


<tr>
	<th><c:if test="${printCheckBox }">
			<input type="checkbox" name="cbxSelectAllItems" id="cbxSelectAllItems" />
		</c:if> No.</th>
	<th>Period</th>
	<th>Weather</th>
	<th>Description</th>
</tr>