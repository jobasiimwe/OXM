package org.agric.oxm.excelimport;

import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.excelimport.model.Column;
import org.agric.oxm.excelimport.model.ColumnStrings;
import org.agric.oxm.excelimport.model.MsExcelCell;


public class MsExcellTemplate {

	/**
	 * template name
	 */
	private String name;

	private List<String> columnNames = new ArrayList<String>();

	private List<ColumnStrings> columnStrings = new ArrayList<ColumnStrings>();
	/**
	 * array containing columns not expected to be null
	 */
	private List<Integer> noneNullColumns = new ArrayList<Integer>();

	/**
	 * array containing columns not expected to have Blank Cells
	 */
	private List<Integer> noneBlunkColumns = new ArrayList<Integer>();

	/**
	 * array containing columns not expected to have Formulae
	 */
	private List<Integer> onlyNumericOrBlankColumns = new ArrayList<Integer>();

	/**
	 * unique values expected
	 */
	private Integer uniqueColumns[];

	private Integer indexOfFirstDataRow = null;

	private List<Column> columns = new ArrayList<Column>();

	/**
	 * the default constructor
	 */
	public MsExcellTemplate() {

	}

	/**
	 * 
	 * Constructor for a template,
	 * 
	 * @param templateName
	 * @param columnNamesStr
	 *            String containing comma separated String names.
	 * @param columnNameSeparator
	 * @param noneNullColumns
	 * @param noneBlunkColumns
	 * @param uniqueColumns
	 * @param onlyNumericOrBlankColumns
	 * @param columnStrings
	 * @param indexOfFirstdataRow
	 */
	public MsExcellTemplate(String templateName, String columnNamesStr,
			String columnNameSeparator, List<Integer> noneNullColumns,
			List<Integer> noneBlunkColumns, Integer uniqueColumns[],
			List<Integer> onlyNumericOrBlankColumns,
			List<ColumnStrings> columnStrings, Integer indexOfFirstdataRow) {

		this.setName(templateName);
		this.setColumnNames(StringUtil.convertStringToList(columnNamesStr,
				columnNameSeparator));
		this.setColumnStrings(columnStrings);
		this.setOnlyNumericOrBlankColumns(onlyNumericOrBlankColumns);
		this.setUniqueColumns(uniqueColumns);
		this.setNoneNullColumns(noneNullColumns);
		this.setNoneBlunkColumns(noneBlunkColumns);
		this.setIndexOfFirstDataRow(indexOfFirstdataRow);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<Integer> getNoneNullColumns() {
		return noneNullColumns;
	}

	public void setNoneNullColumns(List<Integer> noneNullColumns) {
		this.noneNullColumns = noneNullColumns;
	}

	public List<Integer> getNoneBlunkColumns() {
		return noneBlunkColumns;
	}

	public void setNoneBlunkColumns(List<Integer> noneBlunkColumns) {
		this.noneBlunkColumns = noneBlunkColumns;
	}

	public Integer getIndexOfFirstDataRow() {
		return indexOfFirstDataRow;
	}

	public void setIndexOfFirstDataRow(Integer indexOfFirstDataRow) {
		this.indexOfFirstDataRow = indexOfFirstDataRow;
	}

	public List<ColumnStrings> getColumnStrings() {
		return columnStrings;
	}

	public void setColumnStrings(List<ColumnStrings> columnStrings) {
		this.columnStrings = columnStrings;
	}

	public Integer[] getUniqueColumns() {
		return uniqueColumns;
	}

	public void setUniqueColumns(Integer[] uniqueColumns) {
		this.uniqueColumns = uniqueColumns;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public void setOnlyNumericOrBlankColumns(
			List<Integer> onlyNumericOrBlankColumns) {
		this.onlyNumericOrBlankColumns = onlyNumericOrBlankColumns;
	}

	public List<Integer> getOnlyNumericOrBlankColumns() {
		return onlyNumericOrBlankColumns;
	}

	/**
	 * 
	 * @param columnIndex
	 * @return
	 */
	public String getColumnName(Integer columnIndex) {

		String columnName = "";
		if (columnIndex < columnNames.size()) {
			columnName = columnNames.get(columnIndex);

			if (columnName == null || columnName == "")
				columnName = getColumnLetterInMsExcel(columnIndex);
		} else
			columnName = getColumnLetterInMsExcel(columnIndex);

		return columnName;
	}

	/**
	 * method that retrieves the MS-Excel Column letters
	 * 
	 * @param columnIndex
	 * @return
	 */
	public String getColumnLetterInMsExcel(Integer columnIndex) {

		switch (columnIndex) {
		case 0:
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "I";
		case 9:
			return "J";
		case 10:
			return "K";
		case 11:
			return "K";
		case 12:
			return "M";
		case 13:
			return "N";
		case 14:
			return "O";
		case 15:
			return "P";
		case 16:
			return "Q";
		case 17:
			return "R";
		case 18:
			return "S";
		case 19:
			return "T";
		case 20:
			return "U";
		case 21:
			return "V";
		default:
			return "Column : " + columnIndex
					+ " most likely is outside the template area";
		}
	}

	public String getNullCellErrorMessage(MsExcelCell nullCell) {
		String nullCellError = String
				.format("\n Error: Null Cell (%s%s) detected at row - %s, for - %s in %s. \n",
						nullCell.getColumLetterInMSExcel(), nullCell.getRow(),
						nullCell.getRow(), nullCell.getTemplateColumnName(),
						getName());
		return nullCellError;
	}

	public String getBlankCellErrorMessage(MsExcelCell blankCell) {
		String error = String
				.format("\n Error: Blank Cell (%s%s) detected at row - %s, for - %s in %s. \n",
						blankCell.getColumLetterInMSExcel(),
						blankCell.getRow(), blankCell.getRow(),
						blankCell.getTemplateColumnName(), getName());
		return error;
	}

	public String getNoneNumericOrBlankCellErrorMessage(
			MsExcelCell noneNumericOrBlankCell) {
		String error = String
				.format("\n Error: invalid value (maybe formulae) found in Cell (%s%s) at row - %s, for - %s in %s. \n"
						+ "Remedy for fomulae: \n"
						+ "1. Select the affected cell(s)\n"
						+ "2. Edit- Copy - \n"
						+ "3. Paste Special - Select values -ok \n"
						+ "4. Save changes restart import process",
						noneNumericOrBlankCell.getColumLetterInMSExcel(),
						noneNumericOrBlankCell.getRow(),
						noneNumericOrBlankCell.getRow(),
						noneNumericOrBlankCell.getTemplateColumnName(),
						getName());
		return error;
	}

	/**
	 * creates the error message for invalid cell contents
	 * 
	 * @param unKnownTextCell
	 * @return
	 */
	public String getInvalidTextCellErrorMessage(MsExcelCell unKnownTextCell) {
		String error = String
				.format("\n Error: Invalid value found in Cell (%s%s), for - %s in %s. \n",
						unKnownTextCell.getColumLetterInMSExcel(),
						unKnownTextCell.getRow(),
						unKnownTextCell.getTemplateColumnName(), getName());
		if (unKnownTextCell.getContent() != null)
			if (unKnownTextCell.getContent() != "")
				error += unKnownTextCell.getContent();
		return error;
	}

	/**
	 * 
	 * @param duplicatedCells
	 * @return
	 */
	public String getDuplicateValueErrorMessage(
			List<MsExcelCell> duplicatedCells) {
		String duplicatedColumns = "";
		String duplicates = "";

		for (MsExcelCell cell : duplicatedCells) {
			String thisCell = String.format("[%s%s",
					cell.getColumLetterInMSExcel(), cell.getRow());
			if (cell.getDuplicates() != null)
				if (cell.getDuplicates().size() > 0) {

					for (MsExcelCell duplicateCell : cell.getDuplicates())
						thisCell += String.format(",%s%s",
								duplicateCell.getColumLetterInMSExcel(),
								duplicateCell.getRow());
					duplicates += thisCell
							+ String.format("]=%s  ", cell.getContent());
				}

			if (duplicatedColumns.indexOf(cell.getTemplateColumnName()) == -1) {
				if (duplicatedColumns == "")
					duplicatedColumns += cell.getTemplateColumnName();
				else
					duplicatedColumns += ", " + cell.getTemplateColumnName();
			}
		}
		String error = String.format(
				"\n Error: Duplicate value(s) (%s), for - %s in %s. \n",
				duplicates, duplicatedColumns, getName());
		return error;
	}

}
