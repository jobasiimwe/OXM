package org.agric.oxm.server.security;

/**
 * 
 * @author Job
 * 
 */
public final class PermissionConstants {

	private PermissionConstants() {

	}

	@PermissionAnnotation(id = "A0A08826-96C0-438b-876E-55534ABE0461", description = "Rights for an adminstrator")
	public static final String PERM_ADMIN = "perm_admin";

	@PermissionAnnotation(id = "BE77AA9B-60CC-47F7-B163-12B0064BE0FA", description = "Ability to access web resources if api is used in a web application")
	public static final String PERM_WEB_ACCESS = "perm_web_access";

	/**
	 * permission to view administration control panel ...
	 */
	@PermissionAnnotation(id = "68C2C81F-1492-43c7-9F27-DB4A792540FC", description = "Ability to view the administration control panel ")
	public static final String PERM_VIEW_ADMINISTRATION = "perm_view_administration";

	/**
	 * permission to add a user
	 */
	@PermissionAnnotation(id = "19A55078-045E-43a6-A40D-8E6159C57963", description = "Ability to add user")
	public static final String ADD_USER = "add_user";

	/**
	 * permission to view a user
	 */
	@PermissionAnnotation(id = "77186806-AB8F-4f61-B4A6-2D894DCAB47C", description = "Ability to view user")
	public static final String VIEW_USER = "view_user";

	/**
	 * permission to edit a user
	 */
	@PermissionAnnotation(id = "D7D8E11E-A0AB-4fc5-9C40-CC28F180834A", description = "Ability to edit user")
	public static final String EDIT_USER = "edit_user";

	/**
	 * permission to delete a user
	 */
	@PermissionAnnotation(id = "5D5A8046-98F5-4ef3-ADE5-97135204C31F", description = "Ability to delete user")
	public static final String DELETE_USER = "delete_user";

	/**
	 * permission to add a concept_category, concepts
	 */
	@PermissionAnnotation(id = "8841EFC7-EAEF-4b1a-93BD-A387FD1295F4", description = "Ability to add concept details")
	public static final String ADD_CONCEPT_DETAILS = "add_concept_details";

	/**
	 * permission to view a concept_category, concepts
	 */
	@PermissionAnnotation(id = "9C72D281-797E-4fe2-A762-C8D1862A1609", description = "Ability to view concept details")
	public static final String VIEW_CONCEPT_DETAILS = "view_concept_details";

	/**
	 * permission to view an concept category, concepts
	 */
	@PermissionAnnotation(id = "44ABDB3F-97BB-4523-9FE1-C730D3B378C2", description = "Ability to edit concept details")
	public static final String EDIT_CONCEPT_DETAILS = "edit_concept_details";

	/**
	 * permission to delete a concept_category, concepts
	 */
	@PermissionAnnotation(id = "2CD8DF85-152E-4c30-BF74-FE18103A5D2D", description = "Ability to delete concept details")
	public static final String DELETE_CONCEPT_DETAILS = "delete_concept_details";

	/**
	 * permission to add a role
	 */
	@PermissionAnnotation(id = "618DECE3-194B-41e0-A384-3A1BC72638BB", description = "Ability to add a role")
	public static final String ADD_ROLE = "add_role";

	/**
	 * permission to view a role
	 */
	@PermissionAnnotation(id = "A129BAE0-8075-4b0c-B0C8-6794249CB05B", description = "Ability to view a role")
	public static final String VIEW_ROLE = "view_role";

	/**
	 * permission to edit roles
	 */
	@PermissionAnnotation(id = "1043B4BB-2BC5-46e2-A8B9-A978B6B1AA98", description = "Ability to edit a role")
	public static final String EDIT_ROLE = "edit_role";

	/**
	 * permission to delete a role
	 */
	@PermissionAnnotation(id = "10FE210A-0EBF-45aa-802C-DBD47D458381", description = "Ability to delete a role")
	public static final String DELETE_ROLE = "delete_role";

	/**
	 * permission to add a selling_place
	 */
	@PermissionAnnotation(id = "EB445C15-1531-448e-B682-320E79C16CA1", description = "Ability to add a selling_place")
	public static final String ADD_SELLING_PLACE = "add_selling_place";

	/**
	 * permission to view a selling_place
	 */
	@PermissionAnnotation(id = "D0F6732F-7119-49b0-AAFD-284B5DEDA354", description = "Ability to view a selling_place")
	public static final String VIEW_SELLING_PLACE = "view_selling_place";

	/**
	 * permission to edit selling_places
	 */
	@PermissionAnnotation(id = "AEDF2353-57AB-46ec-909B-86233B283068", description = "Ability to edit a selling_place")
	public static final String EDIT_SELLING_PLACE = "edit_selling_place";

	/**
	 * permission to delete a selling_place
	 */
	@PermissionAnnotation(id = "AC3EADDB-967E-4e54-AEEC-E5F3F76F2009", description = "Ability to delete a selling_place")
	public static final String DELETE_SELLING_PLACE = "delete_selling_place";

	/**
	 * permission to edit comment
	 */
	@PermissionAnnotation(id = "3CAF63B5-FC9A-44c7-9883-E08C0FEAF9C2", description = "Ability to edit a comment")
	public static final String EDIT_COMMENT = "edit_selling_place";

	/**
	 * permission to delete a comment
	 */
	@PermissionAnnotation(id = "2391A5F2-E9A1-47cc-865F-1B8AE5CFE92B", description = "Ability to delete a comment")
	public static final String DELETE_COMMENT = "delete_comment";

	/**
	 * permission to add a crop
	 */
	@PermissionAnnotation(id = "8BB22321-27B3-4c6f-93B0-B85A3B9E3981", description = "Ability to add a crop")
	public static final String ADD_CROP = "add_crop";

	/**
	 * permission to view a crop
	 */
	@PermissionAnnotation(id = "CE05C5C7-90E6-44ad-99E4-499EB3D0D7E9", description = "Ability to view a crop")
	public static final String VIEW_CROP = "view_crop";

	/**
	 * permission to edit crops
	 */
	@PermissionAnnotation(id = "2A1DC5A8-2615-4bac-A20D-1859DAEF6D7F", description = "Ability to edit a crop")
	public static final String EDIT_CROP = "edit_crop";

	/**
	 * permission to delete a crop
	 */
	@PermissionAnnotation(id = "1302D167-8D23-46a0-92D0-092BBBEC2AF9", description = "Ability to delete a crop")
	public static final String DELETE_CROP = "delete_crop";

	/**
	 * permission to add a district_details
	 */
	@PermissionAnnotation(id = "90887B4F-F17B-4da7-8939-3EE5D0CFB0E6", description = "Ability to add a district details")
	public static final String ADD_DISTRICT_DETAILS = "add_district_details";

	/**
	 * permission to view a district_details
	 */
	@PermissionAnnotation(id = "A2637FD4-E26D-4bf2-9A0D-000970E26390", description = "Ability to view a district details")
	public static final String VIEW_DISTRICT_DETAILS = "view_district_details";

	/**
	 * permission to edit district_detailss
	 */
	@PermissionAnnotation(id = "279C70D8-1B14-44d9-AC76-53844EDFF2AE", description = "Ability to edit a district details")
	public static final String EDIT_DISTRICT_DETAILS = "edit_district_details";

	/**
	 * permission to delete a district_details
	 */
	@PermissionAnnotation(id = "74EB56BD-BC53-438e-9D98-92176AF69E1D", description = "Ability to delete a district details")
	public static final String DELETE_DISTRICT_DETAILS = "delete_district_details";

	/**
	 * permission to add a financial institution
	 */
	@PermissionAnnotation(id = "9460DB4B-5199-49f1-B5C8-3140A0646B81", description = "Ability to add a financial institution")
	public static final String ADD_FINANCIAL_INSTITUTION = "add_financial_institution";

	/**
	 * permission to view a financial institution
	 */
	@PermissionAnnotation(id = "14C00675-ED03-4c17-934F-1217E33E6E93", description = "Ability to view a financial institution")
	public static final String VIEW_FINANCIAL_INSTITUTION = "view_financial_institution";

	/**
	 * permission to edit financial institutions
	 */
	@PermissionAnnotation(id = "8432B737-571C-4fea-91B3-0665EDE90471", description = "Ability to edit a financial_institution")
	public static final String EDIT_FINANCIAL_INSTITUTION = "edit_financial_institution";

	/**
	 * permission to delete a financial institution
	 */
	@PermissionAnnotation(id = "17807728-E32A-477e-8372-E082C99AB375", description = "Ability to delete a financial institution")
	public static final String DELETE_FINANCIAL_INSTITUTION = "delete_financial_institution";

	/**
	 * permission to add a land use
	 */
	@PermissionAnnotation(id = "0DAE7F97-67F9-47f7-A41C-D9CDBB4E1DAB", description = "Ability to add a land use")
	public static final String ADD_LAND_USE = "add_land_use";

	/**
	 * permission to view a land use
	 */
	@PermissionAnnotation(id = "0EE81CFB-0966-4096-9C2E-01C17528FE2B", description = "Ability to view a land use")
	public static final String VIEW_LAND_USE = "view_land_use";

	/**
	 * permission to edit land uses
	 */
	@PermissionAnnotation(id = "D0A4CAE4-AB7E-412c-91C7-13EC31403850", description = "Ability to edit a land_use")
	public static final String EDIT_LAND_USE = "edit_land_use";

	/**
	 * permission to delete a land use
	 */
	@PermissionAnnotation(id = "17114C97-ABC6-4a18-8F05-BCE684FD2357", description = "Ability to delete a land use")
	public static final String DELETE_LAND_USE = "delete_land_use";

	/**
	 * permission to add a producer
	 */
	@PermissionAnnotation(id = "4A9D933E-4D1D-4221-9671-66FD8F2DF497", description = "Ability to add a producer")
	public static final String ADD_PRODUCER = "add_producer";

	/**
	 * permission to view a producer
	 */
	@PermissionAnnotation(id = "E24C3F4B-F65C-4299-A842-7C1830694FBE", description = "Ability to view a producer")
	public static final String VIEW_PRODUCER = "view_producer";

	/**
	 * permission to edit producers
	 */
	@PermissionAnnotation(id = "A9ACEB8F-2E90-41f0-94AA-C3538C46EB24", description = "Ability to edit a producer")
	public static final String EDIT_PRODUCER = "edit_producer";

	/**
	 * permission to delete a producer
	 */
	@PermissionAnnotation(id = "00B29FB9-C7E6-4a29-B98C-FF263B49077D", description = "Ability to delete a producer")
	public static final String DELETE_PRODUCER = "delete_producer";

	/**
	 * permission to add a message
	 */
	@PermissionAnnotation(id = "36C9BB9E-88B7-48ed-8BE2-9675A6FBB7ED", description = "Ability to add a message")
	public static final String ADD_MESSAGE = "add_message";

	/**
	 * permission to view a message
	 */
	@PermissionAnnotation(id = "7EEBF022-37B6-4bfa-A825-E8314A4CCD4A", description = "Ability to view a message")
	public static final String VIEW_MESSAGE = "view_message";

	/**
	 * permission to edit messages
	 */
	@PermissionAnnotation(id = "78BA4961-E2DF-4791-8CE0-57D6BAC6E6BA", description = "Ability to edit a message")
	public static final String EDIT_MESSAGE = "edit_message";

	/**
	 * permission to delete a message
	 */
	@PermissionAnnotation(id = "3BDE7750-24CD-4b4f-B5F8-8E1B48527350", description = "Ability to delete a message")
	public static final String DELETE_MESSAGE = "delete_message";

	/**
	 * permission to add a committee, members and positions
	 */
	@PermissionAnnotation(id = "B024A53E-F1EC-498d-8853-7B51AE72EABA", description = "Ability to add a committee, members and positions")
	public static final String ADD_COMMITTEE_DETAILS = "add_committee_details";

	/**
	 * permission to view a committee, members and positions
	 */
	@PermissionAnnotation(id = "A9D6DFC9-BEE5-4c1d-960C-B3BC447BF7A3", description = "Ability to view a committee, members and positions")
	public static final String VIEW_COMMITTEE__DETAILS = "view_committee_details";

	/**
	 * permission to edit committee, members and positions
	 */
	@PermissionAnnotation(id = "DB0A0DFC-D8EA-4a9e-82AD-F677A969D3E8", description = "Ability to edit a committee, members and positions")
	public static final String EDIT_COMMITTEE__DETAILS = "edit_committee_details";

	/**
	 * permission to delete a committee, members and positions
	 */
	@PermissionAnnotation(id = "030FA7B1-6560-46c1-805A-86C929A2154A", description = "Ability to delete a committee, members and positions")
	public static final String DELETE_COMMITTEE__DETAILS = "delete_committee_details";

	/**
	 * permission to add a post
	 */
	@PermissionAnnotation(id = "74701519-3D5A-42aa-89FF-15D12AAD1B6E", description = "Ability to add a post")
	public static final String ADD_POST = "add_post";

	/**
	 * permission to view a post
	 */
	@PermissionAnnotation(id = "FEEE4B8E-6CC8-4bdd-A8D3-D11B1E5167EF", description = "Ability to view a post")
	public static final String VIEW_POST = "view_post";

	/**
	 * permission to edit posts
	 */
	@PermissionAnnotation(id = "9C97A4FE-A253-4ede-828D-18F050CDF340", description = "Ability to edit a post")
	public static final String EDIT_POST = "edit_post";

	/**
	 * permission to delete a post
	 */
	@PermissionAnnotation(id = "29642CA9-5BCB-4713-AB3D-45919CB8AD20", description = "Ability to delete a post")
	public static final String DELETE_POST = "delete_post";

	/**
	 * permission to add a price
	 */
	@PermissionAnnotation(id = "A31EC2A6-193E-4b9c-9C71-85A85AE6C303", description = "Ability to add a price")
	public static final String ADD_PRICE = "add_price";

	/**
	 * permission to view a price
	 */
	@PermissionAnnotation(id = "E51044F5-B4B4-4a08-95E7-82D6AEDFEC28", description = "Ability to view a price")
	public static final String VIEW_PRICE = "view_price";

	/**
	 * permission to edit prices
	 */
	@PermissionAnnotation(id = "AC985F46-CE39-4695-B410-A42865CDFF18", description = "Ability to edit a price")
	public static final String EDIT_PRICE = "edit_price";

	/**
	 * permission to delete a price
	 */
	@PermissionAnnotation(id = "F929DA00-0552-4c17-A6B2-300DC028218B", description = "Ability to delete a price")
	public static final String DELETE_PRICE = "delete_price";

	/**
	 * permission to add a prod level
	 */
	@PermissionAnnotation(id = "49DA4D58-45A3-49d6-8612-1F262726CDBA", description = "Ability to add a prod level")
	public static final String ADD_PROD_LEVEL = "add_prod_level";

	/**
	 * permission to view a prod level
	 */
	@PermissionAnnotation(id = "9FEC2B36-1DD5-4dd7-8379-10989E65FD00", description = "Ability to view a prod level")
	public static final String VIEW_PROD_LEVEL = "view_prod_level";

	/**
	 * permission to edit prod_levels
	 */
	@PermissionAnnotation(id = "90F7885A-60C9-4fcd-AE33-E5A983F2CEF2", description = "Ability to edit a prod level")
	public static final String EDIT_PROD_LEVEL = "edit_prod_level";

	/**
	 * permission to delete a prod level
	 */
	@PermissionAnnotation(id = "40485885-1D67-45db-AECF-8F6D896B211C", description = "Ability to delete a prod level")
	public static final String DELETE_PROD_LEVEL = "delete_prod_level";

	// ---------------------------

	/**
	 * permission to add a prod organization
	 */
	@PermissionAnnotation(id = "F25AA7C5-4F4F-460a-B30F-D1685F56A076", description = "Ability to add a prod organisation")
	public static final String ADD_PROD_ORG = "add_prod_org";

	/**
	 * permission to view a prod organization
	 */
	@PermissionAnnotation(id = "8957BAEA-61B8-4281-94E5-3FDB889772E9", description = "Ability to view a prod organisation")
	public static final String VIEW_PROD_ORG = "view_prod_org";

	/**
	 * permission to edit prod organization
	 */
	@PermissionAnnotation(id = "91E9CDE7-0017-4efc-8260-D4DECAD48795", description = "Ability to edit a prod organisation")
	public static final String EDIT_PROD_ORG = "edit_prod_org";

	/**
	 * permission to delete a prod organization
	 */
	@PermissionAnnotation(id = "B4A66B4D-BB9D-4e4a-8797-35DB9C8C9A77", description = "Ability to delete a prod organisation")
	public static final String DELETE_PROD_ORG = "delete_prod_org";

	@PermissionAnnotation(id = "B000IIIK44-BB9D-4e4a-8797-567UUUUHG", description = "Ability to perform CRUD operations on season")
	public static final String MANAGE_SEASONS = "manage_season";

	@PermissionAnnotation(id = "56555IIjGWRW-BB9D-4e4a-8797-00USDSD4", description = "Ability to view seasons")
	public static final String VIEW_SEASONS = "view_season";

	@PermissionAnnotation(id = "5PSQQQH-458e-BB9D-4e4a-8797-00USDSD4", description = "Ability to save annoymous user")
	public static final String ANNONYMOUS_USER = "annonymous_user";

	// ===========================================================================================

	/**
	 * permission to add a product
	 */
	@PermissionAnnotation(id = "240136B8-B344-49e5-8ED5-86381F35B62D", description = "Ability to add a product")
	public static final String ADD_PRODUCT = "add_product";

	/**
	 * permission to view a product
	 */
	@PermissionAnnotation(id = "180A96C0-D107-4f5a-8A07-929669EB3389", description = "Ability to view a product")
	public static final String VIEW_PRODUCT = "view_product";

	/**
	 * permission to edit products
	 */
	@PermissionAnnotation(id = "C161A7A8-16BD-4bb8-BD3D-4FC23A2AEC2F", description = "Ability to edit a product")
	public static final String EDIT_PRODUCT = "edit_product";

	/**
	 * permission to delete a product
	 */
	@PermissionAnnotation(id = "09B5700D-EEC4-4476-A03E-F302A9790F34", description = "Ability to delete a product")
	public static final String DELETE_PRODUCT = "delete_product";

	/**
	 * permission to export to excel
	 */
	@PermissionAnnotation(id = "23E4456B-C8CD-4cc8-BDF8-48944551AC34", description = "Ability to export to excell")
	public static final String EXPORT_TO_EXCELL = "export_to_excell";
	
	/**
	 * permission to export to excel
	 */
	@PermissionAnnotation(id = "export_to_pdf_id", description = "Ability to export to pdf")
	public static final String EXPORT_TO_PDF = "export_to_pdf";

}
