package org.agric.oxm.server;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is used for annotating system defined concepts
 * 
 * @author ctumwebaze
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConceptAnnotation {

    /**
     * system assigned id for the concept
     * 
     * @return
     */
    String id();

    /**
     * the description of the concept
     * 
     * @return
     */
    String description();

    /**
     * the concept category attached to this concept
     */
    String[] ConceptCategories();
}
