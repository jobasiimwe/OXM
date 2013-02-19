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
	 * concept Missing coursework marks under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "911024D6-ECA4-44e0-9F33-B3C7E42B5A45", description = "Missing coursework marks", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String MISSING_COURSEWORK_MARKS = "Missing coursework marks";

	/**
	 * concept Missing exam marks under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "79728EC6-5759-4a1d-B821-7D33F735E879", description = "Missing exam marks", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String MISSING_EXAM_MARKS = "Missing exam marks";

	/**
	 * concept Missing all marks under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "178C1D52-DB15-4004-9318-1745676CE5AA", description = "Missing all marks", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String MISSING_ALL_MARKS = "Missing all marks";

	/**
	 * concept Showing retake under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "EA0D4988-F888-4b53-93CE-1CC2D37C8FFB", description = "Showing retake", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String SHOWING_RETAKE = "Showing retake";

	/**
	 * concept Asking for remarking under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "0C0E434C-424D-4ea9-8089-62D98286CCE1", description = "Asking for remarking", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String REMARK = "Asking for remarking";

	/**
	 * concept Wrong marks under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "309BD3DB-D77F-42f8-8777-ACDE143B4D04", description = "Concept Wrong marks", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String WRONG_MARKS = "Wrong marks";

	/**
	 * concept Other type under category COMPLAINT TYPE
	 */
	@ConceptAnnotation(id = "35B60D4D-B560-450e-A166-303DE74A56ED", description = "Concept Other", ConceptCategories = { DefaultConceptCategories.COMPLAINT_TYPE })
	public static final String OTHER = "Other type";

	/**
	 * concept "Unknown Under category COMPLAINT_STATUS
	 */
	@ConceptAnnotation(id = "303DC446-78EB-49e2-B7B9-9E0F86BB0E1A", description = "Unknown complaint status", ConceptCategories = { DefaultConceptCategories.COMPLAINT_STATUS })
	public static final String UNKNOWN = "Unknown";

	/**
	 * concept "New" Under category COMPLAINT_STATUS
	 */
	@ConceptAnnotation(id = "DDD6A821-175F-40e5-8470-ED96AE2F2412", description = "Complaint status New", ConceptCategories = { DefaultConceptCategories.COMPLAINT_STATUS })
	public static final String NEW = "New";

	/**
	 * concept "Accepted" Under category COMPLAINT_STATUS
	 */
	@ConceptAnnotation(id = "16697875-791B-41ee-8F67-C57A714FE578", description = "Complaint status Accepted", ConceptCategories = { DefaultConceptCategories.COMPLAINT_STATUS })
	public static final String ACCEPTED = "Accepted";

	/**
	 * concept Resolved Under category COMPLAINT_STATUS
	 */
	@ConceptAnnotation(id = "75D5B20E-A533-43b4-8715-887310BE208E", description = "Complaint status Resolved", ConceptCategories = { DefaultConceptCategories.COMPLAINT_STATUS })
	public static final String RESOLVED = "Resolved";

	/**
	 * concept "Open" Under category COMPLAINT_STATUS
	 */
	@ConceptAnnotation(id = "4E08DFBD-758C-4ab5-AA23-11C73E764721", description = "Complaint status Open", ConceptCategories = { DefaultConceptCategories.COMPLAINT_STATUS })
	public static final String OPEN = "Open";

	/**
	 * concept "Closed" Under category COMPLAINT_STATUS
	 */
	@ConceptAnnotation(id = "B0B0371D-65FC-404a-87B0-1DBFB2CEDDED", description = "Complaint status Closed", ConceptCategories = { DefaultConceptCategories.COMPLAINT_STATUS })
	public static final String CLOSED = "Closed";

	/**
	 * concept "Diploma" Under category COURSE_TYPE
	 */
	@ConceptAnnotation(id = "18047DAA-BCB0-425f-B739-3A9735315DB4", description = "Course type Diploma", ConceptCategories = { DefaultConceptCategories.COURSE_TYPE })
	public static final String DIPLOMA = "Diploma";

	/**
	 * concept "Bachelors" Under category COURSE_TYPE
	 */
	@ConceptAnnotation(id = "62C2AF72-0675-44b7-AD3E-63B0BBC2E187", description = "Course type Bachelors", ConceptCategories = { DefaultConceptCategories.COURSE_TYPE })
	public static final String BACHELORS = "Bachelors";

	/**
	 * concept "PGD" Under category COURSE_TYPE
	 */
	@ConceptAnnotation(id = "87D845A3-F86F-4886-9516-AD1C465FDE60", description = "Course type Post graduate diploma (PGD)", ConceptCategories = { DefaultConceptCategories.COURSE_TYPE })
	public static final String POST_GRADUATE_DIPLOMA = "PGD";

	/**
	 * concept "PhD" Under category COURSE_TYPE
	 */
	@ConceptAnnotation(id = "03E98F3D-3C2B-48a7-8CE9-99CF58543B0E", description = "Course type PhD", ConceptCategories = { DefaultConceptCategories.COURSE_TYPE })
	public static final String PHD = "PhD";

	/**
	 * concept "Masters" Under category COURSE_TYPE
	 */
	@ConceptAnnotation(id = "98BD6367-BD2E-4116-A160-03BA525DFF38", description = "Course type Masters", ConceptCategories = { DefaultConceptCategories.COURSE_TYPE })
	public static final String MASTERS = "Masters";

	/**
	 * concept "Short Course" Under category COURSE_TYPE
	 */
	@ConceptAnnotation(id = "A5426D80-D0D4-4d15-A549-C442F7E65D4F", description = "Course type Short Course", ConceptCategories = { DefaultConceptCategories.COURSE_TYPE })
	public static final String SHORT_COURSE = "Short Course";

	/**
	 * concept "DAY" Under category TIME_OF_STUDY
	 */
	@ConceptAnnotation(id = "EC742F4B-87C0-4705-ADAC-90167501E8AF", description = "Time of study Day", ConceptCategories = { DefaultConceptCategories.TIME_OF_STUDY })
	public static final String DAY = "Day";

	/**
	 * concept "EVENING" Under category TIME_OF_STUDY
	 */
	@ConceptAnnotation(id = "16782AB4-BB7F-4374-BD02-E3B604DF22FD", description = "Time of study Evening", ConceptCategories = { DefaultConceptCategories.TIME_OF_STUDY })
	public static final String EVENING = "Evening";

	/**
	 * concept "Early Morning" Under category TIME_OF_STUDY
	 */
	@ConceptAnnotation(id = "C864398D-061C-4d0b-959C-33195AB55C53", description = "Time of study Early Morning", ConceptCategories = { DefaultConceptCategories.TIME_OF_STUDY })
	public static final String EARLY_MORNING = "Early Morning";

	/**
	 * concept "Mid Morning" Under category TIME_OF_STUDY
	 */
	@ConceptAnnotation(id = "C5FB3F8D-207D-42fd-B28C-49E42F46F2CB", description = "Time of study Mid Morning", ConceptCategories = { DefaultConceptCategories.TIME_OF_STUDY })
	public static final String MID_MORNING = "Mid Morning";

	/**
	 * concept "AFTERNOON" Under category TIME_OF_STUDY
	 */
	@ConceptAnnotation(id = "E24FA369-AD1A-44f1-BF38-46D4E121C6C9", description = "Time of study Afternoon", ConceptCategories = { DefaultConceptCategories.TIME_OF_STUDY })
	public static final String AFTERNOON = "Afternoon";

	/**
	 * concept "YEAR(S)" Under category TIME_UNIT
	 */
	@ConceptAnnotation(id = "9435B17C-26C9-4bd3-B601-013EDBD427BA", description = "Time In years", ConceptCategories = { DefaultConceptCategories.TIME_UNIT })
	public static final String YEARS = "Year(s)";

	/**
	 * concept "Bachelors" Under category TIME_UNIT
	 */
	@ConceptAnnotation(id = "1844C901-1B64-4d1b-93D6-E4CA7A86A165", description = "Time In Months", ConceptCategories = { DefaultConceptCategories.TIME_UNIT })
	public static final String MONTHS = "Month(s)";

	/**
	 * concept "PhD" Under category TIME_UNIT
	 */
	@ConceptAnnotation(id = "E9DFF98A-EE31-48b8-B05C-750078E34F2C", description = "Time In weeks", ConceptCategories = { DefaultConceptCategories.TIME_UNIT })
	public static final String WEEKS = "Week(s)";

	/**
	 * concept "DAYS" Under category TIME_UNIT
	 */
	@ConceptAnnotation(id = "E2D27A6C-D643-40db-9300-17C032520E57", description = "Time In Days", ConceptCategories = { DefaultConceptCategories.TIME_UNIT })
	public static final String DAYS = "Day(s)";

	/**
	 * concept "Forward" Under category COMPLAINT_EVENT_TYPE
	 */
	@ConceptAnnotation(id = "883ACD9C-3EE6-4388-ACFC-D31B7B2345DB", description = "Forward complaint", ConceptCategories = { DefaultConceptCategories.COMPLAINT_EVENT_TYPE })
	public static final String FORWARD = "Forward complaint";

	/**
	 * concept "Status Change" Under category COMPLAINT_EVENT_TYPE
	 */
	@ConceptAnnotation(id = "B564C465-F460-4ced-B111-D627AEAAC401", description = "Status Change", ConceptCategories = { DefaultConceptCategories.COMPLAINT_EVENT_TYPE })
	public static final String STATUS_CHANGE = "Status Change";

	/**
	 * concept "Comment" Under category COMPLAINT_EVENT_TYPE
	 */
	@ConceptAnnotation(id = "4D7B8865-8817-4757-BC91-1CD21A45A20E", description = "Comment", ConceptCategories = { DefaultConceptCategories.COMPLAINT_EVENT_TYPE })
	public static final String COMMENT = "Comment";

	public static String getConceptId(String name) {
		try {
			ConceptAnnotation annotation = ConceptsUtil.getConceptFieldAnnotation(
					DefaultConcepts.class, name);
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
