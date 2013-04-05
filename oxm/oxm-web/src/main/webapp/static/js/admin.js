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
 * handles the farm scripting
 */
$(document)
		.ready(
				function() {

					/**
					 * crop functions
					 */
					$('#lnkEditCrop').click(function() {
						return singleItemAction('lnkEditCrop', "Crop");
					});

					$('#lnkCropDetails').click(function() {
						return singleItemAction('lnkCropDetails', "Crop");
					});

					$('#lnkDeleteCrop').click(function() {
						return multipleItemAction('lnkDeleteCrop', "Crop");
					});

					/**
					 * producer organization functions
					 */

					$("#lnkDeletePOrg").click(
							function() {
								return multipleItemAction('lnkDeletePOrg',
										"Producer-Organisation");
							});

					$(
							"#lnkEditPOrg, #lnkPOrgDetails, #lnkPOrgMembers, #lnkPOrgDocs, #lnkPOrgStaff, #lnkPOrgCommittees, ")
							.click(
									function() {
										return singleItemAction($(this).attr(
												'id'), $("#nameOfItemOnPage").attr("value"));
									});

					$("#lnkEditPOrgProducer").click(
							function() {
								return singleItemAction('lnkEditPOrgProducer',
										"Producer");
							});

					/**
					 * position js
					 */

					$('#lnkEditPosition').click(function() {
						return singleItemAction('lnkEditPosition', "Position");
					});

					$('#lnkPositionHolders').click(
							function() {
								return singleItemAction('lnkPositionHolders',
										"Position");
							});

					$('#lnkDeletePosition').click(
							function() {
								return multipleItemAction('lnkDeletePosition',
										"Position");
							});

					/**
					 * district functions
					 */
					$('#lnkEditDistrict').click(function() {
						return singleItemAction('lnkEditDistrict', "District");
					});

					$('#lnkDistrictSubCounties').click(
							function() {
								return singleItemAction(
										'lnkDistrictSubCounties', "District");
							});

					$('#lnkDeleteDistrict').click(
							function() {
								return multipleItemAction('lnkDeleteDistrict',
										"District");
							});

					$('#btnSaveDistrict').click(function() {
						return hasText('txtName', "Name");
					});

					/**
					 * subCounty functions
					 */
					$('#lnkEditSubCounty').click(
							function() {
								return singleItemAction('lnkEditSubCounty',
										"SubCounty");
							});

					$('#lnkSubCountyParishes').click(
							function() {
								return singleItemAction('lnkSubCountyParishes',
										"SubCounty");
							});

					$('#lnkDeleteSubCounty').click(
							function() {
								return multipleItemAction('lnkDeleteSubCounty',
										"SubCounty");
							});

					$('#btnSaveSubCounty').click(function() {
						return hasText('txtName', "Name");
					});

					/**
					 * parish functions
					 */
					$('#lnkEditParish').click(function() {
						return singleItemAction('lnkEditParish', "Parish");
					});

					$('#lnkParishVillages').click(function() {
						return singleItemAction('lnkParishVillages', "Parish");
					});

					$('#lnkDeleteParish').click(function() {
						return multipleItemAction('lnkDeleteParish', "Parish");
					});

					$('#btnSaveParish').click(function() {
						return hasText('txtName', "Name");
					});

					/**
					 * village functions
					 */
					$('#lnkEditVillage').click(function() {
						return singleItemAction('lnkEditVillage', "Village");
					});

					$('#lnkDeleteVillage').click(
							function() {
								return multipleItemAction('lnkDeleteVillage',
										"Village");
							});

					$('#btnSaveVillage').click(function() {
						return hasText('txtName', "Name");
					});

					/**
					 * Financial-Institution functions
					 */
					$('#lnkEditfInstitution').click(
							function() {
								return singleItemAction('lnkEditfInstitution',
										"Financial-Institution");
							});

					$('#lnkDeletefInstitution').click(
							function() {
								return multipleItemAction(
										'lnkDeletefInstitution',
										"Financial-Institution");
							});

					$('#btnSavefInstitution').click(function() {
						return hasText('txtName', "Name");
					});

					/**
					 * Season functions
					 */

					$("#lnkEditSeason").click(function() {
						return singleItemAction('lnkEditSeason', "Season");
					});

					$("#lnkDeleteSeason").click(function() {
						return multipleItemAction('lnkDeleteSeason', "Season");
					});

					/**
					 * Price monitor functions
					 */
					$('#lnkAddPrice').click(
							function() {
								return singleDropDownItemAction('lnkAddPrice',
										'ddCrops', 'crop');
							});

					$("#lnkEditPrice").click(function() {
						return singleItemAction('lnkEditPrice', "Price");
					});

					$("#lnkDeletePrice").click(function() {
						return multipleItemAction('lnkDeletePrice', "Price");
					});

					/**
					 * Selling place functions
					 */
					$("#lnkEditSellingPlace").click(
							function() {
								return singleItemAction('lnkEditSellingPlace',
										"SellingPlace");
							});

					$("#lnkDeleteSellingPlace")
							.click(
									function() {
										return multipleItemAction(
												'lnkDeleteSellingPlace',
												"SellingPlace");
									});

					$('#btnSaveSellingPlace').click(function() {
						if (!hasText('txtName', "Name"))
							return false;
						if (!someAreChecked('sellingTypes', "Selling Types"))
							return false;

						return true;
					});

					/**
					 * Role js
					 */
					$("#lnkEditRole").click(function() {
						return singleItemAction('lnkEditRole', "Role");
					});

					$("#lnkDeleteRole").click(function() {
						return multipleItemAction('lnkDeleteRole', "Role");
					});

					$('#btnSaveRole').click(function() {
						if (!hasText('txtName', "Name"))
							return false;
						if (!hasText('txtDescription', "Description"))
							return false;

						return true;
					});

					/**
					 * Users js
					 */
					$("#lnkEditUser").click(function() {
						return singleItemAction('lnkEditUser', "User");
					});

					$("#lnkDeleteUser").click(function() {
						return multipleItemAction('lnkDeleteUser', "User");
					});

					$('#btnSaveUser').live('click', function() {
						return false;
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

					/**
					 * concepts js
					 */
					$("#lnkEditConcept").click(function() {
						return singleItemAction('lnkEditConcept', "Concept");
					});

					$("#lnkDeleteConcept").click(
							function() {
								return multipleItemAction('lnkDeleteConcept',
										"Concept");
							});
				});