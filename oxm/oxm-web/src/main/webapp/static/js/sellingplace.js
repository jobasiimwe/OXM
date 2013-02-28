/**
 * 
 */
$(document).ready(function() {
	/*
	 * delete selling place.
	 */
	$("#lnkDeleteSellingPlace").click(function() {
		if($(":checked", "table.recordTable tbody tr").length > 0){
			if(window.confirm("Do you want to delete the Selling Place(s)?")){
				$.ajax({
					  type: 'POST',
					  url: $(this).attr("href"),
					  data: $("input[name='selectedSellingPlace']").serialize(),
					  success: function(data, textStatus, xmlHttpRequest){
						  $(":checked", "table.recordTable tbody tr").each(function(){
							  var id = $(this).attr("value");
							  $("table.recordTable tbody tr#"+id).remove();
						  });
						  
						  setSystemMessage(xmlHttpRequest.responseText);
					  }
					});
			}
		}else{
			alert("please select a Selling place and try again")
		}
		
		return false;
	});
	
	/*
	 * edit a concept
	 */
	$("#lnkEditSellingPlace").click(function(){
		var numberSelected = $(":checked", "table.recordTable tbody tr").length;
		if( numberSelected > 0){
			if(numberSelected == 1){
				var url = $(this).attr("href");
				url += $(":checked", "table.recordTable tbody tr").attr("value");
				
				window.location = url;
			}else{
				alert("please select only one Selling place and try again.")
			}
		}else{
			alert("please select a Selling place and try again")
		}
		
		return false;
	});
});