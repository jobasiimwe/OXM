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
	public static final String PERM_ADD_USER = "perm_add_user";

	/**
	 * permission to view a user
	 */
	@PermissionAnnotation(id = "77186806-AB8F-4f61-B4A6-2D894DCAB47C", description = "Ability to view user")
	public static final String PERM_VIEW_USER = "perm_view_user";

	/**
	 * permission to edit a user
	 */
	@PermissionAnnotation(id = "D7D8E11E-A0AB-4fc5-9C40-CC28F180834A", description = "Ability to edit user")
	public static final String PERM_EDIT_USER = "perm_edit_user";

	/**
	 * permission to delete a user
	 */
	@PermissionAnnotation(id = "5D5A8046-98F5-4ef3-ADE5-97135204C31F", description = "Ability to delete user")
	public static final String PERM_DELETE_USER = "perm_delete_user";

	/**
	 * permission to add a concept_category, concepts 
	 */
	@PermissionAnnotation(id = "8841EFC7-EAEF-4b1a-93BD-A387FD1295F4", description = "Ability to add concept details")
	public static final String PERM_ADD_CONCEPT_DETAILS = "perm_add_concept_details";

	/**
	 * permission to view a concept_category, concepts 
	 */
	@PermissionAnnotation(id = "9C72D281-797E-4fe2-A762-C8D1862A1609", description = "Ability to view concept details")
	public static final String PERM_VIEW_CONCEPT_DETAILS = "perm_view_concept_details";

	
	/**
	 * permission to view an concept category, concepts
	 */
	@PermissionAnnotation(id = "44ABDB3F-97BB-4523-9FE1-C730D3B378C2", description = "Ability to edit concept details")
	public static final String PERM_EDIT_CONCEPT_DETAILS = "perm_edit_concept_details";

	/**
	 * permission to delete a concept_category, concepts
	 */
	@PermissionAnnotation(id = "2CD8DF85-152E-4c30-BF74-FE18103A5D2D", description = "Ability to delete concept details")
	public static final String PERM_DELETE_CONCEPT_DETAILS = "perm_delete_concept_details";

	/**
	 * permission to add a role
	 */
	@PermissionAnnotation(id = "618DECE3-194B-41e0-A384-3A1BC72638BB", description = "Ability to add a role")
	public static final String PERM_ADD_ROLE = "perm_add_role";

	/**
	 * permission to view a role
	 */
	@PermissionAnnotation(id = "A129BAE0-8075-4b0c-B0C8-6794249CB05B", description = "Ability to view a role")
	public static final String PERM_VIEW_ROLE = "perm_view_role";

	/**
	 * permission to edit roles
	 */
	@PermissionAnnotation(id = "1043B4BB-2BC5-46e2-A8B9-A978B6B1AA98", description = "Ability to edit a role")
	public static final String PERM_EDIT_ROLE = "perm_edit_role";

	/**
	 * permission to delete a role
	 */
	@PermissionAnnotation(id = "10FE210A-0EBF-45aa-802C-DBD47D458381", description = "Ability to delete a role")
	public static final String PERM_DELETE_ROLE = "perm_delete_role";

}
