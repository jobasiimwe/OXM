package org.agric.oxm.web.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.agric.oxm.model.BaseData;

public class BasePropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
	if (super.getValue() != null && super.getValue() instanceof BaseData) {
	    return ((BaseData) super.getValue()).getId();
	}

	return super.getAsText();
    }

}
