package org.agric.oxm.utils;

import java.lang.reflect.Field;

import org.agric.oxm.server.ConceptAnnotation;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OXM Utilities.
 * 
 * @author Job
 * 
 */
public final class ConceptsUtil {

	/**
	 * default constructor. does not allow instantiation
	 */
	private ConceptsUtil() {
		// prevents calls from subclass
		LOG.warn("");
		throw new UnsupportedOperationException();
	}

	/** The logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ConceptsUtil.class);

	/**
	 * gets the concept category annotation for the given containing class and
	 * concept category
	 * 
	 * @param containingClass
	 *            the class containing the field whose concept category
	 *            annotation is required
	 * @param conceptCategory
	 *            the concept category whose concept category annotation is
	 *            required
	 * @return
	 * @throws SecurityException
	 *             thrown if we have no access to the field of the containing
	 *             class by reflection
	 * @throws NoSuchFieldException
	 *             thrown if the field doesn't exist in the given containing
	 *             class
	 */
	public static ConceptCategoryAnnotation getConceptCategoryFieldAnnotation(
			Class<?> containingClass, String conceptCategory)
			throws SecurityException, NoSuchFieldException {
		Field[] fields = containingClass.getFields();
		for (Field field : fields) {
			try {
				String fieldValue = (String) field.get(conceptCategory);
				if (fieldValue != null
						&& StringUtils.equals(fieldValue, conceptCategory)) {
					return field.getAnnotation(ConceptCategoryAnnotation.class);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * gets the concept annotation for the given field in the given containing
	 * class
	 * 
	 * @param containingClass
	 *            the class containing the field whose concept annotation is
	 *            required
	 * @param conceptName
	 *            the name of the concept whose concept annotation is required.
	 * @return
	 * @throws SecurityException
	 *             thrown if we have no access to the field of the containing
	 *             class by reflection
	 * @throws NoSuchFieldException
	 *             thrown if the field doesn't exist in the given containing
	 *             class
	 */
	public static ConceptAnnotation getConceptFieldAnnotation(
			Class<?> containingClass, String conceptName)
			throws SecurityException, NoSuchFieldException {
		Field[] fields = containingClass.getFields();
		for (Field field : fields) {
			try {
				String fieldValue = (String) field.get(conceptName);
				if (fieldValue != null
						&& StringUtils.equals(fieldValue, conceptName)) {
					return field.getAnnotation(ConceptAnnotation.class);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
