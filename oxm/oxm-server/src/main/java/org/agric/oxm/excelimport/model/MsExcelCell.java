package org.agric.oxm.excelimport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents a Cell in a spreadsheet
 * 
 * @author Job
 * 
 */
public class MsExcelCell {

	private Integer row;
	private Integer colum;
	private String columLetterInMSExcel = "";

	private String templateColumnName = "";

	private String content;
	
	private List<MsExcelCell> duplicates = new ArrayList<MsExcelCell>();

	/**
	 * default constructor
	 */
	public MsExcelCell() {

	}

	/**
	 * constructor
	 * 
	 * @param row
	 * @param column
	 * @param columnLetter
	 * @param columnName
	 */
	public MsExcelCell(int row, int column, String columnLetter,
			String columnName) {

		this.setRow(row);
		this.setColum(column);
		this.setColumLetterInMSExcel(columnLetter);
		this.setTemplateColumnName(columnName);
	}

	/**
	 * constructor
	 * 
	 * @param row
	 * @param column
	 * @param columnLetter
	 * @param columnName
	 */
	public MsExcelCell(int row, int column, String columnLetter,
			String columnName, String content) {
		this.setRow(row);
		this.setColum(column);
		this.setColumLetterInMSExcel(columnLetter);
		this.setTemplateColumnName(columnName);
		this.setContent(content);
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public int getColum() {
		return colum;
	}

	public void setColum(Integer colum) {
		this.colum = colum;
	}

	public String getColumLetterInMSExcel() {
		return columLetterInMSExcel;
	}

	public void setColumLetterInMSExcel(String columLetterInMSExcel) {
		this.columLetterInMSExcel = columLetterInMSExcel;
	}

	public String getTemplateColumnName() {
		return templateColumnName;
	}

	public void setTemplateColumnName(String templateColumnName) {
		this.templateColumnName = templateColumnName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<MsExcelCell> getDuplicates() {
	    return duplicates;
	}

	public void setDuplicates(List<MsExcelCell> duplicates) {
	    this.duplicates = duplicates;
	}

	public void addDuplicate(MsExcelCell duplicateCell){
	    if(this.getDuplicates()==null)
		this.setDuplicates(new ArrayList<MsExcelCell>());
	    this.getDuplicates().add(duplicateCell);
	}
}
