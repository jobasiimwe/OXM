package org.agric.oxm.excelimport.model;

import java.util.List;

public class Value {

	private Integer columnIndex;

	private String stringValue = "";

	List<Column> dependantColumns;

	public Value(Integer index, String stringValue,
			Integer dependantColumnIndex,
			String longStringOfDependantColumnValues) {
		this.setColumnIndex(index);
		this.setStringValue(stringValue);

	}

	public Value(int index, String stringValue) {
		this.setColumnIndex(index);
		this.setStringValue(stringValue);
	}

	public Integer getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public List<Column> getDependantColumns() {
		return dependantColumns;
	}

	public void setDependantColumns(List<Column> dependantColumns) {
		this.dependantColumns = dependantColumns;
	}

}
