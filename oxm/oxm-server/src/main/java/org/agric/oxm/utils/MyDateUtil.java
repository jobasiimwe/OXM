/**
 * 
 */
package org.agric.oxm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Job
 * 
 */
public class MyDateUtil {

	/**
	 * The date formats used to parse a String into a Date
	 */
	public final static String[] DEFAULT_INPUT_FORMATS = { "dd/MM/yyyy",
			"dd-MM-yyyy", "dd/MM/yy", "dd-MM-yy", "dd/MMM/yyyy", "dd-MMM-yyyy" };

	public static SimpleDateFormat dateFormat_stroke_dd_MM_yyyy = new SimpleDateFormat(
			"dd/MM/yyyy");

	public static SimpleDateFormat dateFormat_dd_MMM_yy = new SimpleDateFormat(
			"dd-MMM-yy");

	public static SimpleDateFormat dateFormat_dd_MMM_yyyy = new SimpleDateFormat(
			"dd-MMM-yyyy");

	public static SimpleDateFormat dateFormat_yyyy_mm_dd_HH_MM_ss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static DateFormat dateFormat_dash_dd_MMM_yyyy = new SimpleDateFormat(
			"dd-MMM-yyyy");
	public static DateFormat dateFormat_space_dd_MMM = new SimpleDateFormat(
			"dd MMM");
	public static DateFormat dateFormat_MMM = new SimpleDateFormat("MMM");
	public static DateFormat dateFormat_yyyy = new SimpleDateFormat("yyyy");
	public static DateFormat dateFormat_dd = new SimpleDateFormat("dd");

	public static String getMonthShortName(Integer num) {
		switch (num) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "NOV";
		case 12:
			return "DEC";
		default:
			return "";
		}
	}

}
