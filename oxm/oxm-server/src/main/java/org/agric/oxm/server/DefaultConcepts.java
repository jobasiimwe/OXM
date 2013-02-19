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
	 * concept producer under category PRODUCER
	 */
	@ConceptAnnotation(id = "911024D6-ECA4-44e0-9F33-B3C7E42B5A45", description = "User type producer", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String PRODUCER = "User type producer";

	/**
	 * concept Exporter under category USER_TYPE
	 */
	@ConceptAnnotation(id = "79728EC6-5759-4a1d-B821-7D33F735E879", description = "User type Exporter", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String EXPORTER = "Exporter";

	/**
	 * concept Importer under category USER_TYPE
	 */
	@ConceptAnnotation(id = "178C1D52-DB15-4004-9318-1745676CE5AA", description = "User type Importer", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String IMPORTER = "Importer";

	/**
	 * concept Super Market under category USER_TYPE
	 */
	@ConceptAnnotation(id = "EA0D4988-F888-4b53-93CE-1CC2D37C8FFB", description = "User type Super Market", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String SUPER_MARKET = "Super Market";

	/**
	 * concept Argo-Processor under category USER_TYPE
	 */
	@ConceptAnnotation(id = "0C0E434C-424D-4ea9-8089-62D98286CCE1", description = "User type Argo-Processor", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String AGRO_PROCESSOR = "Argo-Processor";

	/**
	 * concept HOTEL under category USER_TYPE
	 */
	@ConceptAnnotation(id = "309BD3DB-D77F-42f8-8777-ACDE143B4D04", description = "User type Hotel", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String HOTEL = "Hotel";

	/**
	 * concept Restaurant under category USER_TYPE
	 */
	@ConceptAnnotation(id = "35B60D4D-B560-450e-A166-303DE74A56ED", description = "User type Restaurant", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String RESTAURANT = "RESTAURANT";

	/**
	 * concept Caterer under category USER_TYPE
	 */
	@ConceptAnnotation(id = "35B60D4D-B560-450e-A166-303DE74A56ED", description = "User type Caterer", ConceptCategories = { DefaultConceptCategories.USER_TYPE })
	public static final String CATERER = "Caterer";

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
