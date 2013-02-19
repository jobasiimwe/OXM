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
	 * The complaint type concept category
	 */
	@ConceptCategoryAnnotation(id = "CE2485B8-EA69-4eb2-9BAC-25115B5B0FF3", description = "The type of complaint submited by the student")
	public static final String COMPLAINT_TYPE = "Complaint type";

	/**
	 * The complaint status concept category
	 */
	@ConceptCategoryAnnotation(id = "B8C98E8F-4154-475a-BA18-B153CD9B10B4", description = "The status of the complaint submited by the student")
	public static final String COMPLAINT_STATUS = "Complain status";

	/**
	 * The courseType concept category
	 */
	@ConceptCategoryAnnotation(id = "E6389C2A-171F-48b2-B60E-CE7AD8FB9C46", description = "The Course Type of a course in the system")
	public static final String COURSE_TYPE = "Course type";

	/**
	 * The timeOfStuday concept category
	 */
	@ConceptCategoryAnnotation(id = "F7538E54-C9E4-4c36-BDF2-151FD3869DFC", description = "Time of Study of a student in the system (mainly applies to short course students)")
	public static final String TIME_OF_STUDY = "Time of Study";

	/**
	 * The timeUnit concept category
	 */
	@ConceptCategoryAnnotation(id = "51A8CDF1-733A-4f35-A175-DDF2A98FE356", description = "Time Unit e.g. Year(s), Month(s), Week(s), used for course duration")
	public static final String TIME_UNIT = "Time unit";

	/**
	 * The timeUnit concept category
	 */
	@ConceptCategoryAnnotation(id = "B8CB5F8F-11EE-496c-BCCA-1D6836F5DD1A", description = "Complaint event type e.g. Comment, Status_change, Forward")
	public static final String COMPLAINT_EVENT_TYPE = "Complaint Event Type";

	/**
	 * array of the default concept categories
	 */
	public static final String[] DEFAULT_CONCEPT_CATEGORIES = new String[] {
			COMPLAINT_TYPE, COMPLAINT_STATUS, COURSE_TYPE, TIME_OF_STUDY,
			TIME_UNIT, COMPLAINT_EVENT_TYPE };

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
