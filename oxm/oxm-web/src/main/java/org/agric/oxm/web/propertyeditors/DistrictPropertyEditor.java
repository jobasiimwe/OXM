package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("districtPropertyEditor")
public class DistrictPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private SetupService setupService;

    
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#getAsText()
     
    @Override
    public String getAsText() {
	if (super.getValue() != null && super.getValue() instanceof District) {
	    return ((District) super.getValue()).getId();
	}

	return super.getAsText();
    }

    
     * (non-Javadoc)
     * 
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(setupService.getDistrictById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/
}
