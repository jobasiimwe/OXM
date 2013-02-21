package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.server.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userPropertyEditor")
public class UserPropertyEditor extends BasePropertyEditor {
    @Autowired
    private UserService userService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(userService.getUserById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }
}
