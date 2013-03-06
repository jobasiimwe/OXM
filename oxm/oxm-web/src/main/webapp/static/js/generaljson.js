/**
 * handles loading drop down elements by ajax
 */
$(document).ready(
		function() {

			function fetchChildObjects(url, elementId) {
				if (elementId != null && url != null) {
					$.getJSON(url, function(data) {
						var items = [];

						if (data != null) {
							// items.push('<option value=""></option>');
							$.each(data, function(index, childobject) {
								items
										.push('<option value="'
												+ childobject.id + '">'
												+ childobject.name
												+ '</option>');
							});

							$("#" + elementId).html(items.join(''));
						}
					});
				} else {
					$("#" + elementId).html('');
				}
			}

			$('#dddistrict').live("change", function() {
				loadSubCounties();
			});

			function loadSubCounties() {
				var districtId = $("#dddistrict").attr("value");
				var districtId = $.trim(districtId);
				if (districtId == '') {
					alert("Select a district first");
					return false;
				}
				var url = getBaseURL() + 'get/json/subcounties/' + districtId;
				fetchChildObjects(url, "ddsubcounty")
				
				$("#ddparish").html('');
				$("#ddvillageLc1Zone").html('');
				return false;
			}

			$('#ddsubcounty').live("change", function() {
				loadParishes();
			});

			function loadParishes() {
				var subcountyId = $("#ddsubcounty").attr("value");
				var subcountyId = $.trim(subcountyId);
				if (subcountyId == '') {
					alert("Select a sub-county first");
					return false;
				}
				var url = getBaseURL() + 'get/json/parishes/' + subcountyId;
				fetchChildObjects(url, "ddparish")
				
				$("#ddvillageLc1Zone").html('');
				return false;
			}

			$('#ddparish').live("focus", function() {
				loadVillages();
			});

			$('#ddparish').live("change", function() {
				loadVillages();
			});

			function loadVillages() {
				var parishId = $("#ddparish").attr("value");
				var parishId = $.trim(parishId);
				if (parishId == '') {
					alert("Select a parish first");
					return false;
				}
				var url = getBaseURL() + 'get/json/villages/' + parishId;
				fetchChildObjects(url, "ddvillageLc1Zone")
				return false;
			}
		});