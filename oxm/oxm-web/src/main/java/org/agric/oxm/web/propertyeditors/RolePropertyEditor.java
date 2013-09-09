package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.server.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("rolePropertyEditor")
public class RolePropertyEditor extends BasePropertyEditor {

	@Autowired
	private RoleService roleService;

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(roleService.getRoleById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}
