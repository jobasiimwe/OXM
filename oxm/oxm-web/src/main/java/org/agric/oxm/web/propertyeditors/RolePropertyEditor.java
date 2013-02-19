package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("rolePropertyEditor")
public class RolePropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private UserService userService;
    
    
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(userService.getRoleById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/
}
