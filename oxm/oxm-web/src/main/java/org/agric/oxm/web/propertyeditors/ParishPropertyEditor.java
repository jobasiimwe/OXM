package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("parishPropertyEditor")
public class ParishPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private AdminService adminService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(adminService.getParishById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/

}
