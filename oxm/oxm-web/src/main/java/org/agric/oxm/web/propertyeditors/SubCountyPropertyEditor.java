package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("subCountyPropertyEditor")
public class SubCountyPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private AdminService adminService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(adminService.getSubCountyById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }
*/
}
