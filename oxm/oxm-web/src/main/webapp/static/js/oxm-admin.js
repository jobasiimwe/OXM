/**
 * handles the farm scripting
 */
$(document)
		.ready(
				function() {

					/**
					 * single item functions
					 */
					$("a[id^='lnkEdit']," + "a[id^='lnkChild']").click(
							function() {
								return singleItemAction($(this).attr('id'), $(
										"#nameOfItemOnPage").attr("value"));
							});

					/**
					 * multiple item functions
					 */
					$("a[id^='lnkDelete'],").click(
							function() {
								return multipleItemAction($(this).attr('id'),
										$("#nameOfItemOnPage").attr("value"));
							});

					/**
					 * multiple item functions
					 */
					$("a[id^='deleteBtn'],").click(
							function() {
								return multipleItemAction($(this).attr('id'),
										$("#nameOfItemOnPage").attr("value"));
							});

					/**
					 * single item functions
					 */
					$(
							" #lnkPOrgDetails, #lnkPOrgMembers, #lnkPOrgDocs, "// pOrg
									+ " #lnkPOrgStaff, #lnkPOrgCommittee, "
									+ " #lnkDistrictCounties,"// district
									+ " #lnkCountySubCounties,#lnkSubCountyParishes,"// county-subCounty
									+ "  #lnkParishVillages,"// parish-village
									+ " #lnkPositionHolders,"
									+ " #lnkCropDetails, "
									+ " #lnkPOrgCommittees, ").click(
							function() {
								return singleItemAction($(this).attr('id'), $(
										"#nameOfItemOnPage").attr("value"));
							});

					/**
					 * these forms contain txtName
					 */
					$(
							"#btnSaveDistrict, #btnSaveCounty, #btnSaveSubCounty, #btnSaveParish, "
									+ "	#btnSaveVillage, #btnSavefInstitution, #btnSavePOrgCommittee")
							.click(function() {
								return hasText('txtName', "Name");
							});

					/**
					 * Price monitor functions
					 */
					$('#lnkAddPrice').click(
							function() {
								return singleDropDownItemAction('lnkAddPrice',
										'ddProducts', 'Product');
							});

					$('#btnSaveSellingPlace').click(function() {
						if (!hasText('txtName', "Name"))
							return false;
						if (!someAreChecked('sellingTypes', "Selling Types"))
							return false;

						return true;
					});

					$('#btnSaveRole').click(function() {
						if (!hasText('txtName', "Name"))
							return false;
						if (!hasText('txtDescription', "Description"))
							return false;

						return true;
					});

					$('#btnSaveUser').live('click', function() {
						return false;
					});

					$('#btnSavePOrgCommitteeMember')
							.live(
									'click',
									function() {
										var id = getSingleSelectedItem($(this)
												.attr('id'), $(
												"#nameOfItemOnPage").attr(
												"value"));
										var currentPositionHolder = $(
												'#txtPositionHolder').attr(
												'value');
										if (id == "multiple") {
											alert("Please select only one "
													+ $("#nameOfItemOnPage")
															.attr("value")
													+ " and try again.");
											return false;
										}

										if ((currentPositionHolder == "null" || currentPositionHolder == "")
												&& id == "none") {
											alert("Select a "
													+ $("#nameOfItemOnPage")
															.attr("value")
													+ " and try again");
											return false;
										}

										if (id != "none")
											$('#txtPositionHolder').attr(
													'value', id);
										return true;
									});

					$('#btnCancelUserEdit')
							.click(
									function() {
										var qStaffPage = $("#qStaffPage").val();
										var id = $("#id").val();
										// Cancel from profile page
										if (qStaffPage == "profilePage") {
											if (window
													.confirm('Do you want to cancel this operation?')) {
												window.location = "staff?action=view&item=staff&id="
														+ id;
												return true;
											} else {
												return false;
											}
										} else {
											if (window
													.confirm('Do you want to cancel this operation?')) {
												window.location = "staff?action=view";
												return true;
											} else {
												return false;
											}
										}
									});

					changeProducer();
					function changeProducer() {

						if ($('#userFormRoleList').length) {
							var rolePdocucerId = "4836AFAB-3D62-482c-BA9A-D9D15839C68A";
							var roleProducerChecked = false;

							$('#userFormRoleList :checked').each(function() {
								if ($(this).val() == rolePdocucerId) {
									roleProducerChecked = true;
								}
							});

							if (roleProducerChecked) {
								$("#ddProducerOrg, #txtLandSize").removeAttr(
										"disabled");

							} else {
								$("#ddProducerOrg, #txtLandSize").attr(
										"disabled", "disabled");
							}
						}
					}

					$(function() {
						$('#userFormRoleList input').click(function() {
							changeProducer();
						});
					});

					/**
					 * multiple item functions
					 */
					$("input[id^='multiBtnForm'],").click(
							function() {
								var url = getMultiButtonFormUrl("multiBtnForm",
										$(this).attr("id"));
								$("#multiBtnForm").attr("action", url);
								return true;

							});

					function getMultiButtonFormUrl(formId, btnId) {
						var url = $("#" + formId).attr("firstAction");

						if (isSystemBlank(url))
							url = $("#" + formId).attr("action");

						var task = $("#" + btnId).attr("task");

						if (task == "print")
							$("#multiBtnForm").attr("target", "_blank");
						else
							$("#multiBtnForm").attr("target", "");

						url = url + task;
						return url;
					}

					function checkLimitedLengthField(id) {

						var characters = $("#" + id).attr("maxlength");
						if (isSystemBlank(characters))
							characters = 200;

						var item = $("#" + id).attr("itemName");
						if (isSystemBlank(item))
							item = "This Field"

						if ($("#" + id).val().length > characters) {
							alert(item + " can not exceed " + characters);
							$("#" + id).val(
									$("#" + id).val().substr(0, characters));
						}
						var remaining = characters - $("#" + id).val().length;
						$("#counter").html(
								"(<strong>" + remaining + "</strong>)");
						if (remaining <= 10) {
							$("#counter").css("color", "red");
						} else {
							$("#counter").css("color", "black");
						}

					}

					pre_checkLimitedLengthField();

					function pre_checkLimitedLengthField() {
						var limitedlengthFields = $("*").find(
								'textarea[id^="limitedLength"]');

						limitedlengthFields.each(function() {
							var id = $(this).attr('id');
							checkLimitedLengthField(id)
						});
					}

					$('textarea[id^="limitedLength"]').keyup(function() {
						var id = $(this).attr('id');
						checkLimitedLengthField(id)
					});

					showLongResponseText();

					function showLongResponseText() {
						if ($('#longResponseText').length) {
							// var text1 = $('#longResponseText').text();
							// if (!isSystemBlank(text1))
							// alert(text1);

							var text = $('#longResponseText').text().trim();

							if (!isSystemBlank(text))
								alert(text);

							// $(".blog-text").html(text);
						}
					}
				});