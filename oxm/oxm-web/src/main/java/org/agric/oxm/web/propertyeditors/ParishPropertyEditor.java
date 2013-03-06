package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Parish;
import org.agric.oxm.server.service.Adminservice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("parishPropertyEditor")
public class ParishPropertyEditor extends BasePropertyEditor {

	@Autowired
	private Adminservice setupService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Parish) {
			return ((Parish) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(setupService.getParishById(text));
			}
		} else {
			super.setAsText(text);
		}
	}

}
