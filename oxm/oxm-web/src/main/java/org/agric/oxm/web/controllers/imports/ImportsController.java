package org.agric.oxm.web.controllers.imports;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.agric.oxm.excelimport.MsExcelProcessesor;
import org.agric.oxm.excelimport.MsExcellTemplate;
import org.agric.oxm.excelimport.model.ColumnStrings;
import org.agric.oxm.excelimport.model.MsExcelCell;
import org.agric.oxm.model.County;
import org.agric.oxm.model.District;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.ProducerOrg;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.User;
import org.agric.oxm.model.Village;
import org.agric.oxm.model.enums.HouseHoldCategory;
import org.agric.oxm.model.enums.Gender;
import org.agric.oxm.model.enums.ImportItem;
import org.agric.oxm.model.enums.MaritalStatus;
import org.agric.oxm.model.enums.UserStatus;
import org.agric.oxm.model.exception.OperationFailedException;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.util.DistrictProducerImportData;
import org.agric.oxm.model.util.MyDateUtil;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.security.util.OXMSecurityUtil;
import org.agric.oxm.server.service.Adminservice;
import org.agric.oxm.server.service.ProducerOrgService;
import org.agric.oxm.server.service.RoleService;
import org.agric.oxm.server.service.UserService;
import org.agric.oxm.utils.StringUtil;
import org.agric.oxm.web.WebConstants;
import org.agric.oxm.web.controllers.porg.POrgController;
import org.agric.oxm.web.controllers.settings.DistrictController;
import org.agric.oxm.web.controllers.settings.UserController;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("import")
public class ImportsController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DistrictController districtController;

	@Autowired
	private Adminservice adminService;

	@Autowired
	private ProducerOrgService producerOrgService;

	@Autowired
	private POrgController pOrgController;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserController userController;

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.ADD_DISTRICT_DETAILS })
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getImportForm(ModelMap modelMap) {
		return getImportDistrictsForm(modelMap);
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.ADD_DISTRICT_DETAILS })
	@RequestMapping(value = "/districts", method = RequestMethod.GET)
	public ModelAndView getImportDistrictsForm(ModelMap modelMap) {
		return prepareImportModel(ImportItem.DISTRICT_DETAILS, modelMap);
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.ADD_DISTRICT_DETAILS })
	@RequestMapping(value = "/producers", method = RequestMethod.GET)
	public ModelAndView getImportProducersForm(ModelMap modelMap) {
		return prepareImportModel(ImportItem.PRODUCERS, modelMap);
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.ADD_DISTRICT_DETAILS })
	@RequestMapping(value = "/producerorgs", method = RequestMethod.GET)
	public ModelAndView getImportProducerOrganisationsForm(ModelMap modelMap) {
		return prepareImportModel(ImportItem.PRODUCER_ORGz, modelMap);
	}

	private ModelAndView prepareImportModel(ImportItem importItem,
			ModelMap modelMap) {
		DistrictProducerImportData districtProducerImportData = new DistrictProducerImportData(
				importItem);
		modelMap.put("districtProducerImportData", districtProducerImportData);
		modelMap.put(WebConstants.CONTENT_HEADER,
				"Import " + importItem.getName());

		modelMap.put("items", ImportItem.values());
		return new ModelAndView("generalImportForm", modelMap);
	}

	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.ADD_DISTRICT_DETAILS,
			PermissionConstants.ADD_PROD_ORG, PermissionConstants.ADD_PRODUCER })
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView getImportAllForm(ModelMap modelMap) {
		return prepareImportModel(ImportItem.ALL, modelMap);
	}

	/**
	 * 
	 * @param districtProducerImportData
	 * @param file
	 * @param modelMap
	 * @return
	 */
	@Secured({ PermissionConstants.ADD_DISTRICT_DETAILS,
			PermissionConstants.ADD_PROD_ORG, PermissionConstants.ADD_PRODUCER })
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ModelAndView postImportHandler(
			@ModelAttribute("districtProducerImportData") DistrictProducerImportData districtProducerImportData,
			@RequestParam(value = "file", required = true) MultipartFile file,
			ModelMap modelMap) {

		if (file.isEmpty()) {
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					"Empty file error");
			return prepareImportModel(
					districtProducerImportData.getImportItem(), modelMap);
		}

		try {

			if (!file.getContentType().toString()
					.equals("application/vnd.ms-excel")
					&& !file.getContentType().toString()
							.equals("application/octet-stream")
					&& !file.getContentType()
							.toString()
							.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
						"Invalid type format. Only MS Excel expected.");
				log.error("File type error - "
						+ file.getContentType().toString());
				return prepareImportModel(
						districtProducerImportData.getImportItem(), modelMap);
			}

			InputStream inputStream = file.getInputStream();

			String feedback = "";

			// ms-excel 2007
			if (file.getContentType()
					.toString()
					.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {

				XSSFWorkbook wb2007 = new XSSFWorkbook(
						OPCPackage.open(inputStream));

				validateTemplate(null, wb2007);
				if (districtProducerImportData.getImportItem().equals(
						ImportItem.DISTRICT_DETAILS))
					feedback = saveDistrictDetails(null, wb2007);
				else if (districtProducerImportData.getImportItem().equals(
						ImportItem.PRODUCER_ORGz))
					feedback = saveProducerOrgs(null, wb2007);
				else if (districtProducerImportData.getImportItem().equals(
						ImportItem.PRODUCERS))
					feedback = saveProducers(null, wb2007);

				if (districtProducerImportData.getImportItem().equals(
						ImportItem.ALL)) {
					String districts = saveDistrictDetails(null, wb2007);
					String porgs = saveProducerOrgs(null, wb2007);
					String producers = saveProducers(null, wb2007);

					feedback = districts + "<br>" + porgs + "<br>" + producers;
				}
			}

			// ms-excel 2003
			if (file.getContentType().toString()
					.equals("application/vnd.ms-excel")
					|| file.getContentType().toString()
							.equals("application/octet-stream")) {

				POIFSFileSystem fileSystem = new POIFSFileSystem(
						file.getInputStream());

				HSSFWorkbook wb2003 = new HSSFWorkbook(fileSystem);

				validateTemplate(wb2003, null);

				if (districtProducerImportData.getImportItem().equals(
						ImportItem.DISTRICT_DETAILS))
					feedback = saveDistrictDetails(wb2003, null);
				else if (districtProducerImportData.getImportItem().equals(
						ImportItem.PRODUCER_ORGz))
					feedback = saveProducerOrgs(wb2003, null);
				else if (districtProducerImportData.getImportItem().equals(
						ImportItem.PRODUCERS))
					feedback = saveProducers(wb2003, null);

				if (districtProducerImportData.getImportItem().equals(
						ImportItem.ALL)) {
					String districts = saveDistrictDetails(wb2003, null);
					String porgs = saveProducerOrgs(wb2003, null);
					String producers = saveProducers(wb2003, null);

					feedback = districts + "/n" + porgs + "/n" + producers;
				}
			}

			modelMap.put(WebConstants.MODEL_ATTRIBUTE_SYSTEM_MESSAGE, feedback);

			if (districtProducerImportData.getImportItem().equals(
					ImportItem.DISTRICT_DETAILS))
				return districtController.viewDistrictHandler(modelMap);
			if (districtProducerImportData.getImportItem().equals(
					ImportItem.PRODUCER_ORGz))
				return pOrgController.view(modelMap);
			if (districtProducerImportData.getImportItem().equals(
					ImportItem.PRODUCERS))
				return userController.view(modelMap);

			if (districtProducerImportData.getImportItem().equals(
					ImportItem.ALL)) {
				return getImportAllForm(modelMap);
			}

		} catch (Exception e) {
			log.error("Failed!", e);
			modelMap.put(WebConstants.MODEL_ATTRIBUTE_ERROR_MESSAGE,
					e.getMessage());
		}
		return prepareImportModel(districtProducerImportData.getImportItem(),
				modelMap);

	}

	/**
	 * validates template <br>
	 * checks blank cells <br>
	 * checks Null cells <br>
	 * checks blank cells <br>
	 * checks M/F column
	 * 
	 * @param wb2003
	 * @param wb2007
	 * @throws ValidationException
	 * @throws OperationFailedException
	 */
	public static void validateTemplate(HSSFWorkbook wb2003, XSSFWorkbook wb2007)
			throws ValidationException, OperationFailedException {

		String TemplateName = "Oxfam Producer Template";
		String columnNamesStr = "Entered, Entered By, Bene. HH No., Staff Responsible, Date of Registration,"
				+ " Date of Entry, District, County, Sub County, Parish, Village, Name of Group,"
				+ " Name of Beneficiary, Sex, Age, Date of Joining, Mariatl Status, Category of HH";

		List<Integer> allNoneNullCells = new ArrayList<Integer>(Arrays.asList(
				6, 8, 9, 10, 11, 12, 13));
		List<Integer> allNoneBlankCells = new ArrayList<Integer>(Arrays.asList(
				6, 8, 9, 10, 11, 12, 13));
		Integer indexOfFirstDataRow = 1;

		List<ColumnStrings> validStrings = new ArrayList<ColumnStrings>();
		ColumnStrings sexColumnStrings = new ColumnStrings(13, "M,F", ",");
		validStrings.add(sexColumnStrings);

		MsExcellTemplate template = new MsExcellTemplate(TemplateName,
				columnNamesStr, ",", allNoneNullCells, allNoneBlankCells, null,
				null, validStrings, indexOfFirstDataRow);

		MsExcelProcessesor processesor = null;

		if (wb2007 != null) {
			processesor = new MsExcelProcessesor(wb2007, wb2007.getSheetAt(0),
					template);
		} else {
			processesor = new MsExcelProcessesor(wb2003, wb2003.getSheetAt(0),
					template);
		}

		/**
		 * none null cells test
		 */
		MsExcelCell nullCell = processesor.checkTemplateForNullCells();
		if (null != nullCell) {
			String errorMsg = template.getNullCellErrorMessage(nullCell);
			throw new ValidationException(errorMsg);
		}

		/**
		 * none blank cells test
		 */
		MsExcelCell blankCell = processesor.checkTemplateForBlankCells();
		if (null != blankCell) {
			String errorMsg = template.getBlankCellErrorMessage(blankCell);
			throw new ValidationException(errorMsg);
		}
	}

	// =============================================================================================================

	/**
	 * 
	 * @param wb2003
	 * @param wb2007
	 * @return
	 * @throws SessionExpiredException
	 * @throws ValidationException
	 */
	private String saveProducerOrgs(HSSFWorkbook wb2003, XSSFWorkbook wb2007)
			throws SessionExpiredException, ValidationException {

		List<ProducerOrg> porgs = producerOrgService.getProducerOrganisations();
		List<District> districts = adminService.getDistricts();
		// counters
		int newPorgs = 0;
		int updatedPorgs = 0;

		Iterator<Row> rit = null;
		if (wb2007 != null)
			rit = wb2007.getSheetAt(0).rowIterator();
		else if (wb2003 != null)
			rit = wb2003.getSheetAt(0).rowIterator();

		int rowIndex = 0;
		for (; rit.hasNext();) {

			if (rowIndex < DistrictProducerImportData.rowToBeginCheckingAt) {
				rowIndex++;
				rit.next();
				continue;
			}
			Row row = rit.next();

			// checks and throws exception if a required item is missing
			checkWhetherItemsExist(false, row, porgs, districts);

			ProducerOrg porg = generateNewPorg(row, porgs, districts);
			if (porg != null) {
				producerOrgService.save(porg);
				porgs = producerOrgService.getProducerOrganisations();
				newPorgs++;
			}
		}

		return getProducerOrgFeedBackString(updatedPorgs, newPorgs);
	}

	private String getProducerOrgFeedBackString(int updatedPorgs, int newPorgs) {
		String str2return = "";
		if (updatedPorgs > 0)
			str2return = str2return
					+ String.format("\n%d Producer org(s) Edited,   ",
							updatedPorgs);

		str2return = str2return
				+ String.format("\n%d Producer org(s) Added  ", newPorgs);
		return str2return;

	}

	// =============================================================================================================

	/**
	 * 
	 * @param wb2003
	 * @param wb2007
	 * @return
	 * @throws SessionExpiredException
	 * @throws ValidationException
	 */
	private String saveDistrictDetails(HSSFWorkbook wb2003, XSSFWorkbook wb2007)
			throws SessionExpiredException, ValidationException {

		List<District> districts = adminService.getDistricts();
		// counters
		int newDistricts = 0;
		int newCounties = 0;
		int newSubCounties = 0;
		int newParishes = 0;
		int newVillages = 0;

		Iterator<Row> rit = null;
		if (wb2007 != null)
			rit = wb2007.getSheetAt(0).rowIterator();
		else if (wb2003 != null)
			rit = wb2003.getSheetAt(0).rowIterator();

		int rowIndex = 0;
		for (; rit.hasNext();) {

			if (rowIndex < DistrictProducerImportData.rowToBeginCheckingAt) {
				rowIndex++;
				rit.next();
				continue;
			}
			Row row = rit.next();

			String districtName = row.getCell(
					DistrictProducerImportData.districtColumnIndex)
					.getStringCellValue();
			districtName = cleanDistrictUnitName(districtName.toUpperCase());

			District district = getDistrictWithName(districtName, districts);
			if (district == null) {
				district = new District(districtName);

				adminService.save(district);
				districts = adminService.getDistricts();
				newDistricts++;
				// continue;

			}

			String countyName = row.getCell(
					DistrictProducerImportData.countyColumnIndex)
					.getStringCellValue();
			countyName = cleanDistrictUnitName(countyName.toUpperCase());

			County county = getCountyWithName(countyName, district);
			if (null == county) {
				county = new County(countyName, district);
				district.addCounty(county);
				adminService.save(district);
				districts = adminService.getDistricts();
				newCounties++;
				// continue;

			}

			String subCountyName = row.getCell(
					DistrictProducerImportData.subcountyColumnIndex)
					.getStringCellValue();
			subCountyName = cleanDistrictUnitName(subCountyName.toUpperCase());

			SubCounty subCounty = getSubCountyWithName(subCountyName, county);
			if (null == subCounty) {
				subCounty = new SubCounty(subCountyName, county);
				county.addSubCounty(subCounty);
				adminService.save(district);
				districts = adminService.getDistricts();
				newSubCounties++;
				// continue;

			}

			String parishName = row.getCell(
					DistrictProducerImportData.parishColumnIndex)
					.getStringCellValue();
			parishName = cleanDistrictUnitName(parishName.toUpperCase());

			Parish parish = getParishWithName(parishName, subCounty);
			if (null == parish) {
				parish = new Parish(parishName, subCounty);
				subCounty.addParish(parish);
				adminService.save(subCounty);
				districts = adminService.getDistricts();
				newParishes++;
				// continue;

			}

			String villageName = row.getCell(
					DistrictProducerImportData.villageColumnIndex)
					.getStringCellValue();
			villageName = cleanDistrictUnitName(villageName.toUpperCase());

			Village village = getVillageWithName(villageName, parish);
			if (null == village) {
				village = new Village(villageName, parish);
				parish.addVillage(village);
				adminService.save(parish);
				districts = adminService.getDistricts();
				newVillages++;
				// continue;
			}
		}

		String str2return = "";
		if (newDistricts > 0)
			str2return = str2return
					+ String.format("\nNew District(s)= %d,   ", newDistricts);
		if (newCounties > 0)
			str2return = str2return
					+ String.format("\nNew County(ies)=%d,   ", newCounties);
		if (newSubCounties > 0)
			str2return = str2return
					+ String.format("\nNew SubCounty(ies)=%d,   ",
							newSubCounties);
		if (newParishes > 0)
			str2return = str2return
					+ String.format("\nNew Parish(es)=%d,   ", newParishes);
		if (newVillages > 0)
			str2return = str2return
					+ String.format("\nNew Village(s)=%d,   ", newVillages);

		str2return = StringUtils.isNotBlank(str2return) ? str2return
				+ "\nSuccessfully saved to the database\n" : "";
		return str2return;
	}

	// =============================================================================================================

	/**
	 * 
	 * @param wb2003
	 * @param wb2007
	 * @return
	 * @throws Exception
	 */
	private String saveProducers(HSSFWorkbook wb2003, XSSFWorkbook wb2007)
			throws Exception {

		try {
			List<ProducerOrg> porgs = producerOrgService
					.getProducerOrganisations();
			List<District> districts = adminService.getDistricts();

			Role roleProducer = roleService
					.getRoleById("4836AFAB-3D62-482c-BA9A-D9D15839C68A");

			Connection con = getPostgreSQLDBConnection();

			// counters
			int newProducers = 0;
			int updatedProducers = 0;

			Iterator<Row> rit = null;
			if (wb2007 != null)
				rit = wb2007.getSheetAt(0).rowIterator();
			else if (wb2003 != null)
				rit = wb2003.getSheetAt(0).rowIterator();

			int rowIndex = 0;
			for (; rit.hasNext();) {

				if (rowIndex < DistrictProducerImportData.rowToBeginCheckingAt) {
					rowIndex++;
					rit.next();
					continue;
				}
				Row row = rit.next();

				String farmerPorgName = row.getCell(
						DistrictProducerImportData.porgNameColumnIndex)
						.getStringCellValue();
				ProducerOrg farmerPorg = getProducerOrgWithName(farmerPorgName,
						porgs);

				// ================

				String producerName = row
						.getCell(
								DistrictProducerImportData.farmerNameColumnIndex)
						.getStringCellValue().trim();
				String producerGenderStr = row.getCell(
						DistrictProducerImportData.genderColumnIndex)
						.getStringCellValue();
				Gender producerGender = producerGenderStr.equalsIgnoreCase("M") ? Gender.MALE
						: producerGenderStr.equalsIgnoreCase("F") ? Gender.FEMALE
								: null;
				Integer age = (row.getCell(
						DistrictProducerImportData.ageColumnIndex)
						.getCellType() == Cell.CELL_TYPE_NUMERIC) ? (int) row
						.getCell(DistrictProducerImportData.ageColumnIndex)
						.getNumericCellValue() : null;

				String producerDateOfJoiningStr = "";
				Integer yearOfJoining = null;
				Date producerDateOfJoining = null;
				if (row.getCell(
						DistrictProducerImportData.dateOfJoiningColumnIndex)
						.getCellType() == Cell.CELL_TYPE_NUMERIC)
					yearOfJoining = (int) row
							.getCell(
									DistrictProducerImportData.dateOfJoiningColumnIndex)
							.getNumericCellValue();
				else if (row.getCell(
						DistrictProducerImportData.dateOfJoiningColumnIndex)
						.getCellType() == Cell.CELL_TYPE_STRING)
					producerDateOfJoiningStr = row
							.getCell(
									DistrictProducerImportData.dateOfJoiningColumnIndex)
							.getStringCellValue();

				// use dateStringLength greater than 5 at list 1/1/12
				if (StringUtils.isNotBlank(producerDateOfJoiningStr)
						&& producerDateOfJoiningStr.trim() != "-"
						&& producerDateOfJoiningStr.length() > 5) {
					try {
						producerDateOfJoining = DateUtils.parseDate(
								producerDateOfJoiningStr,
								MyDateUtil.DEFAULT_INPUT_FORMATS);
					} catch (Exception e) {
						log.error("Error Parsing date", e);
					}
				}

				String maritalStatusStr = row.getCell(
						DistrictProducerImportData.maritalStatusColumnIndex)
						.getStringCellValue();
				MaritalStatus maritalStatus = null;

				if (StringUtils.isNotBlank(maritalStatusStr)) {
					switch (StringUtil.capitalizeFirstLetters(maritalStatusStr)
							.trim()) {
					case "Married":
						maritalStatus = MaritalStatus.MARRIED;
						break;
					case "Single":
						maritalStatus = MaritalStatus.SINGLE;
						break;
					case "Widow":
						maritalStatus = MaritalStatus.WIDOW;
						break;
					case "Widower":
						maritalStatus = MaritalStatus.WIDOWER;
						break;
					case "Separated":
						maritalStatus = MaritalStatus.SEPARATED;
						break;
					default:
						maritalStatus = null;

					}
				}

				String categoryStr = row.getCell(
						DistrictProducerImportData.categoryOfHHColumnIndex)
						.getStringCellValue();

				HouseHoldCategory categoryOfHouseHold = null;

				if (StringUtils.isNotBlank(categoryStr))
					switch (StringUtil.capitalizeFirstLetters(categoryStr)
							.trim()) {
					case "Mhh":
						categoryOfHouseHold = HouseHoldCategory.MHH;
						break;
					case "Fhh":
						categoryOfHouseHold = HouseHoldCategory.FHH;
						break;
					default:
						categoryOfHouseHold = null;
					}

				// checks and throws exception if a required item is missing
				checkWhetherItemsExist(true, row, porgs, districts);

				User producer = getProducerWithName(producerName, farmerPorg);
				if (producer != null)
					log.info("Producer name " + producerName
							+ " exists multiple times in " + farmerPorgName);

				// save new producer
				producer = new User(farmerPorg, producerName, producerGender,
						age, maritalStatus, categoryOfHouseHold);

				if (yearOfJoining != null)
					producer.setYearOfJoining(yearOfJoining);
				if (producerDateOfJoining != null)
					producer.setDateOfJoining(producerDateOfJoining);

				producer.addRole(roleProducer);
				producer.setStatus(UserStatus.ENABLED);

				producer.setClearTextPassword("pass");
				OXMSecurityUtil.prepUserCredentials(producer);

				producer.setProfilePicture(null);

				ResultSet rs = null;
				boolean generateDefaultUserName = true;
				do {
					producer.setUsername(OXMSecurityUtil.generateUserName(
							producer, generateDefaultUserName));
					generateDefaultUserName = false;
					String SQL = "Select * from users where username=\'"
							+ producer.getUsername() + "\'";
					Statement stmt = con.createStatement();
					rs = stmt.executeQuery(SQL);

				} while (rs.next());

				userService.validate(producer);
				userService.saveUser(producer);
				porgs = producerOrgService.getProducerOrganisations();
				newProducers++;
			}

			return getProducersFeedBackString(updatedProducers, newProducers);

		} catch (Exception e) {
			log.error("Error", e);
			throw e;
		}
	}

	private String getProducersFeedBackString(int updatedProducers,
			int newProducers) {
		String str2return = "";
		if (updatedProducers > 0)
			str2return = str2return
					+ String.format("\n%d Progucer(s) Edited,   ",
							updatedProducers);

		str2return = str2return
				+ String.format("\n%d Producer(s) Added,   ", newProducers);
		return str2return;

	}

	// =============================================================================================================
	public static ProducerOrg getProducerOrgWithName(String name,
			List<ProducerOrg> porgs) {
		if (porgs == null)
			return null;
		for (ProducerOrg porg : porgs) {
			if (porg.getName().toLowerCase().trim()
					.equalsIgnoreCase(name.toLowerCase().trim()))
				return porg;
		}
		return null;
	}

	public static District getDistrictWithName(String name,
			List<District> districts) {
		if (districts == null)
			return null;
		for (District district : districts) {
			if (district.getName().equalsIgnoreCase(name))
				return district;
		}
		return null;
	}

	public static County getCountyWithName(String name, District district) {
		if (district.getCounties() == null)
			return null;
		for (County county : district.getCounties()) {
			if (county.getName().equalsIgnoreCase(name))
				return county;
		}
		return null;
	}

	public static SubCounty getSubCountyWithName(String name, County county) {
		if (county.getSubCounties() == null)
			return null;
		for (SubCounty subCounty : county.getSubCounties()) {
			if (subCounty.getName().equalsIgnoreCase(name))
				return subCounty;
		}
		return null;
	}

	public static User getProducerWithName(String producerName, ProducerOrg porg) {
		if (porg.getProducers() == null)
			return null;
		for (User user : porg.getProducers()) {
			if (user.getName().equalsIgnoreCase(producerName))
				return user;
		}
		return null;
	}

	public static Parish getParishWithName(String name, SubCounty subcounty) {
		if (subcounty.getParishes() == null)
			return null;

		for (Parish parish : subcounty.getParishes()) {
			if (parish.getName().equalsIgnoreCase(name))
				return parish;
		}
		return null;
	}

	public static Village getVillageWithName(String name, Parish parish) {
		if (parish.getVillages() == null)
			return null;
		for (Village village : parish.getVillages()) {
			if (village.getName().equalsIgnoreCase(name))
				return village;
		}
		return null;
	}

	/**
	 * removes unwanted parts of names of Counties, sub-counties, e.t.c
	 * 
	 * @param districtUnitName
	 * @return
	 */
	private String cleanDistrictUnitName(String districtUnitName) {

		/**
		 * note: checking for COUNTY b4 "SUB COUNTY" and "SUBCOUNTY" leaves u
		 * with a "... SUB" Check COUNTY LAST
		 */
		String str = "DISTRICT,SUB COUNTY,SUBCOUNTY,COUNTY,PARISH,VILLAGE,CELL,(,";
		List<String> unwantedStrings = StringUtil.convertStringToList(str, ",");
		/**
		 * remove unwanted parts of the string
		 */
		for (String unwantedSubString : unwantedStrings) {
			if (districtUnitName.contains(unwantedSubString))
				districtUnitName = districtUnitName.substring(0,
						districtUnitName.indexOf(unwantedSubString));
		}
		districtUnitName = StringUtil.capitalizeFirstLetters(districtUnitName);
		return districtUnitName.trim();
	}

	// =============================================================================================================

	private String checkWhetherItemsExist(boolean producer, Row row,
			List<ProducerOrg> porgs, List<District> districts)
			throws ValidationException {

		String farmerPorgName = row.getCell(
				DistrictProducerImportData.porgNameColumnIndex)
				.getStringCellValue();

		String districtName = cleanDistrictUnitName(row
				.getCell(DistrictProducerImportData.districtColumnIndex)
				.getStringCellValue().toUpperCase());
		String countyName = row.getCell(
				DistrictProducerImportData.countyColumnIndex)
				.getStringCellValue();
		String subCountyName = row.getCell(
				DistrictProducerImportData.subcountyColumnIndex)
				.getStringCellValue();
		String parishName = row.getCell(
				DistrictProducerImportData.parishColumnIndex)
				.getStringCellValue();
		String villageName = row.getCell(
				DistrictProducerImportData.villageColumnIndex)
				.getStringCellValue();

		districtName = cleanDistrictUnitName(districtName.toUpperCase());
		subCountyName = cleanDistrictUnitName(subCountyName.toUpperCase());
		parishName = cleanDistrictUnitName(parishName.toUpperCase());
		villageName = cleanDistrictUnitName(villageName.toUpperCase());

		String missingItem = "";
		District district = null;
		SubCounty subCounty = null;
		County county = null;
		Parish parish = null;
		Village village = null;

		ProducerOrg porgName = getProducerOrgWithName(farmerPorgName, porgs);
		if (producer && porgName == null) {
			missingItem = farmerPorgName + " Producer Group ";
		} else {
			district = getDistrictWithName(districtName, districts);
			if (district == null) {
				missingItem = districtName + " district ";
			} else {
				county = getCountyWithName(countyName, district);
				if (null == county) {
					missingItem = countyName + " county ";
				} else {
					subCounty = getSubCountyWithName(subCountyName, county);
					if (null == subCounty) {
						missingItem = subCountyName + " sub-county ";
					} else {
						parish = getParishWithName(parishName, subCounty);
						if (null == parish) {
							missingItem = parishName + " parish ";
						} else {
							village = getVillageWithName(villageName, parish);
							if (null == village) {
								missingItem = villageName + " village ";
							}
						}
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(missingItem)) {
			String error = "";
			if (producer) {
				error = "Ooops cant save Producer,  - " + farmerPorgName + "."
						+ missingItem + " is missing in the database.";
			} else {
				error = "Ooops cant save Producer group - " + porgName + "."
						+ missingItem + " is missing in the database.";
			}
			log.error("Missing Item Error", error);
			throw new ValidationException(error);
		}
		return missingItem;

	}

	private ProducerOrg generateNewPorg(Row row, List<ProducerOrg> porgs,
			List<District> districts) {

		String porgName = row.getCell(
				DistrictProducerImportData.porgNameColumnIndex)
				.getStringCellValue();

		String districtName = cleanDistrictUnitName(row
				.getCell(DistrictProducerImportData.districtColumnIndex)
				.getStringCellValue().toUpperCase());
		String countyName = row.getCell(
				DistrictProducerImportData.countyColumnIndex)
				.getStringCellValue();
		String subCountyName = row.getCell(
				DistrictProducerImportData.subcountyColumnIndex)
				.getStringCellValue();
		String parishName = row.getCell(
				DistrictProducerImportData.parishColumnIndex)
				.getStringCellValue();
		String villageName = row.getCell(
				DistrictProducerImportData.villageColumnIndex)
				.getStringCellValue();

		districtName = cleanDistrictUnitName(districtName.toUpperCase());
		subCountyName = cleanDistrictUnitName(subCountyName.toUpperCase());
		parishName = cleanDistrictUnitName(parishName.toUpperCase());
		villageName = cleanDistrictUnitName(villageName.toUpperCase());

		District district = getDistrictWithName(districtName, districts);
		County county = getCountyWithName(countyName, district);
		SubCounty subCounty = getSubCountyWithName(subCountyName, county);
		Parish parish = getParishWithName(parishName, subCounty);
		Village village = getVillageWithName(villageName, parish);

		ProducerOrg porg = getProducerOrgWithName(porgName, porgs);
		if (porg == null) {
			// save new producer org
			porg = new ProducerOrg(porgName.trim(), district, county,
					subCounty, parish, village);
			return porg;

		} else {
			if (!porg.getSubCounty().equals(subCounty)
					|| !porg.getParish().equals(parish)
					|| !porg.getVillage().equals(village)) {
				log.error("Found samilar producer Org - " + porgName
						+ " in different place - " + districtName + ", "
						+ countyName + ", " + subCountyName + ", " + parishName
						+ ", " + villageName);
			}
			return null;
		}
	}

	public Connection getPostgreSQLDBConnection() throws SQLException,
			ClassNotFoundException {
		// Load the SQLServerDriver class, build the
		// connection string, and get a connection
		Class.forName("org.postgresql.Driver");
		String username = "oxmadmin";
		String pass = "qas1234s";
		String connectionUrl = "jdbc:postgresql://localhost:5432/oxm-db";

		Connection con = DriverManager.getConnection(connectionUrl, username,
				pass);
		System.out.println("Connected to Oxfam PostgreSQL db, oxm-db.");
		return con;
	}
}
