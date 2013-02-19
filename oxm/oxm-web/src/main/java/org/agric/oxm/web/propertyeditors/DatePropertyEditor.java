package org.agric.oxm.web.propertyeditors;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component("datePropertyEditor")
public class DatePropertyEditor extends BasePropertyEditor {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(
	    "dd/MM/yyyy");

    public DatePropertyEditor() {
    }

    public DatePropertyEditor(SimpleDateFormat dateFormat) {
	this.dateFormat = dateFormat;
    }

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

    @Override
    public String getAsText() {
	if (super.getValue() != null) {
	    return dateFormat.format(super.getValue());
	}

	return ""; 
    }

}
