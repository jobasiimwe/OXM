/**
 * 
 */
package org.agric.oxm.model.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	// ===============================================================

	public static SimpleDateFormat monthName_Year = new SimpleDateFormat(
			"MMMMM - yyyy");

	public static SimpleDateFormat dayNum_month_Time = new SimpleDateFormat(
			"dd - MMMMM, HH:mm a");

	public static SimpleDateFormat dayName_Time = new SimpleDateFormat(
			"EEEEE - hh:MM a");

	public static SimpleDateFormat time = new SimpleDateFormat("hh:MM a");

	public static String getDisplayDate(Date date) {

		SimpleDateFormat fmtToUse = new SimpleDateFormat("dd/MMM/yyyy");
		String displayDate = "";

		Calendar caNow = Calendar.getInstance();
		Calendar calDatePosted = Calendar.getInstance();

		caNow.setTime(new Date());
		calDatePosted.setTime(date);

		if (caNow.get(Calendar.YEAR) == calDatePosted.get(Calendar.YEAR)) {
			if (caNow.get(Calendar.MONTH) == calDatePosted.get(Calendar.MONTH)) {
				if (caNow.get(Calendar.DAY_OF_MONTH) == calDatePosted
						.get(Calendar.DAY_OF_MONTH)) {
					Integer minutes = minuteDifference(caNow, calDatePosted);

					if (minutes < 1)
						return "Just Now";
					if (minutes <= 50)
						return minutes + " minutes ago";

					if (minutes > 50 && minutes < 90)
						return "1 hour ago";

					int hours = Math.round(minutes / 60);

					return hours + " Hours ago";

				} else {

					Integer daysDiff = caNow.get(Calendar.DAY_OF_MONTH)
							- calDatePosted.get(Calendar.DAY_OF_MONTH);

					if (daysDiff == 1)
						return "Yesterday " + time.format(date);
					else if (daysDiff < 7)
						fmtToUse = dayName_Time;
					else
						fmtToUse = dayNum_month_Time;
				}

			} else {
				fmtToUse = dayNum_month_Time;
			}
		} else {
			fmtToUse = monthName_Year;
		}

		displayDate = fmtToUse.format(date);

		return displayDate;
	}

	/**
	 * takes times on the same day and returns minute difference
	 * 
	 * @param now
	 * @param then
	 * @return
	 */
	public static Integer minuteDifference(Calendar now, Calendar then) {

		if (now.get(Calendar.HOUR_OF_DAY) == then.get(Calendar.HOUR_OF_DAY))
			return now.get(Calendar.MINUTE) - then.get(Calendar.MINUTE);
		else
			return now.get(Calendar.MINUTE)
					+ (60 - then.get(Calendar.MINUTE))
					+ ((now.get(Calendar.HOUR_OF_DAY)
							- then.get(Calendar.HOUR_OF_DAY) - 1) * 60);

	}
	
	//=======================================================================================
	
		public static String getDateRangeString(Date startDate, Date endDate) {

			if (MyDateUtil.dateFormat_MMM.format(startDate).equalsIgnoreCase(
					MyDateUtil.dateFormat_MMM.format(endDate))) {

				if (MyDateUtil.dateFormat_yyyy.format(startDate).equalsIgnoreCase(
						MyDateUtil.dateFormat_yyyy.format(endDate)))
					return MyDateUtil.dateFormat_dd.format(startDate) + " - "
							+ MyDateUtil.dateFormat_dd.format(endDate) + " "
							+ MyDateUtil.dateFormat_MMM.format(startDate) + " "
							+ MyDateUtil.dateFormat_yyyy.format(startDate);
				else
					return MyDateUtil.dateFormat_space_dd_MMM.format(startDate)
							+ " - "
							+ MyDateUtil.dateFormat_space_dd_MMM.format(endDate)
							+ " " + MyDateUtil.dateFormat_yyyy.format(startDate);
			} else {
				if (MyDateUtil.dateFormat_yyyy.format(startDate).equalsIgnoreCase(
						MyDateUtil.dateFormat_yyyy.format(endDate)))
					return MyDateUtil.dateFormat_space_dd_MMM.format(startDate)
							+ " - "
							+ MyDateUtil.dateFormat_space_dd_MMM.format(endDate)
							+ " " + MyDateUtil.dateFormat_yyyy.format(startDate);
				else
					return MyDateUtil.dateFormat_dash_dd_MMM_yyyy.format(startDate)
							+ " - "
							+ MyDateUtil.dateFormat_dash_dd_MMM_yyyy
									.format(endDate);
			}
		}
}
