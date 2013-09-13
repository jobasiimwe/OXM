package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.server.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * property editor that supports permission data binding in controllers
 * 
 * 
 */
@Component("permissionPropertyEditor")
public class PermissionPropertyEditor extends BasePropertyEditor {

    @Autowired
    private RoleService roleService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(roleService.getPermissionById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }
}
