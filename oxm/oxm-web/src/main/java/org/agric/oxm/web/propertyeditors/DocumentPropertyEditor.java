package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Document;
import org.agric.oxm.server.service.DocumentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("documentPropertyEditor")
public class DocumentPropertyEditor extends BasePropertyEditor {

	@Autowired
	private DocumentService documentService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Document) {
			return ((Document) super.getValue()).getId();
		}

		return super.getAsText();
	}
	
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
	}
}
