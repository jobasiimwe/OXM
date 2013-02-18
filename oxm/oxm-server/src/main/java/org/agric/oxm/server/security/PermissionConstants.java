package org.agric.oxm.server.security;



public final class PermissionConstants {

    private PermissionConstants() {

    }


    @PermissionAnnotation(id = "A0A08826-96C0-438b-876E-55534ABE0461",
	    description = "Rights for an adminstrator")
    public static final String PERM_ADMIN = "perm_admin";

    @PermissionAnnotation(
	    id = "BE77AA9B-60CC-47F7-B163-12B0064BE0FA",
	    description = "Ability to access web resources if api is used in a web application")
    public static final String PERM_WEB_ACCESS = "perm_web_access";
}
