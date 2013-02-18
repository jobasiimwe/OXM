/**
 * handles the farm scripting
 */
$(document).ready(function() {
	/*
	 * style buttons
	 */
	$('.uiButton').button();
	
	/**
	 * validate a staff form
	 */
	function validateUserForm() {
		return true;
	}
	
	$('#btnCancel').click(function(){
		var qStaffPage = $("#qStaffPage").val();
		var id = $("#id").val();
		// Cancel from profile page
		if(qStaffPage == "profilePage"){
			if(window.confirm('Do you want to cancel this operation?')){
				window.location = "staff?action=view&item=staff&id="+id;
				return true;
			}else{
				return false;
			}
		}else{
			if(window.confirm('Do you want to cancel this operation?')){
				window.location = "staff?action=view";
				return true;
			}else{
				return false;
			}
		}
	});

	/*
	 * edit staff member
	 */
	$('#btnEditUser').click(function() {
		var id = $("table.recordTable tr input:checked").attr("id");
		if(id != null){
			var href = $(this).attr("href");
			href = href + "&id="+id;
			$(this).attr("href", href);
			
			return true;
		}else{
			alert('please select a user member first');
			return false;
		}
	});
	
	$('#btnDeleteUser').click(function(){
		if($(":checked", "table.recordTable tbody tr").length > 0){
			if(window.confirm("Do you want to delete the selected User(s)?")){
				var ids=$("input[name=selectedUser]:checked").map(
					     function () {return this.value;}).get().join(",");
				var href = $(this).attr("href");
				href = href + "&userIdsToDelete=" + ids;
				$(this).attr("href", href);

				return true;
			}
		}else{
			alert("please select a user and try again")
		}
		
		return false;
	});
	
	$('#btnSaveUser').live('click', function() {
		// TODO validate the form input with javascript
		if (validateUserForm()) {
			return true
		}
		return false;
	});
	
});