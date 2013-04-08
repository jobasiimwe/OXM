package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Position;
import org.agric.oxm.server.service.PositionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("positionPropertyEditor")
public class PositionPropertyEditor extends BasePropertyEditor {
	@Autowired
	private PositionService positionService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Position) {
			return ((Position) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(positionService.getPositionById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}
