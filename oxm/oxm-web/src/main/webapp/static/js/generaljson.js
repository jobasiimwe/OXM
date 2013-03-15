/**
 * handles loading drop down elements by ajax
 */
$(document)
		.ready(
				function() {

					function fetchChildObjects(url, elementId,
							firstElementBlank, preselectedvalueid) {
						if (elementId != null && url != null) {
							$
									.getJSON(
											url,
											function(data) {
												var items = [];

												if (data != null) {

													var preselectedvalue = null;
													if (preselectedvalueid != null)
														if ($("#"
																+ preselectedvalueid).length)
															preselectedvalue = $(
																	"#"
																			+ preselectedvalueid)
																	.attr(
																			"value");

													if (firstElementBlank)
														items
																.push('<option value="none"></option>');

													$
															.each(
																	data,
																	function(
																			index,
																			childobject) {

																		if (preselectedvalue != null
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

													$("#" + elementId).html(
															items.join(''));
												}
											});
						} else {
							$("#" + elementId).html('');
						}
					}

					loadDistricts()

					function loadDistricts() {
						var url = getBaseURL() + 'get/json/districts';
						if ($('#dddistrict').length) {
							fetchChildObjects(url, "dddistrict", false,
									"preselectedDistrictId");
						}
						if ($('#dddistrictwithblank').length) {
							fetchChildObjects(url, "dddistrictwithblank", true,
									"preselectedDistrictId");
						}
						return false;
					}

					$('#dddistrict').live("change", function() {
						loadSubCounties();
					});

					$('#dddistrictwithblank').live("change", function() {
						loadSubCounties();
					});

					// loadSubCounties();

					function loadSubCounties() {
						var districtId = ""
						if ($('#dddistrict').length)
							var districtId = $("#dddistrict").attr("value");
						if ($('#dddistrictwithblank').length)
							var districtId = $("#dddistrictwithblank").attr(
									"value");

						var districtId = $.trim(districtId);
						if (districtId != '') {
							var url = getBaseURL() + 'get/json/subcounties/'
									+ districtId;
							if ($('#ddsubcounty').length) {
								fetchChildObjects(url, "ddsubcounty", false,
										"preselectedSubCountyId");
							}
							if ($('#ddsubcountywithblank').length) {
								fetchChildObjects(url, "ddsubcountywithblank",
										true, "preselectedSubCountyId");
							}
						}

						$("#ddparish").html('');
						$("#ddparishwithblank").html('');
						$("#ddvillage").html('');
						$("#ddvillagewithblank").html('');

						return false;
					}

					$('#ddsubcounty').live("change", function() {
						loadParishes();
					});
					$('#ddsubcountywithblank').live("change", function() {
						loadParishes();
					});

					function loadParishes() {
						var subcountyId = "";

						if ($('#ddsubcounty').length)
							var subcountyId = $("#ddsubcounty").attr("value");
						if ($('#ddsubcountywithblank').length)
							var subcountyId = $("#ddsubcountywithblank").attr(
									"value");

						var subcountyId = $.trim(subcountyId);
						if (subcountyId != '') {
							var url = getBaseURL() + 'get/json/parishes/'
									+ subcountyId;
							if ($('#ddparish').length) {
								fetchChildObjects(url, "ddparish", false,
										"preselectedParishId");
							}
							if ($('#ddparishwithblank').length) {
								fetchChildObjects(url, "ddparishwithblank",
										true, "preselectedParishId");
							}
						}

						$("#ddvillage").html('');
						$("#ddvillagewithblank").html('');

						return false;
					}

					$('#ddparish').live("focus", function() {
						loadVillages();
					});

					$('#ddparishwithblank').live("change", function() {
						loadVillages();
					});

					function loadVillages() {
						var parishId = "";

						if ($('#ddparish').length)
							var parishId = $("#ddparish").attr("value");
						if ($('#ddparishwithblank').length)
							var parishId = $("#ddparishwithblank")
									.attr("value");

						var parishId = $.trim(parishId);
						if (parishId != '') {
							var url = getBaseURL() + 'get/json/villages/'
									+ parishId;
							if ($('#ddvillage').length) {
								fetchChildObjects(url, "ddvillage", false,
										"preselectedVillageId");
							}
							if ($('#ddvillagewithblank').length) {
								fetchChildObjects(url, "ddvillagewithblank",
										true, "preselectedVillageId");
							}
						}

						$("#ddvillage").html('');
						$("#ddvillagewithblank").html('');
						return false;
					}
				});