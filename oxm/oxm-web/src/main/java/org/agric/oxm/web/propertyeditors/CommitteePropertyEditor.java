package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Committee;
import org.agric.oxm.server.service.CommitteeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("committeePropertyEditor")
public class CommitteePropertyEditor extends BasePropertyEditor {

	@Autowired
	private CommitteeService committeeService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Committee) {
			return ((Committee) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(committeeService.getCommitteeById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}
