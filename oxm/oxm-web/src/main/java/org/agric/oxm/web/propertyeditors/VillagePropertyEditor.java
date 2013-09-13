package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Village;
import org.agric.oxm.server.service.Adminservice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("villagePropertyEditor")
public class VillagePropertyEditor extends BasePropertyEditor {

	@Autowired
	private Adminservice setupService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Village) {
			return ((Village) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(setupService.getVillageById(text));
			}
		} else {
			super.setAsText(text);
		}
	}

}
