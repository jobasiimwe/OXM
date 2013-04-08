function hasText(id, fieldName) {
	var value = $.trim($("#" + id).val());
	if (value == null || value == "") {
		alert(fieldName + " is required!!");
		return false;
	}

	return true;
}

function someAreChecked(checkBoxesName, fieldName) {
	if ($("input[name=" + checkBoxesName + "]:checked").length > 0)
		return true;
	else {
		alert(fieldName + " is required!!");
		return false;
	}
}

function singleDropDownItemAction(buttonId, dropDownId, itemName) {
	var selectedId = $('#' + dropDownId).attr("value");
	if (selectedId == null || selectedId == "") {
		alert("First select a " + itemName + " to perform this action");
		return false;
	} else {
		var href = $('#' + buttonId).attr("href");
		href = href + selectedId;
		$('#' + buttonId).attr("href", href);

		return true;
	}
}

/**
 * sets the system message
 */
function setSystemMessage(message) {
	$("div#systemMsg #sMsg").html(message);
	$("div#systemMsg").removeClass('hide');

	if (!$("div#errorMsg").hasClass('hide'))
		$("div#errorMsg").addClass('hide');
}

/**
 * sets the error message
 */
function setErrorMessage(message) {
	$("div#errorMsg #eMsg").html(message);
	$("div#errorMsg").removeClass('hide');

	if (!$("div#systemMsg").hasClass('hide'))
		$("div#systemMsg").addClass('hide');
}

function getBaseURL() {
	var baseUrl = $("#baseUrl").attr("value");
	if (baseUrl == null || baseUrl == "")
		baseUrl = location.protocol + "//" + location.hostname
				+ (location.port && ":" + location.port) + "/";
	else
		baseUrl = baseUrl + "/";
	return baseUrl;
}

/**
 * this method handles link/button clicks that require only one item in the
 * record table to be selected
 * 
 * @param id
 * @returns {Boolean}
 */
function singleItemAction(btnId, itemName) {
	if (itemName == null || itemName == "")
		itemName = "item";

	if ($(":checked", "table.recordTable tbody tr").length > 0) {
		var id = $("input[name=selected" + itemName + "]:checked").map(
				function() {
					return this.value;
				}).get().join(",");
		if (id.indexOf(",") != -1) {
			alert("Please select only one " + itemName + " and try again.");
			return false;
		}

		var href = $("#" + btnId).attr("href");
		href = href + id;
		$("#" + btnId).attr("href", href);

		return true;
	} else {
		alert("Select a " + itemName + " and try again");
	}

	return false;

}

/**
 * this method handles link/button clicks that require only one item in the
 * record table to be selected
 * 
 * @param id
 * @returns {Boolean}
 */
function getSingleSelectedItem(btnId, itemName) {
	if (itemName == null || itemName == "")
		itemName = "item";

	if ($(":checked", "table.recordTable tbody tr").length > 0) {
		var id = $("input[name=selected" + itemName + "]:checked").map(
				function() {
					return this.value;
				}).get().join(",");
		if (id.indexOf(",") != -1) {
			return "multiple";
		}

		return id;
		
	} else return "none";

}

/**
 * this method handles link and button clicks that require a one or more items
 * in the record table to be selected
 * 
 * @param id
 * @returns {Boolean}
 */
function multipleItemAction(btnId, itemName) {
	if (itemName == null || itemName == "")
		itemName = "item";

	if ($(":checked", "table.recordTable tbody tr").length > 0) {
		if (window.confirm("Do you want to delete the selected " + itemName
				+ "(s)?")) {
			var ids = $("input[name=selected" + itemName + "]:checked").map(
					function() {
						return this.value;
					}).get().join(",");
			var href = $("#" + btnId).attr("href");
			href = href + ids;
			$("#" + btnId).attr("href", href);

			return true;
		}
	} else {
		alert("Please select " + itemName + "(s) and try again")
	}

	return false;

}

/**
 * System wide Javascript file
 */
$(document).ready(
		function() {

			// setup global ajax requests
			$.ajaxSetup({
				beforeSend : function(xhr, settings) {
					xhr.setRequestHeader("X-AjaxRequest", "1");
				},
				complete : function(xhr, textStatus) {
					if (xhr.status == 601) {
						// alert("xhr status: " + xhr.status);
						window.location.reload();
					}
				}
			});

			/*
			 * setting the datepicker settings
			 */
			$(".uiDateTextbox").datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : 'dd/mm/yy'
			});

			$(".uiDateTextbox").live("focus", function() {
				$(this).datepicker({
					changeMonth : true,
					changeYear : true,
					dateFormat : 'dd/mm/yy'
				});
			});

			/*
			 * setup the ajax loader
			 */
			$("#loader").hide().ajaxStart(function() {
				$(this).show();
			}).ajaxStop(function() {
				$(this).hide();
			});

			/**
			 * initializing global dialog box
			 */
			$('#dialogBox').dialog({
				autoOpen : false,
				modal : true,
				beforeClose : function(event, ui) {
					if (window.confirm("Do you want to close this window?")) {
						return true
					} else
						return false;

					return true;
				}
			});

			/**
			 * initializing global button theme
			 */
			$(".uiButton").button();

			/**
			 * intialize tabs globally
			 */
			$("#tabs").tabs();

			/*
			 * selecting a tab using a url parameter
			 */
			var param = $(document).getUrlParam('selectedTab');
			$('#tabs').tabs('select', parseFloat(param));

			/*
			 * handling the cancel button within the tab
			 */

			$(".tabCancel", ".tab").live('click', function() {
				var $tabs = $('#tabs');// .tabs();
				var selected = $tabs.tabs('option', 'selected');
				$tabs.tabs('load', selected);
			});

			/*
			 * vertial styling of the tabs
			 */
			$("#tabs").tabs().addClass('ui-tabs-vertical ui-helper-clearfix');
			$("#tabs li").removeClass('ui-corner-top').addClass(
					'ui-corner-left');

			/*
			 * every time the record table row is clicked, we trigger the radio
			 * button in the row to be clicked
			 */
			$("table#recordTable  tr").live(
					'click',
					function(event) {
						// get all rows with the .selectedRow css class
						$("table#recordTable tr.selectedRow").removeClass(
								"selectedRow");
						$("table#recordTable tr input:checked").attr("checked",
								false);
						$(this).addClass("selectedRow");

						if (event.target.type != 'radio') {
							$(':radio', this).trigger('click');
						}
					});

			$("table.recordTable tbody tr").live(
					'click',
					function(event) {

						if (event.target.type == 'checkbox') {
							if ($(':checkbox', this).attr('checked'))
								$(this).addClass("selected");
							else
								$(this).removeClass("selected");
						} else {
							var url = $(this).attr("url");
							if (url != null) {
								window.open(url);
							}

							if ($(':checkbox', this).attr('checked')
									|| $(this).hasClass('selected')) {
								$(this).removeClass("selected");
								$(':checkbox', this).attr('checked', false);
							} else {
								$(this).addClass("selected");
								$(':checkbox', this).attr('checked', true);
							}
						}
					});

			/**
			 * everytime the cbxSelectAllItems is selected, all rows in the same
			 * table should be selected.
			 */
			$("#cbxSelectAllItems").live('click', function(event) {
				if ($(this).attr("checked") == "checked") {
					$("table.recordTable tbody tr").each(function() {
						$(this).addClass("selected");
						$(":checkbox", $(this)).attr('checked', true);
					});
				} else {
					$("table.recordTable tbody tr").each(function() {
						$(this).removeClass("selected");
						$(":checkbox", this).attr('checked', false);
					});
				}
			});

			/**
			 * Scroll Bar
			 */
			/* zebra stripe the tables (not necessary for scrolling) */
			var tbl = $("table.recordTable");
			addZebraStripe(tbl);
			addMouseOver(tbl);

			$("table.scrollableTable").createScrollableTable({
				width : '1000px',
				height : '400px'
			});

			function addZebraStripe(table) {
				table.find("tbody tr:odd").addClass("alt");
			}

			function addMouseOver(table) {
				table.find("tbody tr").hover(function() {
					$(this).addClass("over");
				}, function() {
					$(this).removeClass("over");
				});
			}

		});