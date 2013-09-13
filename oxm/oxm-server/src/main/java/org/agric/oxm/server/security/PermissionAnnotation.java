package org.agric.oxm.server.security;



import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionAnnotation {

    String id();

    String description();
}
