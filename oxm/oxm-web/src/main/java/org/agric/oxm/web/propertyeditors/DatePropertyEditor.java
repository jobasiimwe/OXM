package org.agric.oxm.web.propertyeditors;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * handles web data binding for date types
 * 
 * 
 */
@Component("datePropertyEditor")
public class DatePropertyEditor extends BasePropertyEditor {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(
	    "dd/MM/yyyy");

    /**
     * default constructor
     */
    public DatePropertyEditor() {
    }

    /**
     * constructor with a default date format
     * 
     * @param dateFormat
     */
    public DatePropertyEditor(SimpleDateFormat dateFormat) {
	this.dateFormat = dateFormat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    try {
		super.setValue(dateFormat.parse(text));
	    } catch (ParseException e) {
		throw new IllegalArgumentException(
			"date cannot be parsed: make sure the date is in the specified format");
	    }
	} else {
	    super.setValue(null);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
	if (super.getValue() != null) {
	    return dateFormat.format(super.getValue());
	}

	return ""; // return an empty string because the object is null
    }

}
