package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.BlogComment;
import org.agric.oxm.server.service.BlogCommentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blogCommentPropertyEditor")
public class BlogCommentPropertyEditor extends BasePropertyEditor {

	@Autowired
	private BlogCommentService blogCommentService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof BlogComment) {
			return ((BlogComment) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(blogCommentService.getById(text));
			}
		} else {
			super.setAsText(text);
		}
	}

}
