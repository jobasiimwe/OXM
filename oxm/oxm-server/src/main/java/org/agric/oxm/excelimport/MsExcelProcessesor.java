package org.agric.oxm.excelimport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.agric.oxm.excelimport.model.ColumnStrings;
import org.agric.oxm.excelimport.model.MsExcelCell;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class that takes an excell template specification and a workbook and does
 * tests on the workbook
 * 
 * @author Job
 * 
 */
public class MsExcelProcessesor {

	private static Logger log = LoggerFactory
			.getLogger(MsExcelProcessesor.class);

	private boolean processing2003;

	private HSSFWorkbook workBook;

	private HSSFSheet sheet;

	private MsExcellTemplate template;

	private XSSFWorkbook xssfWorkBook;

	private XSSFSheet xssfSheet;

	public MsExcelProcessesor(XSSFWorkbook xssfWorkbook, XSSFSheet xssfSheet,
			MsExcellTemplate template2) {
		this.setXssfSheet(xssfSheet);
		this.setXssfWorkBook(xssfWorkbook);
		this.setTemplate(template2);
		this.setProcessing2003(false);
	}

	public MsExcelProcessesor(HSSFWorkbook wb, HSSFSheet sheet,
			MsExcellTemplate msExcellTemplate) {
		this.setWorkBook(wb);
		this.setSheet(sheet);
		this.setTemplate(msExcellTemplate);
		this.setProcessing2003(true);
	}

	public boolean isProcessing2003() {
		return processing2003;
	}

	public void setProcessing2003(boolean processing2003) {
		this.processing2003 = processing2003;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public MsExcellTemplate getTemplate() {
		return template;
	}

	public void setTemplate(MsExcellTemplate template) {
		this.template = template;
	}

	public void setXssfSheet(XSSFSheet xssfSheet) {
		this.xssfSheet = xssfSheet;
	}

	public XSSFSheet getXssfSheet() {
		return xssfSheet;
	}

	public void setXssfWorkBook(XSSFWorkbook xssfWorkBook) {
		this.xssfWorkBook = xssfWorkBook;
	}

	public XSSFWorkbook getXssfWorkBook() {
		return xssfWorkBook;
	}

	private Iterator<Row> getSheetIterator() {
		if (isProcessing2003())
			return sheet.rowIterator();
		else
			return xssfSheet.rowIterator();
	}

	public MsExcelCell checkTemplateForNullCells() {
		return getNullCellAmongThese(template.getNoneNullColumns(),
				template.getIndexOfFirstDataRow());
	}

	public MsExcelCell checkTemplateForBlankCells() {
		return getBlankCellAmongThese(template.getNoneBlunkColumns(),
				template.getIndexOfFirstDataRow());
	}

	public MsExcelCell checkTemplateCellsThatOnlyAllowNumericOrBlankValues() {
		return getNoneNumericAndNoneBlankCellAmongThese(
				template.getOnlyNumericOrBlankColumns(),
				template.getIndexOfFirstDataRow());
	}

	public MsExcelCell checkTemplateForInvalidValues() {
		if (null != template.getColumnStrings())
			if (template.getColumnStrings().size() > 0) {
				MsExcelCell offendingCell = new MsExcelCell();
				for (ColumnStrings columnStrings : template.getColumnStrings()) {
					offendingCell = ColumnCellsContainValidStrings(
							columnStrings.getColumn(),
							columnStrings.getStrings(),
							template.getIndexOfFirstDataRow());
					if (offendingCell != null)
						if (offendingCell.getColumLetterInMSExcel() != null)
							if (offendingCell.getColumLetterInMSExcel() != "")
								return offendingCell;

				}

			}
		return null;
	}

	public List<MsExcelCell> checkTemplatesUniqueColumns() {
		if (null != template.getUniqueColumns()) {
			Integer uniqueColumns[] = template.getUniqueColumns();
			List<MsExcelCell> offendingCells = new ArrayList<MsExcelCell>();
			for (int i = 0; i < uniqueColumns.length; i++) {
				offendingCells = columnDuplicates(uniqueColumns[i],
						template.getIndexOfFirstDataRow());
				if (null != offendingCells)
					return offendingCells;
			}
		}
		return null;
	}

	/**
	 * 
	 * checks to ensure that the specified cells are not null
	 * 
	 * @param cellsToCheck
	 * @param rowToBeginCheckingAt
	 * @return
	 */
	public MsExcelCell getNullCellAmongThese(List<Integer> cellsToCheck,
			int rowToBeginCheckingAt) {

		String cells2Check = "";
		for (int i = 0; i < cellsToCheck.size(); i++)
			cells2Check = cells2Check.concat(" " + cellsToCheck.get(i));

		String message = "Checking for NULL among these cells " + cells2Check;
		log.info(message);

		int rowIndex = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < rowToBeginCheckingAt) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row row = rit.next();

			for (int i = 0; i < cellsToCheck.size(); i++) {

				if (null == row.getCell(cellsToCheck.get(i))) {
					return getCellDetails(row.getRowNum() + 1,
							cellsToCheck.get(i));
				}
			}
		}

		log.info("Success: No NULL cell found");
		return null;
	}

	/**
	 * 
	 * checks to ensure that the specified cells are not BLANK
	 * 
	 * @param cellsToCheck
	 * @param rowToBeginCheckingAt
	 * @return
	 */
	public MsExcelCell getBlankCellAmongThese(List<Integer> cellsToCheck,
			int rowToBeginCheckingAt) {

		int rowIndex = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < rowToBeginCheckingAt) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row row = rit.next();

			// boolean noneNullCellFound = false;
			for (int i = 0; i < cellsToCheck.size(); i++) {

				// if(row.getCell(cellsToCheck[i]).getCellType()==Cell.CELL_TYPE_BLANK)
				// log.info("blank cell found");

				if (null != row.getCell(cellsToCheck.get(i)))
					if (row.getCell(cellsToCheck.get(i)).getCellType() == Cell.CELL_TYPE_BLANK) {
						MsExcelCell blankCell = new MsExcelCell(
								row.getRowNum() + 1, cellsToCheck.get(i),
								template.getColumnLetterInMsExcel(cellsToCheck
										.get(i)),
								template.getColumnName(cellsToCheck.get(i)));
						log.info("Error: BLANK cell found");
						return blankCell;
					}

				if (row.getCell(cellsToCheck.get(i)).getCellType() == Cell.CELL_TYPE_STRING) {
					if (StringUtils.isBlank(row.getCell(cellsToCheck.get(i))
							.getStringCellValue().trim())) {
						MsExcelCell blankCell = new MsExcelCell(
								row.getRowNum() + 1, cellsToCheck.get(i),
								template.getColumnLetterInMsExcel(cellsToCheck
										.get(i)),
								template.getColumnName(cellsToCheck.get(i)));
						log.info("Error: BLANK cell found");
						return blankCell;
					}
				}
			}
		}

		log.info("Success: No BLANK cell found in " + template.getName());
		return null;
	}

	/**
	 * 
	 * checks to ensure that the specified cells are not null
	 * 
	 * @param cellsToCheck
	 * @param rowToBeginCheckingAt
	 * @return
	 */
	public MsExcelCell getNoneNumericAndNoneBlankCellAmongThese(
			List<Integer> cellsToCheck, Integer rowToBeginCheckingAt) {

		String cells2Check = "";
		for (int i = 0; i < cellsToCheck.size(); i++)
			cells2Check = cells2Check.concat(" " + cellsToCheck.get(i));

		int rowIndex = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < rowToBeginCheckingAt) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row row = rit.next();

			for (int i = 0; i < cellsToCheck.size(); i++) {

				if (null == row.getCell(cellsToCheck.get(i)))
					continue;

				if (!(row.getCell(cellsToCheck.get(i)).getCellType() == Cell.CELL_TYPE_NUMERIC)
						&& !(row.getCell(cellsToCheck.get(i)).getCellType() == Cell.CELL_TYPE_BLANK)) {
					log.error("Found a none numeric and none blank cell in the excell template");
					return getCellDetails(row.getRowNum() + 1,
							cellsToCheck.get(i));
				}
			}
		}

		log.info("No None Numeric None Blank CELL found");
		return null;
	}

	/**
	 * checks to ensure cells of a given column contain particular strings e.g
	 * "M" and "F" for sex
	 * 
	 * @param columnIndex
	 * @param validStrings
	 * @param rowToBeginCheckingAt
	 * @return
	 */
	public MsExcelCell ColumnCellsContainValidStrings(int columnIndex,
			List<String> validStrings, int rowToBeginCheckingAt) {

		int rowIndex = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < rowToBeginCheckingAt) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row row = rit.next();
			String cellValue = row.getCell(columnIndex).getStringCellValue()
					.toLowerCase();
			boolean equivalentStringFound = false;
			for (String validString : validStrings) {
				validString = validString.toLowerCase();
				if (cellValue.equals(validString)) {
					equivalentStringFound = true;
					break;
				}
			}
			if (!equivalentStringFound) {
				log.info("Error: Invalid String found");
				return getCellDetails(
						row.getRowNum(),
						columnIndex,
						"<br><b>Cell Value:</b> \""
								+ cellValue
								+ "\" <br><b>Expected:</b> "
								+ StringUtil.convertListToString(validStrings,
										", "));
			}

		}// end of for loop

		return null;

	}

	/**
	 * get Cell details of a given Cell for the known templates
	 * 
	 * @param rowIndex
	 * @param columIndex
	 * @return
	 */
	public MsExcelCell getCellDetails(int rowIndex, int columIndex) {
		MsExcelCell cell = new MsExcelCell();

		cell.setColum(columIndex);
		cell.setRow(rowIndex + 1);
		cell.setColumLetterInMSExcel(template
				.getColumnLetterInMsExcel(columIndex));
		cell.setTemplateColumnName(template.getColumnName(columIndex));

		return cell;
	}

	public MsExcelCell getCellDetails(int rowIndex, int columIndex,
			String content) {
		MsExcelCell cell = getCellDetails(rowIndex, columIndex);
		cell.setContent(content);
		return cell;
	}

	/**
	 * checks particular cell value is @param value (used to verify
	 * course-unit-code)
	 * 
	 * @param cellIndex
	 * @param row
	 * @param value
	 * @return
	 */
	public boolean ParticularCellStringIs(int cellIndex, int row,
			String valueRequired) {
		int rowIndex = 0;
		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < row) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row rowInQuestion = rit.next();
			if (rowInQuestion.getCell(cellIndex) == null)
				return false;
			if (rowInQuestion.getCell(cellIndex).getStringCellValue()
					.equals(valueRequired))
				return true;
			else
				return false;
		}

		return false;
	}

	/**
	 * checks particular cell value contains
	 * 
	 * @param cellIndex
	 * @param row
	 * @param valueRequired
	 * @return
	 */
	public boolean ParticularCellStringContains(int cellIndex, int row,
			String valueRequired) {
		int rowIndex = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < row) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row rowInQuestion = rit.next();
			if (rowInQuestion.getCell(cellIndex).getStringCellValue()
					.contains(valueRequired))
				return true;
			else
				return false;
		}

		return false;

	}

	/**
	 * checks to ensure a given column of the worksheet doesn't have duplicates
	 * i.e all provided values are unique
	 * 
	 * @param columnIndex
	 * @param rowToBeginCheckingAt
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<MsExcelCell> columnDuplicates(int columnIndex,
			int rowToBeginCheckingAt) {

		List<MsExcelCell> duplicatedCells = new ArrayList<MsExcelCell>();
		int rowIndex = 0;
		int currentRow = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < rowToBeginCheckingAt) {
				rowIndex++;
				currentRow++;
				rit.next();
				continue;
			}

			Row row = rit.next();

			if (row.getCell(columnIndex).getCellType() == Cell.CELL_TYPE_STRING) {
				if (!isUnique(columnIndex, currentRow, row.getCell(columnIndex)
						.getStringCellValue(), null, rowToBeginCheckingAt)) {
					boolean duplicateCellAlreadyRecorded = false;
					for (MsExcelCell cell : duplicatedCells) {
						if (cell.getContent().equalsIgnoreCase(
								row.getCell(columnIndex).getStringCellValue())) {
							cell.addDuplicate(getCellDetails(row.getRowNum(),
									columnIndex, row.getCell(columnIndex)
											.getStringCellValue()));
							duplicateCellAlreadyRecorded = true;
						}
					}
					if (!duplicateCellAlreadyRecorded)
						duplicatedCells.add(getCellDetails(row.getRowNum(),
								columnIndex, row.getCell(columnIndex)
										.getStringCellValue()));

				}
			} else if (row.getCell(columnIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {

				if (!isUnique(columnIndex, currentRow, null,
						row.getCell(columnIndex).getNumericCellValue(),
						rowToBeginCheckingAt)) {
					double content = row.getCell(columnIndex)
							.getNumericCellValue();
					boolean duplicateCellAlreadyRecorded = false;
					for (MsExcelCell cell : duplicatedCells) {
						if (cell.getContent().equalsIgnoreCase(
								String.valueOf(content))) {
							cell.addDuplicate(getCellDetails(row.getRowNum(),
									columnIndex, String.valueOf(content)));
							duplicateCellAlreadyRecorded = true;
						}
					}
					if (!duplicateCellAlreadyRecorded)
						duplicatedCells.add(getCellDetails(row.getRowNum(),
								columnIndex, String.valueOf(content)));
				}
			}

			currentRow++;
		}

		if (null == duplicatedCells)
			return null;
		else if (duplicatedCells.size() == 0)
			return null;
		else
			return duplicatedCells;
	}

	public boolean isUnique(int columnIndex, int rowNotToCheck,
			String cellStringValueBeingChecked,
			Double cellIntegerValueBeingChecked, int rowToBeginCheckingAt) {
		int rowIndex = 0;

		for (Iterator<Row> rit = getSheetIterator(); rit.hasNext();) {

			if (rowIndex < rowToBeginCheckingAt || rowIndex == rowNotToCheck) {
				rowIndex++;
				rit.next();
				continue;
			}

			Row row = rit.next();

			if (row.getCell(columnIndex).getCellType() == Cell.CELL_TYPE_STRING) {
				if (row.getCell(columnIndex).getStringCellValue()
						.equalsIgnoreCase(cellStringValueBeingChecked))
					return false;
			} else if (row.getCell(columnIndex).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				double value = row.getCell(columnIndex).getNumericCellValue();
				if (cellIntegerValueBeingChecked.equals(value))
					return false;
			}
			rowIndex++;
		}

		return true;
	}

}
