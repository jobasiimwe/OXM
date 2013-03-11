/**
 * 
 */
$(document)
		.ready(
				function() {
					$("#lnkDeleteProductionOrg")
							.click(
									function() {
										if ($(":checked",
												"table.recordTable tbody tr").length > 0) {
											if (window
													.confirm("Do you want to delete the Production Organization(s)?")) {
												$
														.ajax({
															type : 'POST',
															url : $(this).attr(
																	"href"),
															data : $(
																	"input[name='selectedPOrganization']")
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
											alert("please select a Production Organization and try again")
										}

										return false;
									});

					$("#lnkEditProductionOrg").click(function() {
						return singleItemAction('lnkEditProductionOrg');
					});

					function singleItemAction(id) {
						var numberSelected = $(":checked",
								"table.recordTable tbody tr").length;
						if (numberSelected > 0) {
							if (numberSelected == 1) {
								var url = $("#"+id).attr("href");
								url += $(":checked",
										"table.recordTable tbody tr").attr(
										"value");

								window.location = url;
							} else {
								alert("please select only one item and try again.")
							}
						} else {
							alert("please select an item and try again")
						}

						return false;
					}

					$("#lnkProducerOrgProducers").click(function() {
					return singleItemAction('lnkProducerOrgProducers');
				});
				});