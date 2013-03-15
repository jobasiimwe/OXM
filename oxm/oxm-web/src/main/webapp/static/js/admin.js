function isNotBlank(id, fieldName) {
	var value = $.trim($("#" + id).val());
	if (value == null || value == "") {
		alert(fieldName + " is required!!");
		return false;
	}

	return true;
}

/**
 * handles the farm scripting
 */
$(document).ready(function() {

	/**
	 * crop functions
	 */
	$('#btnEditCrop').click(function() {
		return singleItemAction('btnEditCrop', "Crop");
	});

	$('#btnCropDetails').click(function() {
		return singleItemAction('btnCropDetails', "Crop");
	});

	$('#btnDeleteCrop').click(function() {
		return multipleItemAction('btnDeleteCrop', "Crop");
	});

	/**
	 * producer organization functions
	 */

	$("#lnkDeletePOrg").click(function() {
		return multipleItemAction('lnkDeletePOrg', "Producer-Organisation");
	});

	$("#lnkEditPOrg").click(function() {
		return singleItemAction('lnkEditPOrg', "Producer-Organisation");
	});

	$("#lnkPOrgProducers").click(function() {
		return singleItemAction('lnkPOrgProducers', "Producer-Organisation");
	});

	$("#lnkEditPOrgProducer").click(function() {
		return singleItemAction('lnkEditPOrgProducer', "Producer");
	});

	/**
	 * position js
	 */

	$('#btnEditPosition').click(function() {
		return singleItemAction('btnEditPosition', "Position");
	});

	$('#btnPositionHolders').click(function() {
		return singleItemAction('btnPositionHolders', "Position");
	});

	$('#btnDeletePosition').click(function() {
		return multipleItemAction('btnDeletePosition', "Position");
	});

	/**
	 * district functions
	 */
	$('#lnkEditDistrict').click(function() {
		return singleItemAction('lnkEditDistrict', "District");
	});

	$('#lnkDistrictSubCounties').click(function() {
		return singleItemAction('lnkDistrictSubCounties', "District");
	});

	$('#lnkDeleteDistrict').click(function() {
		return multipleItemAction('lnkDeleteDistrict', "District");
	});

	$('#btnSaveDistrict').click(function() {
		return isNotBlank('txtName', "Name");
	});

	/**
	 * subCounty functions
	 */
	$('#lnkEditSubCounty').click(function() {
		return singleItemAction('lnkEditSubCounty', "SubCounty");
	});

	$('#lnkSubCountyParishes').click(function() {
		return singleItemAction('lnkSubCountyParishes', "SubCounty");
	});

	$('#lnkDeleteSubCounty').click(function() {
		return multipleItemAction('lnkDeleteSubCounty', "SubCounty");
	});

	$('#btnSaveSubCounty').click(function() {
		return isNotBlank('txtName', "Name");
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
		return isNotBlank('txtName', "Name");
	});

	/**
	 * village functions
	 */
	$('#lnkEditVillage').click(function() {
		return singleItemAction('lnkEditVillage', "Village");
	});

	$('#lnkDeleteVillage').click(function() {
		return multipleItemAction('lnkDeleteVillage', "Village");
	});

	$('#btnSaveVillage').click(function() {
		return isNotBlank('txtName', "Name");
	});
	
	/**
	 * village functions
	 */
	$('#lnkEditfInstitution').click(function() {
		return singleItemAction('lnkEditfInstitution', "Financial-Institution");
	});

	$('#lnkDeletefInstitution').click(function() {
		return multipleItemAction('lnkDeletefInstitution', "Financial-Institution");
	});

	$('#btnSavefInstitution').click(function() {
		return isNotBlank('txtName', "Name");
	});
});