package org.agric.oxm.server;

import org.agric.oxm.utils.ConceptsUtil;

/**
 * This class contains definition of the default system defined concepts
 * 
 * @author ctumwebaze
 * 
 */
public final class DefaultConcepts {

	/**
	 * default constructor
	 */
	private DefaultConcepts() {

	}

	/**
	 * concept Retail Under category SELL_TYPE
	 */
	@ConceptAnnotation(id = "303DC446-78EB-49e2-B7B9-9E0F86BB0E1A", description = "Sell type - retial", ConceptCategories = { DefaultConceptCategories.SELLING_TYPE })
	public static final String RETAIL = "Retail";

	/**
	 * concept Whole sale Under category SELLING TYPE
	 */
	@ConceptAnnotation(id = "DDD6A821-175F-40e5-8470-ED96AE2F2412", description = "Sell type - whole sale", ConceptCategories = { DefaultConceptCategories.SELLING_TYPE })
	public static final String WHOLE_SALE = "Whole sale";

	/**
	 * concept Advert Under category FORUM_POST_TYPE
	 */
	@ConceptAnnotation(id = "75D5B20E-A533-43b4-8715-887310BE208E", description = "Advert post in forum", ConceptCategories = { DefaultConceptCategories.FORUM_POST_TYPE })
	public static final String ADVERT = "Advert";

	/**
	 * concept Oder Under category FORUM_POST_TYPE
	 */
	@ConceptAnnotation(id = "4E08DFBD-758C-4ab5-AA23-11C73E764721", description = "Oder post in forum", ConceptCategories = { DefaultConceptCategories.FORUM_POST_TYPE })
	public static final String ODER = "Oder";

	/**
	 * concept Kilogram Under category UNITS_OF_MEASURE
	 */
	@ConceptAnnotation(id = "B0B0371D-65FC-404a-87B0-1DBFB2CEDDED", description = "Kilogram", ConceptCategories = { DefaultConceptCategories.UNITS_OF_MEASURE })
	public static final String KG = "Kg";

	/**
	 * concept ton Under category UNITS_OF_MEASURE
	 */
	@ConceptAnnotation(id = "18047DAA-BCB0-425f-B739-3A9735315DB4", description = "Ton - unit of measure", ConceptCategories = { DefaultConceptCategories.UNITS_OF_MEASURE })
	public static final String TON = "Ton";
	
	@ConceptAnnotation(id = "18KKKJS-BCB0-425f-B739-89PoQ4", description = "Dry", ConceptCategories = { DefaultConceptCategories.SEASON_NAME })
	public static final String DRY = "Dry";

	public static String getConceptId(String name) {
		try {
			ConceptAnnotation annotation = ConceptsUtil
					.getConceptFieldAnnotation(DefaultConcepts.class, name);
			if (annotation != null) {
				return annotation.id();
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return null;
	}
}
