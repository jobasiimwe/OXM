package org.agric.oxm.web.forms;

import java.text.ParseException;
import java.util.Date;

import org.agric.oxm.model.util.MyDateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * presents a generic command property value
 * 
 */
public class GenericCommandValue {

	// public static SimpleDateFormat dateFormat = new SimpleDateFormat(
	// "dd/MM/yyyy");

	private String value;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * default constructor
	 */
	public GenericCommandValue() {
		super();
	}

	public GenericCommandValue(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	// ==========================================================================================
	// ==========================================================================================
	// ==========================================================================================

	public Boolean getBooleanValue() {
		return Boolean.valueOf(this.getValue());
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.value = booleanValue.toString();
	}

	// ==========================================================================================
	// ==========================================================================================
	// ==========================================================================================

	public Double getDoubleValue() {
		try {
			return Double.parseDouble(this.getValue());
		} catch (Exception e) {
			return null;
		}
	}

	// ==========================================================================================
	// ==========================================================================================
	// ==========================================================================================

	public Date getDateValue() {
		try {
			if (StringUtils.isNotBlank(this.getValue()))
				return MyDateUtil.dateFormat_stroke_dd_MM_yyyy.parse(this
						.getValue());
			else
				return null;
		} catch (ParseException e) {
			log.error("Date Error", e);
			return null;
		}
	}

}
