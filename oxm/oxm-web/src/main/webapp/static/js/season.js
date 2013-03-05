/**
 * 
 */
$(document)
		.ready(
				function() {
					$("#lnkDeleteSeason")
							.click(
									function() {
										if ($(":checked",
												"table.recordTable tbody tr").length > 0) {
											if (window
													.confirm("Do you want to delete the Season(s)?")) {
												$
														.ajax({
															type : 'POST',
															url : $(this).attr(
																	"href"),
															data : $(
																	"input[name='selectedSeason']")
																	.serialize(),
															success : function(
																	data,
																	textStatus,
																	xmlHttpRequest) {
																$(":checked",
																		"table.recordTable tbody tr")
																		.each(
																				function() {
																					var id = $(
																							this)
																							.attr(
																									"value");
																					$(
																							"table.recordTable tbody tr#"
																									+ id)
																							.remove();
																				});

																setSystemMessage(xmlHttpRequest.responseText);
															}
														});
											}
										} else {
											alert("please select a Season and try again")
										}

										return false;
									});

					$("#lnkEditSeason")
							.click(
									function() {
										var numberSelected = $(":checked",
												"table.recordTable tbody tr").length;
										if (numberSelected > 0) {
											if (numberSelected == 1) {
												var url = $(this).attr("href");
												url += $(":checked",
														"table.recordTable tbody tr")
														.attr("value");

												window.location = url;
											} else {
												alert("please select only one Season and try again.")
											}
										} else {
											alert("please select a Season and try again")
										}

										return false;
									});
				});