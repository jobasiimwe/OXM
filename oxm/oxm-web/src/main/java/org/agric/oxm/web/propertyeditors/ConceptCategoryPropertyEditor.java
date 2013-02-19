package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("conceptCategoryPropertyEditor")
public class ConceptCategoryPropertyEditor extends BasePropertyEditor {
    /*@Autowired
    private ConceptService conceptService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(conceptService.getConceptCategoryById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/
}
