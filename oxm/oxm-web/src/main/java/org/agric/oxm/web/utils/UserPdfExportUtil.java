package org.agric.oxm.web.utils;

import java.util.Date;
import java.util.List;

import org.agric.oxm.model.User;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.enums.MaritalStatus;
import org.agric.oxm.model.search.UserSearchParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

//import com.itextpdf.text.List;

public class UserPdfExportUtil {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	Document document = new Document();

	private UserSearchParameters params;

	// =========================================================================

	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
			Font.BOLD);
	private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.NORMAL, BaseColor.RED);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
			Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			Font.BOLD);

	public UserPdfExportUtil() {

	}

	/**
	 * sets params, PicsInCourse and fontsAndStyles on,
	 * 
	 * @param params
	 */
	public UserPdfExportUtil(UserSearchParameters params) {
		this.setParams(params);
	}

	public void setParams(UserSearchParameters params) {
		this.params = params;
	}

	public UserSearchParameters getParams() {
		return params;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void populateDocument(List<User> users) throws DocumentException {

		document.open();
		addMetaData(document);
		addTitlePage(document);
		addUsersContent(document, users);
		document.close();
	}

	public String getFileName() {
		String fileName = "acholi_farmers_system_";

		if (null != params.getProducerOrg())
			fileName += params.getProducerOrg().getName().toLowerCase()
					.replace(" ", "_");
		return fileName;
	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("Acholi Farmer List");
		document.addSubject("Using iText");
		document.addKeywords("Achooli, Farmers, List");
		document.addAuthor("System Generated");
		document.addCreator("System Generated");
	}

	private static void addTitlePage(Document document)
			throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph("ACHOLI FARMERS SYSTEM, - FARMER LIST",
				catFont));

		addEmptyLine(preface, 1);
		preface.add(new Paragraph("System Generated: " + new Date(), smallBold));//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addEmptyLine(preface, 3);

		document.add(preface);
		// Start a new page
		document.newPage();
	}

	private void addUsersContent(Document document, List<User> users)
			throws DocumentException {

		Anchor anchor = new Anchor("First Chapter", catFont);
		anchor.setName("First Chapter");

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Section subCatPart = catPart.addSection("List Of Users");

		createTable(subCatPart, users);
		document.add(subCatPart);

	}

	private void createTable(Section subCatPart, List<User> users)
			throws BadElementException {

		PdfPTable table = new PdfPTable(10);
		addHeaderCell("No", table);
		addHeaderCell("Sub-County", table);
		addHeaderCell("Parish", table);
		addHeaderCell("Village", table);
		addHeaderCell("Group-Name", table);
		addHeaderCell("Name", table);
		addHeaderCell("Sex", table);
		addHeaderCell("Age", table);
		addHeaderCell("Marital Status", table);
		addHeaderCell("House Hold", table);

		Integer currentRow = 1;

		for (User user : users) {
			addCell(currentRow++, table);
			addCell(user.getProducerOrg() != null ? user.getProducerOrg()
					.getSubCounty().getName() : "", table);
			addCell(user.getProducerOrg() != null ? user.getProducerOrg()
					.getParish().getName() : "", table);
			addCell(user.getProducerOrg() != null ? user.getProducerOrg()
					.getVillage().getName() : "", table);
			addCell(user.getProducerOrg() != null ? user.getProducerOrg()
					.getName() : "", table);
			addCell(user.getName(), table);

			String genderletter = user.getGender() != null ? (user.getGender()
					.equals(Gender.FEMALE) ? "F" : "M") : "";
			addCell(genderletter, table);

			if (user.getAge() != null)
				addCell(user.getAge(), table);
			else
				addCell("-", table);

			String maritalStatusletter = user.getMaritalStatus() != null ? user
					.getMaritalStatus().getName() : "";
			addCell(maritalStatusletter, table);

			String hhletter = user.getHouseHoldCategory() != null ? user
					.getHouseHoldCategory().toString() : "";
			addCell(hhletter, table);

		}
		subCatPart.add(table);
		// return table;
	}

	private void addHeaders(PdfPTable table) {
		// ------------Row 0-------------

		addHeaderCell("No", table);
		addHeaderCell("District", table);
		addHeaderCell("County", table);
		addHeaderCell("Sub County", table);
		addHeaderCell("Parish", table);
		addHeaderCell("Group", table);
		addHeaderCell("Name", table);
		addHeaderCell("Sex", table);
		addHeaderCell("Age", table);
		addHeaderCell("date of Joining", table);
		addHeaderCell("Marital Status", table);
		addHeaderCell("HH Category", table);
		addHeaderCell("Phone 1", table);
		addHeaderCell("Phone 2", table);
	}

	private void addHeaderCell(String phrase, PdfPTable table) {
		PdfPCell c1 = new PdfPCell(new Phrase(phrase));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

	}

	private void addCell(Integer number, PdfPTable table) {
		PdfPCell c1 = new PdfPCell(new Phrase(number.toString()));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(c1);

	}

	private void addCell(String phrase, PdfPTable table) {
		PdfPCell c1 = new PdfPCell(new Phrase(phrase));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(c1);

	}

	private static void createList(Section subCatPart) {
		com.itextpdf.text.List list = new com.itextpdf.text.List(true, false,
				10);
		list.add(new ListItem("First point"));
		list.add(new ListItem("Second point"));
		list.add(new ListItem("Third point"));
		subCatPart.add(list);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
