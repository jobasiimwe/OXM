package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("permissionPropertyEditor")
public class PermissionPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private UserService userService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(userService.getPermissionById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/
}
