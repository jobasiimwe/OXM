package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.StaffMember;
import org.agric.oxm.server.service.ProducerOrgService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("staffMemberPropertyEditor")
public class StaffMemberpropertyEditor extends BasePropertyEditor {

	@Autowired
	private ProducerOrgService producerOrgService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof StaffMember) {
			return ((StaffMember) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(producerOrgService.getStaffMemberById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}
