package org.agric.oxm.web.utils;

import java.text.SimpleDateFormat;
import java.util.List;

import org.agric.oxm.model.User;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.search.UserSearchParameters;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserExcelExportUtil {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private UserSearchParameters params;

	// =========================================================================

	private XSSFWorkbook wb = new XSSFWorkbook();

	private Short standardRowHeight = 500;
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");

	// private Short markColumnWidth = 1600;// 1500
	// private Short gpAndUC_ColumnWidth = 1600;// 2000

	private Short borderThickness = XSSFCellStyle.BORDER_MEDIUM;

	XSSFFont fontRed = wb.createFont();
	XSSFFont fontBlue = wb.createFont();
	XSSFFont fontRedBold = wb.createFont();
	XSSFFont fontBold = wb.createFont();

	XSSFFont fontBoldOrangeBkGround = wb.createFont();

	XSSFCellStyle cStyleRetake = wb.createCellStyle();
	XSSFCellStyle leftBlack = wb.createCellStyle();
	XSSFCellStyle centerBlack = wb.createCellStyle();
	XSSFCellStyle centerBlackBold = wb.createCellStyle();
	XSSFCellStyle centerBlack2DPlaces = wb.createCellStyle();

	XSSFCellStyle leftBlack_BorderRight = wb.createCellStyle();

	XSSFCellStyle centerBlack_BorderBottom = wb.createCellStyle();
	XSSFCellStyle centerBlack_BorderTop = wb.createCellStyle();
	XSSFCellStyle centerBlack_BorderLeftRight = wb.createCellStyle();
	XSSFCellStyle centerBlack_BorderLeft = wb.createCellStyle();
	XSSFCellStyle centerBlack_BorderRight = wb.createCellStyle();

	XSSFCellStyle centerBlackBold_BorderTop = wb.createCellStyle();
	XSSFCellStyle centerBlackBold_BorderTopBottom = wb.createCellStyle();

	XSSFCellStyle centerBlack2DPlaces_BoldLeftRight = wb.createCellStyle();

	DataFormat format = wb.createDataFormat();

	XSSFCellStyle centerBlack1DPlace = wb.createCellStyle();
	XSSFCellStyle centerBlack0DPlaces = wb.createCellStyle();
	XSSFCellStyle centerBlackRetakeScore = wb.createCellStyle();
	XSSFCellStyle centerBlack2DPlacesRetakeScore = wb.createCellStyle();
	XSSFCellStyle centerBlack1DPlacesRetakeScore = wb.createCellStyle();
	XSSFCellStyle centerBlack0DPlacesRetakeScore = wb.createCellStyle();

	XSSFCellStyle centerBlack1DPlace_BorderRight = wb.createCellStyle();

	// first time retakes (ctr)
	XSSFCellStyle centerRed1DPlaceRetake = wb.createCellStyle();

	XSSFCellStyle centerRed0DPlaceRetake = wb.createCellStyle();
	// retake again
	XSSFCellStyle centerRed1DPlaceRetakeRetakeScore = wb.createCellStyle();

	XSSFCellStyle centerRed2DPlaceRetakeRetakeScore = wb.createCellStyle();
	XSSFCellStyle centerRed0DPlaceRetakeRetakeScore = wb.createCellStyle();

	// retaker blue (now passed)
	XSSFCellStyle cStyleUnAlignedBlue_Retaker = wb.createCellStyle();
	XSSFCellStyle centerBlue_Retaker = wb.createCellStyle();
	XSSFCellStyle centerBlue0DPlace_Retaker = wb.createCellStyle();
	XSSFCellStyle centerBlue1DPlace_Retaker = wb.createCellStyle();
	XSSFCellStyle centerBlue2DPlace_Retaker = wb.createCellStyle();

	// styles with bold borders
	XSSFCellStyle centerBlue2DPlace_Retaker_BoldLeftRight = wb
			.createCellStyle();
	XSSFCellStyle centerBlue1DPlace_BoldRight = wb.createCellStyle();

	/**
	 * ---------------set styles--------------------------------
	 */
	public void setFontsAndStyles() {
		fontRed.setColor(XSSFFont.COLOR_RED);

		fontBlue.setColor(XSSFFont.COLOR_NORMAL);

		fontRedBold.setColor(XSSFFont.COLOR_RED);
		fontRedBold.setBoldweight(Font.BOLDWEIGHT_BOLD);

		fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// fontBoldOrangeBkGround.setColor(XSSFColor.BLACK.index);
		// fontBoldOrangeBkGround.setColor(XSSFColor.BLACK.index);

		cStyleRetake.setFont(fontBold);

		// normal marks
		centerBlack.setAlignment(CellStyle.ALIGN_CENTER);
		leftBlack.setAlignment(CellStyle.ALIGN_LEFT);

		centerBlackBold.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlackBold.setFont(fontBold);

		centerBlack2DPlaces.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlack2DPlaces.setDataFormat(format.getFormat("#0.00"));

		centerBlack1DPlace.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlack1DPlace.setDataFormat(format.getFormat("#0.0"));

		centerBlack0DPlaces.setAlignment(CellStyle.ALIGN_CENTER);

		centerBlack0DPlaces.setDataFormat(format.getFormat("#0"));

		// RetakeScores that appear in Black

		centerBlackRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlackRetakeScore.setFont(fontBold);

		centerBlack2DPlacesRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlack2DPlacesRetakeScore.setDataFormat(format.getFormat("#0.00"));
		centerBlack2DPlacesRetakeScore.setFont(fontBold);

		centerBlack1DPlacesRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlack1DPlacesRetakeScore.setDataFormat(format.getFormat("#0.0"));
		centerBlack1DPlacesRetakeScore.setFont(fontBold);

		centerBlack0DPlacesRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlack0DPlacesRetakeScore.setDataFormat(format.getFormat("#0"));
		centerBlack0DPlacesRetakeScore.setFont(fontBold);

		centerRed1DPlaceRetake.setAlignment(CellStyle.ALIGN_CENTER);

		centerRed1DPlaceRetake.setDataFormat(format.getFormat("#0.0"));
		centerRed1DPlaceRetake.setFont(fontRed);

		centerRed0DPlaceRetake.setAlignment(CellStyle.ALIGN_CENTER);

		centerRed0DPlaceRetake.setDataFormat(format.getFormat("##0"));
		centerRed0DPlaceRetake.setFont(fontRed);

		centerRed1DPlaceRetakeRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerRed1DPlaceRetake.setDataFormat(format.getFormat("#0.0"));
		centerRed1DPlaceRetakeRetakeScore.setFont(fontRedBold);

		centerRed2DPlaceRetakeRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerRed2DPlaceRetakeRetakeScore.setDataFormat(format
				.getFormat("#0.00"));
		centerRed2DPlaceRetakeRetakeScore.setFont(fontRedBold);

		centerRed0DPlaceRetakeRetakeScore.setAlignment(CellStyle.ALIGN_CENTER);
		centerRed0DPlaceRetakeRetakeScore
				.setDataFormat(format.getFormat("##0"));
		centerRed0DPlaceRetakeRetakeScore.setFont(fontRedBold);

		cStyleUnAlignedBlue_Retaker.setFont(fontBlue);

		centerBlue_Retaker.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlue_Retaker.setFont(fontBlue);

		centerBlue0DPlace_Retaker.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlue0DPlace_Retaker.setDataFormat(format.getFormat("##0"));
		centerBlue0DPlace_Retaker.setFont(fontBlue);

		centerBlue1DPlace_Retaker.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlue1DPlace_Retaker.setDataFormat(format.getFormat("#0.0"));
		centerBlue1DPlace_Retaker.setFont(fontBlue);

		centerBlue2DPlace_Retaker.setAlignment(CellStyle.ALIGN_CENTER);
		centerBlue2DPlace_Retaker.setDataFormat(format.getFormat("#0.00"));
		centerBlue2DPlace_Retaker.setFont(fontBlue);

		// styles with borders

		leftBlack_BorderRight.cloneStyleFrom(leftBlack);
		leftBlack_BorderRight.setBorderRight(borderThickness);

		centerBlack_BorderBottom.cloneStyleFrom(centerBlack);
		centerBlack_BorderBottom.setBorderBottom(borderThickness);

		centerBlack_BorderTop.cloneStyleFrom(centerBlack);
		centerBlack_BorderTop.setBorderTop(borderThickness);

		centerBlack_BorderLeftRight.cloneStyleFrom(centerBlack);
		centerBlack_BorderLeftRight.setBorderLeft(borderThickness);
		centerBlack_BorderLeftRight.setBorderRight(borderThickness);

		centerBlack_BorderLeft.cloneStyleFrom(centerBlack);
		centerBlack_BorderLeft.setBorderLeft(borderThickness);

		centerBlack_BorderRight.cloneStyleFrom(centerBlack);
		centerBlack_BorderRight.setBorderRight(borderThickness);

		centerBlackBold_BorderTop.cloneStyleFrom(centerBlackBold);
		centerBlackBold_BorderTop.setBorderTop(borderThickness);

		centerBlackBold_BorderTopBottom.cloneStyleFrom(centerBlackBold);
		centerBlackBold_BorderTopBottom.setBorderTop(borderThickness);
		centerBlackBold_BorderTopBottom.setBorderBottom(borderThickness);

		centerBlack1DPlace_BorderRight.cloneStyleFrom(centerBlack1DPlace);
		centerBlack1DPlace_BorderRight.setBorderRight(borderThickness);

		// ==========

		centerBlue2DPlace_Retaker_BoldLeftRight
				.cloneStyleFrom(centerBlue2DPlace_Retaker);
		centerBlue2DPlace_Retaker_BoldLeftRight.setBorderLeft(borderThickness);
		centerBlue2DPlace_Retaker_BoldLeftRight.setBorderRight(borderThickness);

		centerBlack2DPlaces_BoldLeftRight.cloneStyleFrom(centerBlack2DPlaces);
		centerBlack2DPlaces_BoldLeftRight.setBorderLeft(borderThickness);
		centerBlack2DPlaces_BoldLeftRight.setBorderRight(borderThickness);

		centerBlue1DPlace_BoldRight.cloneStyleFrom(centerBlue1DPlace_Retaker);
		centerBlue1DPlace_BoldRight.setBorderRight(borderThickness);

		log.info(String.format("created %s styles in workbook!!!!",
				wb.getNumCellStyles()));

		// ---------------styles--------------------------------
	}

	public UserExcelExportUtil() {

	}

	/**
	 * sets params, PicsInCourse and fontsAndStyles on,
	 * 
	 * @param params
	 */
	public UserExcelExportUtil(UserSearchParameters params) {
		this.setParams(params);
		this.setFontsAndStyles();
	}

	public void setParams(UserSearchParameters params) {
		this.params = params;
	}

	public UserSearchParameters getParams() {
		return params;
	}

	public XSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(XSSFWorkbook wb) {
		this.wb = wb;
	}

	public String getFileName() {
		String fileName = "acholi_farmers_system_";

		if (null != params.getProducerOrg())
			fileName += params.getProducerOrg().getName().toLowerCase()
					.replace(" ", "_");
		return fileName;
	}

	public String getSheetName() {
		String sheetName = "";
		sheetName = "Producers_and_Users";

		return sheetName;
	}

	public Integer getLastColumnIndex() {
		Integer lastColumn = 14;

		return lastColumn;
	}

	/**
	 * marks start from column 0
	 * 
	 * @return
	 */
	public Integer getStartColIndex() {
		return 0;
	}

	public void addUsersWorkSheetAndHeaders() {
		XSSFSheet s = getWb().createSheet(getSheetName());

		s.createRow(0);
		s.getRow(0).setHeight(standardRowHeight);

		addTitle();
		addHeaders();

		s.getRow(0).setHeight(standardRowHeight);
		s.getRow(1).setHeight(standardRowHeight);
		s.getRow(2).setHeight(standardRowHeight);

	}

	/**
	 * add row 1
	 */
	private void addTitle() {

		XSSFSheet s = getWb().getSheet(getSheetName());

		XSSFRow row1 = s.createRow(1);

		XSSFCell cellHeader = row1.createCell(2);

		cellHeader
				.setCellValue("Producers/Users exported from Acholi Faremers System");
		cellHeader.setCellStyle(centerBlackBold);
		s.addMergedRegion(new CellRangeAddress(1, 1, 2, 8));

		// bottom border all thru
		for (int i = 0; i <= getLastColumnIndex(); i++) {
			addMyStyle(s, 0, i, centerBlack_BorderBottom);
		}
	}

	/**
	 * add row 2 (detailed format)
	 */
	private void addHeaders() {
		// ------------Row 2-------------
		XSSFSheet s = getWb().getSheet(getSheetName());
		XSSFRow row2 = s.createRow(2);

		myCreateCell(row2, 0, "No", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 1, "District", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 2, "County", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 3, "Sub County", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 4, "parish", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 5, "Village", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 6, "Name of Group", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 7, "Name of Beneficiary",
				centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 8, "Sex", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 9, "Age", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 10, "date of Joining",
				centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 11, "marital Status",
				centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 12, "Category Of House hold",
				centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 13, "Phone 1", centerBlackBold_BorderTopBottom);
		myCreateCell(row2, 14, "Phone 2", centerBlackBold_BorderTopBottom);
	}

	public void populateWorkBook(List<User> users) {

		int currentRow = 3;
		XSSFSheet s = getWb().getSheet(getSheetName());

		for (User user : users) {

			XSSFRow row = s.createRow(currentRow);
			s.getRow(currentRow).setHeight(standardRowHeight);

			myCreateCell(row, 0, currentRow - 2, null);
			if (user.getProducerOrg() != null) {

				String str = user.getProducerOrg().getDistrict() != null ? user
						.getProducerOrg().getDistrict().getName() : "";
				myCreateCell(row, 1, str, null);

				str = user.getProducerOrg().getCounty() != null ? user
						.getProducerOrg().getCounty().getName() : "";
				myCreateCell(row, 2, str, null);

				str = user.getProducerOrg().getSubCounty() != null ? user
						.getProducerOrg().getSubCounty().getName() : "";
				myCreateCell(row, 3, str, null);

				str = user.getProducerOrg().getParish() != null ? user
						.getProducerOrg().getParish().getName() : "";
				myCreateCell(row, 4, str, null);
				str = user.getProducerOrg().getVillage() != null ? user
						.getProducerOrg().getVillage().getName() : "";
				myCreateCell(row, 5, str, null);

				str = StringUtils.isNotBlank(user.getProducerOrg().getName()) ? user
						.getProducerOrg().getSubCounty().getName()
						: "";
				myCreateCell(row, 6, str, null);
			}

			myCreateCell(row, 7, user.getName(), null);
			myCreateCell(row, 8, user.getGender() == null ? "" : user
					.getGender().equals(Gender.FEMALE) ? "F" : "M", null);
			myCreateCell(row, 9, user.getAge(), null);
			myCreateCell(
					row,
					10,
					user.getDateOfJoining() != null ? dateFormat.format(user
							.getDateOfJoining()) : "", null);
			myCreateCell(row, 11, user.getMaritalStatus() != null ? user
					.getMaritalStatus().getName() : "", null);
			myCreateCell(row, 12, user.getHouseHoldCategory() != null ? user
					.getHouseHoldCategory().toString() : "", null);
			myCreateCell(row, 13,
					StringUtils.isNotBlank(user.getPhone1()) ? user.getPhone1()
							: "", null);
			myCreateCell(row, 14,
					StringUtils.isNotBlank(user.getPhone2()) ? user.getPhone2()
							: "", null);

			currentRow++;
		}
	}

	private void addMyStyle(XSSFSheet s, Integer rowIndex, Integer cellIndex,
			XSSFCellStyle style) {
		if (s.getRow(rowIndex) == null)
			s.createRow(rowIndex);

		if (s.getRow(rowIndex).getCell(cellIndex) == null)
			s.getRow(rowIndex).createCell(cellIndex);

		if (null != style)
			s.getRow(rowIndex).getCell(cellIndex).setCellStyle(style);
	}

	// ========================================================================================

	@SuppressWarnings("unused")
	private void myCreateCell(XSSFSheet s, Integer rowIndex, Integer cellIndex,
			String content, XSSFCellStyle style) {
		if (s.getRow(rowIndex) == null) {
			s.createRow(rowIndex);
			s.getRow(rowIndex).setHeight(standardRowHeight);
		}

		XSSFCell cell = s.getRow(rowIndex).createCell(cellIndex);

		if (null != content)
			cell.setCellValue(content);
		else
			cell.setCellValue("");

		if (style != null)
			cell.setCellStyle(style);
	}

	@SuppressWarnings("unused")
	private void myCreateCell(XSSFSheet s, Integer rowIndex, Integer cellIndex,
			Integer content, XSSFCellStyle style) {
		if (s.getRow(rowIndex) == null) {
			s.createRow(rowIndex);
			s.getRow(rowIndex).setHeight(standardRowHeight);
		}

		XSSFCell cell = s.getRow(rowIndex).createCell(cellIndex);

		if (null != content)
			cell.setCellValue(content);
		else
			cell.setCellValue("");
		if (style != null)
			cell.setCellStyle(style);
	}

	@SuppressWarnings("unused")
	private void myCreateCell(XSSFSheet s, Integer rowIndex, Integer cellIndex,
			Double content, XSSFCellStyle style) {
		if (s.getRow(rowIndex) == null) {
			s.createRow(rowIndex);
			s.getRow(rowIndex).setHeight(standardRowHeight);
		}

		XSSFCell cell = s.getRow(rowIndex).createCell(cellIndex);

		if (null != content)
			cell.setCellValue(content);
		else
			cell.setCellValue("");
		if (style != null)
			cell.setCellStyle(style);
	}

	// ========================================================================================

	private void myCreateCell(XSSFRow row, Integer cellIndex, String content,
			XSSFCellStyle style) {
		XSSFCell cell = row.createCell(cellIndex);

		if (null != content)
			cell.setCellValue(content);
		else
			cell.setCellValue("");

		if (style != null)
			cell.setCellStyle(style);
	}

	private void myCreateCell(XSSFRow row, Integer cellIndex, Integer content,
			XSSFCellStyle style) {
		XSSFCell cell = row.createCell(cellIndex);

		if (null != content)
			cell.setCellValue(content);
		else
			cell.setCellValue("");

		if (style != null)
			cell.setCellStyle(style);
	}

	@SuppressWarnings("unused")
	private void myCreateCell(XSSFRow row, Integer cellIndex, Double content,
			XSSFCellStyle style) {
		XSSFCell cell = row.createCell(cellIndex);

		if (null != content)
			cell.setCellValue(content);
		else
			cell.setCellValue("");

		if (style != null)
			cell.setCellStyle(style);
	}

	// =======================================================================================

}
