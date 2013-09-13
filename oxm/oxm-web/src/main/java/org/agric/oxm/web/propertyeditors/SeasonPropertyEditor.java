package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Season;
import org.agric.oxm.server.service.SeasonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("seasonPropertyEditor")
public class SeasonPropertyEditor extends BasePropertyEditor {

	@Autowired
	private SeasonService seasonService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Season) {
			return ((Season) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(seasonService.getById(text));
			}
		} else {
			super.setAsText(text);
		}
	}

}
