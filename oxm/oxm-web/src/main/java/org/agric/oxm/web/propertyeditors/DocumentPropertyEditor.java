package org.agric.oxm.web.propertyeditors;

import org.springframework.stereotype.Component;

@Component("documentPropertyEditor")
public class DocumentPropertyEditor extends BasePropertyEditor {

    /*@Autowired
    private DocumentService documentService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(documentService.getDocumentById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }*/
}
