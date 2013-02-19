package org.agric.oxm.server;

import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.utils.ConceptsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains definition of the default concept categories
 * 
 * @author ctumwebaze
 * 
 */
public final class DefaultConceptCategories {

	private static Logger log = LoggerFactory
			.getLogger(DefaultConceptCategories.class);

	/**
	 * default concept categories
	 */
	private DefaultConceptCategories() {

	}

	/**
	 * The Units of measure concept category
	 */
	@ConceptCategoryAnnotation(id = "CE2485B8-EA69-4eb2-9BAC-25115B5B0FF3", description = "Units of measure")
	public static final String UNITS_OF_MEASURE = "Units of measure";

	/**
	 * The User type concept category
	 */
	@ConceptCategoryAnnotation(id = "B8C98E8F-4154-475a-BA18-B153CD9B10B4", description = "User types")
	public static final String USER_TYPE = "User type";

	/**
	 * The Selling Type Concept category
	 */
	@ConceptCategoryAnnotation(id = "E6389C2A-171F-48b2-B60E-CE7AD8FB9C46", description = "Selling type i.e retail or whole-sale")
	public static final String SELLING_TYPE = "Selling type";

	/**
	 * The Forum post type Concept category
	 */
	@ConceptCategoryAnnotation(id = "92753888-C9D6-44e6-8261-B33A12B10279", description = "Buy and Sell Forum post type")
	public static final String FORUM_POST_TYPE = "Forum post type";

	/**
	 * array of the default concept categories
	 */
	public static final String[] DEFAULT_CONCEPT_CATEGORIES = new String[] {
			UNITS_OF_MEASURE, USER_TYPE, SELLING_TYPE, FORUM_POST_TYPE };

	/**
	 * gets the id of the field in this class representing the given concept
	 * category required
	 * 
	 * @param conceptCategory
	 * @return
	 */
	public static final String getConceptCategoryId(String conceptCategory) {
		try {
			ConceptCategoryAnnotation annotation = ConceptsUtil
					.getConceptCategoryFieldAnnotation(
							DefaultConceptCategories.class, conceptCategory);
			if (annotation != null) {
				return annotation.id();
			}

		} catch (SecurityException e) {
			log.error(null, e);
		} catch (NoSuchFieldException e) {
			log.error(null, e);
		}

		return null;
	}

	/**
	 * gets the {@link ConceptCategory} for the given category name
	 * 
	 * @param conceptService
	 * @param name
	 * @return
	 */
	public static ConceptCategory getConceptCategory(
			ConceptService conceptService, String name) {
		return conceptService
				.getConceptCategoryById(getConceptCategoryId(name));
	}
}
