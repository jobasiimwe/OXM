package org.agric.oxm.web;

import java.lang.reflect.Field;

import org.agric.oxm.model.Gender;
import org.agric.oxm.server.ConceptAnnotation;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OXMUtil {

    private OXMUtil() {
	LOG.warn("");
	throw new UnsupportedOperationException();
    }

    private static final Logger LOG = LoggerFactory.getLogger(OXMUtil.class);

    /*public static String getApplicationDataDirectory() {

	String filepath = null;
	if (OXMConstants.OPERATING_SYSTEM_LINUX.equalsIgnoreCase(OXMConstants.OPERATING_SYSTEM)
		|| OXMConstants.OPERATING_SYSTEM_FREEBSD
		.equalsIgnoreCase(OXMConstants.OPERATING_SYSTEM)
		|| OXMConstants.OPERATING_SYSTEM_OSX
		.equalsIgnoreCase(OXMConstants.OPERATING_SYSTEM)) {

	    filepath = System.getProperty("user.home") + File.separator + ".oxfam_files";
	} else {
	    filepath = System.getProperty("user.home") + File.separator + "Application Data"
		    + File.separator + "oxfam_files";
	}

	filepath = filepath + File.separator;

	File folder = new File(filepath);
	if (!folder.exists()) {
	    folder.mkdirs();
	}

	return filepath;
    }
*/
    public static ConceptCategoryAnnotation getConceptCategoryFieldAnnotation(
	    Class<?> containingClass, String conceptCategory)
	    throws SecurityException, NoSuchFieldException {
	Field[] fields = containingClass.getFields();
	for (Field field : fields) {
	    try {
		String fieldValue = (String) field.get(conceptCategory);
		if (fieldValue != null && StringUtils.equals(fieldValue, conceptCategory)) {
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

    public static ConceptAnnotation getConceptFieldAnnotation(Class<?> containingClass,
	    String conceptName) throws SecurityException, NoSuchFieldException {
	Field[] fields = containingClass.getFields();
	for (Field field : fields) {
	    try {
		String fieldValue = (String) field.get(conceptName);
		if (fieldValue != null && StringUtils.equals(fieldValue, conceptName)) {
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

    public static Gender[] getGenderList() {
	return new Gender[] { Gender.FEMALE, Gender.MALE, Gender.UNKNOWN };
    }


    public static String createUsername(String first, String last) {
	String username;
	first = first.toLowerCase();
	last = last.toLowerCase();
	if (last.indexOf(' ') > 0) {
	    username = last
		    .substring(0, last.indexOf(' '));
	} else {
	    username = last;
	}

	if (first.indexOf(' ') > 0) {
	    username += "."
		    + first
		    .substring(0, first.indexOf(' '));
	} else {
	    username += "." + first;
	}
	return username;
    }
}
