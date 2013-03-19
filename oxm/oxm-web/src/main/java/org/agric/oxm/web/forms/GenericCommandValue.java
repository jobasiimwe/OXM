package org.agric.oxm.web.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericCommandValue {

	public static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");

	private String value;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public GenericCommandValue() {
		super();
	}

	public GenericCommandValue(String value) {
		super();
		this.value = value;
	}

	/**
	 * constructor with initial specified values
	 * 
	 * @param stringValue
	 */
	public GenericCommandValue(Boolean booleanValue) {
		super();
		this.value = booleanValue.toString();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Boolean getBooleanValue() {
		return Boolean.valueOf(this.getValue());
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.value = booleanValue.toString();
	}

	public Date getDateValue() {
		try {
			if (StringUtils.isNotBlank(this.getValue()))
				return dateFormat.parse(this.getValue());
			else
				return null;
		} catch (ParseException e) {
			log.error("Date Error", e);
			return null;
		}
	}
}
