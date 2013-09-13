package org.agric.oxm.server;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is used for annotating Concept categories in the system
 * 
 * @author ctumwebaze
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConceptCategoryAnnotation {

    /**
     * system assigned id for the concept category
     * 
     * @return
     */
    String id();

    /**
     * the description of the concept category
     * 
     * @return
     */
    String description();
}
