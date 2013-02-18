/**
 * 
 */
$(document).ready(function() {
	/*
	 * delete concept.
	 */
	$("#lnkDeleteConcept").click(function() {
		if($(":checked", "table.recordTable tbody tr").length > 0){
			if(window.confirm("Do you want to delete the selected concept(s)?")){
				$.ajax({
					  type: 'POST',
					  url: $(this).attr("href"),
					  data: $("input[name='selectedConcepts']").serialize(),
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
			alert("please select a qualification and try again")
		}
		
		return false;
	});
	
	/*
	 * edit a concept
	 */
	$("#lnkEditConcept").click(function(){
		var numberSelected = $(":checked", "table.recordTable tbody tr").length;
		if( numberSelected > 0){
			if(numberSelected == 1){
				var url = $(this).attr("href");
				url += "&id="+$(":checked", "table.recordTable tbody tr").attr("value");
				
				window.location = url;
			}else{
				alert("please select only one concept and try again.")
			}
		}else{
			alert("please select a concept and try again")
		}
		
		return false;
	});
	
	/*
	 * edit a concept
	 */
	$("#btnLoadConceptCategoryConcepts").click(function(){
		var id = $('#category').val();
		if(id != null && id != ""){
			var formUrl = $('#conceptCategoryForm').attr('action');
			formUrl = formUrl + "&cid="+id;
			$('#conceptCategoryForm').attr('action', formUrl);
			
			return true;
		}else{
			alert('please select a concept category first');
			return false;
		}
	});
});