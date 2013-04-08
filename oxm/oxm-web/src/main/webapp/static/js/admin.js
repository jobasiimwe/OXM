/**
 * handles the farm scripting
 */
$(document)
		.ready(
				function() {

					/**
					 * producerOrg
					 */
					$(
							"#lnkEditPOrg, #lnkPOrgDetails, #lnkPOrgMembers, #lnkPOrgDocs, "// pOrg
									+ " #lnkPOrgStaff, #lnkPOrgCommittee, "
									+ " #lnkEditPOrgCommittee, lnkEditPOrgMember, "
									+ " #lnkEditDistrict, #lnkDistrictSubCounties, #lnkEditSubCounty, "// district
									+ " #lnkSubCountyParishes,  #lnkEditParish, #lnkParishVillages, "
									+ " #lnkEditVillage, "
									+ " #lnkEditPosition, #lnkPositionHolders,"
									+ " #lnkEditfInstitution, #lnkEditSeason, #lnkEditCrop, "
									+ " #lnkCropDetails, #lnkEditPrice, #lnkEditSellingPlace, "
									+ " #lnkEditRole, #lnkEditUser, #lnkEditConcept, "
									+ " #lnkEditPOrgCommittee, #lnkPOrgCommitteeMembers, #lnkEditPOrgCommitteeMember")
							.click(
									function() {
										return singleItemAction($(this).attr(
												'id'), $("#nameOfItemOnPage")
												.attr("value"));
									});

					$(
							"#lnkDeletePOrg, #lnkDeletePOrgMember, #lnkDeletePOrgDocs, "
									+ " #lnkDeletePOrgStaff, #lnkDeletePOrgCommittee, "
									+ " #lnkDeleteDistrict, #lnkDeleteSubCounty," // district
									+ " #lnkDeleteParish, #lnkDeleteVillage, "
									+ " #lnkDeletePOrg, #lnkDeletePosition, #lnkDeletefInstitution, "
									+ " #lnkDeleteSeason, #lnkDeleteCrop, #lnkDeletePrice, "
									+ " #lnkDeleteSellingPlace, #lnkDeleteRole, #lnkDeleteUser, "
									+ " #lnkDeleteConcept,"
									+ " #lnkDeletePOrgCommittee, #lnkDeletePOrgCommitteeMember,").click(
							function() {
								return multipleItemAction($(this).attr('id'),
										$("#nameOfItemOnPage").attr("value"));
							});

					/**
					 * these forms contain txtName
					 */
					$(
							"#btnSaveDistrict, #btnSaveSubCounty, #btnSaveParish, "
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
										'ddCrops', 'crop');
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

				});