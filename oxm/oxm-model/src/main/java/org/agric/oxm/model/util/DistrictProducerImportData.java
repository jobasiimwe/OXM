package org.agric.oxm.model.util;

import org.agric.oxm.model.enums.ImportItem;

public class DistrictProducerImportData {

	private ImportItem importItem;

	public DistrictProducerImportData() {

	}

	public DistrictProducerImportData(ImportItem importItem) {
		this.setImportItem(importItem);
	}

	public ImportItem getImportItem() {
		return importItem;
	}

	public void setImportItem(ImportItem importItem) {
		this.importItem = importItem;
	}

	public static final int rowToBeginCheckingAt = 1;

	public static final int enteredColumnIndex = 0;
	public static final int enteredByColumnIndex = 1;
	public static final int beneHHNoColumnIndex = 2;
	public static final int staffResponsibleColumnIndex = 3;
	public static final int dateOfRegColumnIndex = 4;
	public static final int dateOfEntryColumnIndex = 5;
	public static final int districtColumnIndex = 6;
	public static final int countyColumnIndex = 7;
	public static final int subcountyColumnIndex = 8;
	public static final int parishColumnIndex = 9;
	public static final int villageColumnIndex = 10;
	public static final int porgNameColumnIndex = 11;
	public static final int farmerNameColumnIndex = 12;
	public static final int genderColumnIndex = 13;
	public static final int ageColumnIndex = 14;
	public static final int dateOfJoiningColumnIndex = 15;
	public static final int maritalStatusColumnIndex = 16;
	public static final int categoryOfHHColumnIndex = 17;
}
