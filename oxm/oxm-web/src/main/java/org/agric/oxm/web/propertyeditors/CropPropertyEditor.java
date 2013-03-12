package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.server.service.CropService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cropPropertyEditor")
public class CropPropertyEditor extends BasePropertyEditor {
	@Autowired
	private CropService cropService;

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
