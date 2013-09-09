/**
 * handles loading drop down elements by ajax
 */
$(document)
		.ready(
				function() {

					json1stLoad();

					function json1stLoad() {
						var idArray = new Array("jsondistrict", "jsoncounty",
								"jsonsubcounty", "jsonparish", "jsonvillage");

						for ( var i = 0; i < idArray.length; i++) {
							var id = idArray[i];
							if ($('#' + id).length) {

								var url = getJsonUrl(id, true);
								var firstElementBlank = isFirstElementBlank(id);
								var preselectedItem = $("#" + id).attr(
										"preselectedItem");

								fetchOptionValues(url, id, firstElementBlank,
										preselectedItem);
							}
						}

						return false;
					}

					// ==============================================================================================

					function isFirstElementBlank(id) {
						var firstElementBlank = false;
						if ($("#" + id).attr("firstElementBlank") == "true")
							firstElementBlank = true;
						return firstElementBlank;
					}

					function getJsonUrl(id, usePreselectedParent) {
						var url = $('#' + id).attr("url");

						var parent = $("#" + id).attr("parent");

						if (parent != null) {

							var id2Append = null;

							if (usePreselectedParent) {
								var preselectedParent = $("#" + parent).attr(
										"preselectedItem");
								if (!isSystemBlank(preselectedParent))
									id2Append = preselectedParent;
							} else {
								var selectedParent = $("#" + parent).attr(
										"value");
								if (!isSystemBlank(selectedParent))
									id2Append = selectedParent
							}

							if (isSystemBlank(id2Append))
								return null;
							else
								url = url + id2Append;
						}

						return url;
					}

					// ==============================================================================================

					$('select[id^="json"]').live("change", function() {
						// var id = $(this).attr('id');
						var child = $(this).attr("child");
						if (!isSystemBlank(child)) {
							loadValues(child)
						}
					});

					function loadValues(id) {
						var url = getJsonUrl(id, false);

						if (isSystemBlank(url))
							removeOptions(id);
						else {
							var firstElementBlank = isFirstElementBlank(id);

							fetchOptionValues(url, id, firstElementBlank, null);

							var child = $("#" + id).attr("child");

							removeOptions(child);
						}

						return false;
					}

					/**
					 * removes options of given id and all its children
					 */
					function removeOptions(id) {
						while (!isSystemBlank(id)) {
							$('#' + id).html('');
							id = $("#" + id).attr("child");
						}
					}

					// ==============================================================================================

					$('#ddPOrg-TriggersDistrictChange')
							.live(
									"change",
									function() {
										var id = $(this).attr('id');

										var district = $(
												"#" + id + " option:selected")
												.attr("district");
										if (isSystemBlank(district))
											district = "";
										$("#jsondistrict").attr(
												"preselectedItem", district);

										var county = $(
												"#" + id + " option:selected")
												.attr("county");
										if (isSystemBlank(county))
											county = "";
										$("#jsoncounty").attr(
												"preselectedItem", county);

										var subcounty = $(
												"#" + id + " option:selected")
												.attr("subcounty");
										if (isSystemBlank(subcounty))
											subcounty = "";
										$("#jsonsubcounty").attr(
												"preselectedItem", subcounty);

										var parish = $(
												"#" + id + " option:selected")
												.attr("parish");
										if (isSystemBlank(parish))
											parish = "";
										$("#jsonparish").attr(
												"preselectedItem", parish);

										var village = $(
												"#" + id + " option:selected")
												.attr("village");
										if (isSystemBlank(village))
											village = "";
										$("#jsonvillage").attr(
												"preselectedItem", village);

										json1stLoad();
									});

					// ==============================================================================================

					function fetchOptionValues(url, elementId,
							firstElementBlank, preselectedvalue) {
						if (elementId != null && url != null) {
							$
									.getJSON(
											url,
											function(data) {
												var items = [];

												if (data != null) {

													try {

														if (firstElementBlank)
															items
																	.push('<option value="none"></option>');

														$
																.each(
																		data,
																		function(
																				index,
																				childobject) {

																			if (!isSystemBlank(preselectedvalue)
																					&& childobject.value == preselectedvalue)
																				items
																						.push('<option value="'
																								+ childobject.value
																								+ '" selected="selected">'
																								+ childobject.text
																								+ '</option>');
																			else
																				items
																						.push('<option value="'
																								+ childobject.value
																								+ '">'
																								+ childobject.text
																								+ '</option>');
																		});

														$("#" + elementId)
																.html(
																		items
																				.join(''));
													} catch (err) {
														var txt = "Error: "
																+ err.message
																+ "\n\n";
														txt += "Click OK to continue.\n\n";
														// alert(txt);
													}
												}
											});
						} else {
							$("#" + elementId).html('');
						}
					}
				});