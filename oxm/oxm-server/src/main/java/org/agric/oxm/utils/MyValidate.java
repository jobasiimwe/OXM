package org.agric.oxm.utils;

import java.util.Date;

import org.agric.oxm.model.exception.ValidationException;
import org.apache.commons.lang.StringUtils;

public class MyValidate {

	public static void isNotNull(Object object, String name)
			throws ValidationException {
		if (object == null)
			throw new ValidationException(name + " can not be blank");
	}

	public static void isNotBlank(String str, String name)
			throws ValidationException {
		if (StringUtils.isBlank(str))
			throw new ValidationException(name + " can not be blank");
	}

	public static void isNotFutureDate(Date date, String name)
			throws ValidationException {
		if (date.after(new Date()))
			throw new ValidationException(name + " can not be a future date");
	}

	public static void dateOrderCorrect(Date b4Date, Date afterDate,
			String b4DateName, String afterDateName) throws ValidationException {
		if (afterDate.before(b4Date))
			throw new ValidationException(afterDateName + " ("
					+ MyDateUtil.dateFormat_dd_MMM_yy.format(afterDate)
					+ ") can not be before " + b4DateName + " ("
					+ MyDateUtil.dateFormat_dd_MMM_yy.format(afterDate) + ")");
	}

	public static void isNotLessThanZero(Integer value, String name)
			throws ValidationException {
		if (value < 0)
			throw new ValidationException(name + " can not be less than 0");
	}

	public static void isNotLessThanZero(Double value, String name)
			throws ValidationException {
		if (value < 0.0)
			throw new ValidationException(name + " can not be less than 0");
	}

	public static void isNotLessOrEqualToZero(Integer value, String name)
			throws ValidationException {
		if (value <= 0)
			throw new ValidationException(name
					+ " can not be less or equal to 0");
	}

	public static void isNotLessOrEqualToZero(Double value, String name)
			throws ValidationException {
		if (value <= 0.0)
			throw new ValidationException(name
					+ " can not be less or equal to 0");
	}

}
