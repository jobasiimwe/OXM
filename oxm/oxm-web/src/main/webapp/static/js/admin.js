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

/**
 * handles the farm scripting
 */
$(document).ready(
		function() {

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

			$("#lnkDeletePOrg").click(
					function() {
						return multipleItemAction('lnkDeletePOrg',
								"Producer-Organisation");
					});

			$("#lnkEditPOrg").click(
					function() {
						return singleItemAction('lnkEditPOrg',
								"Producer-Organisation");
					});

			$("#lnkPOrgProducers").click(
					function() {
						return singleItemAction('lnkPOrgProducers',
								"Producer-Organisation");
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
				return hasText('txtName', "Name");
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

			$('#lnkDeleteVillage').click(function() {
				return multipleItemAction('lnkDeleteVillage', "Village");
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
						return multipleItemAction('lnkDeletefInstitution',
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
			$('#lnkAddPrice').click(function() {

				var cropId = $('#ddCrops').attr("value");
				if (cropId == null || cropId == "") {
					alert("Select a crop to add a price");
					return false;
				} else {
					var href = $(this).attr("href");
					href = href + cropId;
					$(this).attr("href", href);

					return true;
				}

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
			$("#lnkEditSellingPlace").click(function() {
				return singleItemAction('lnkEditSellingPlace', "SellingPlace");
			});

			$("#lnkDeleteSellingPlace").click(
					function() {
						return multipleItemAction('lnkDeleteSellingPlace',
								"SellingPlace");
					});

			$('#btnSaveSellingPlace').click(function() {
				if (!hasText('txtName', "Name"))
					return false;
				if (!someAreChecked('sellingTypes', "Selling Types"))
					return false;

				return true;
			});

		});