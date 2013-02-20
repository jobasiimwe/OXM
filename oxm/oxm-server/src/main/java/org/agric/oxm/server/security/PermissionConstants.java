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

}
