package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("conceptPropertyEditor")
public class ConceptPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private ConceptService conceptService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(conceptService.getConceptById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/

}
