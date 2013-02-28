/**
 * handles the farm scripting
 */
$(document).ready(function() {

	$('#btnEditCrop').click(function() {
		if ($(":checked", "table.recordTable tbody tr").length > 0) {
			var id = $("input[name=selectedCrop]:checked").map(function() {
				return this.value;
			}).get().join(",");
			if (id.indexOf(",") != -1) {
				alert("Select only one crop to perform this operation");
				return false;
			}

			var href = $(this).attr("href");
			href = href + id;
			$(this).attr("href", href);

			return true;
		} else {
			alert("Select a crop and try again");
		}

		return false;
	});

	$('#btnCropDetails').click(function() {
		if ($(":checked", "table.recordTable tbody tr").length > 0) {
			var id = $("input[name=selectedCrop]:checked").map(function() {
				return this.value;
			}).get().join(",");
			if (id.indexOf(",") != -1) {
				alert("Select only one crop to perform this operation");
				return false;
			}

			var href = $(this).attr("href");
			href = href + id;
			$(this).attr("href", href);

			return true;
		} else {
			alert("Select a crop and try again");
		}

		return false;
	});

	$('#btnDeleteUser').click(function() {
		if ($(":checked", "table.recordTable tbody tr").length > 0) {
			if (window.confirm("Do you want to delete the selected Crop(s)?")) {
				var ids = $("input[name=selectedCrop]:checked").map(function() {
					return this.value;
				}).get().join(",");
				var href = $(this).attr("href");
				href = href + ids;
				$(this).attr("href", href);

				return true;
			}
		} else {
			alert("Please select a Crop and try again")
		}

		return false;
	});

});