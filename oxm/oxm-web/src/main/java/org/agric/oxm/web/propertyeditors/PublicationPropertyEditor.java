package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("publicationPropertyEditor")
public class PublicationPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private PublicationService publicationService;

    @Override
    public String getAsText() {
	if (super.getValue() != null && super.getValue() instanceof Publication) {
	    return ((Publication) super.getValue()).getId();
	}

	return super.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(publicationService.getPublicationById(text));
	    }
	} else {
	    super.setAsText(text);
	}

    }*/
}
