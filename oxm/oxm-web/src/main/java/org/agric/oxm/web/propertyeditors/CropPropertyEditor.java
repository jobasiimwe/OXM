package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Crop;
import org.agric.oxm.server.service.CropService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cropPropertyEditor")
public class CropPropertyEditor extends BasePropertyEditor {
	@Autowired
	private CropService cropService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Crop) {
			return ((Crop) super.getValue()).getId();
		}

		return super.getAsText();
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(cropService.getCropById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}
