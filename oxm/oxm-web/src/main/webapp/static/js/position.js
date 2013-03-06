/**
 * handles the farm scripting
 */
$(document).ready(function() {

	$('#btnEditPosition').click(function() {
		if ($(":checked", "table.recordTable tbody tr").length > 0) {
			var id = $("input[name=selectedPosition]:checked").map(function() {
				return this.value;
			}).get().join(",");
			if (id.indexOf(",") != -1) {
				alert("Select only one position to perform this operation");
				return false;
			}

			var href = $(this).attr("href");
			href = href + id;
			$(this).attr("href", href);

			return true;
		} else {
			alert("Select a position and try again");
		}

		return false;
	});

	$('#btnPositionHolders').click(function() {
		if ($(":checked", "table.recordTable tbody tr").length > 0) {
			var id = $("input[name=selectedPosition]:checked").map(function() {
				return this.value;
			}).get().join(",");
			if (id.indexOf(",") != -1) {
				alert("Select only one position to perform this operation");
				return false;
			}

			var href = $(this).attr("href");
			href = href + id;
			$(this).attr("href", href);

			return true;
		} else {
			alert("Select a position and try again");
		}

		return false;
	});

	$('#btnDeletePosition').click(function() {
		if ($(":checked", "table.recordTable tbody tr").length > 0) {
			if (window.confirm("Do you want to delete the selected Position(s)?")) {
				var ids = $("input[name=selectedPosition]:checked").map(function() {
					return this.value;
				}).get().join(",");
				var href = $(this).attr("href");
				href = href + ids;
				$(this).attr("href", href);

				return true;
			}
		} else {
			alert("Please select a Position and try again")
		}

		return false;
	});

});