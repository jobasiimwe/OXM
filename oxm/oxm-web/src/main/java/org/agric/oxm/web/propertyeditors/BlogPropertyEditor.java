package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Blog;
import org.agric.oxm.server.service.BlogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("blogPropertyEditor")
public class BlogPropertyEditor extends BasePropertyEditor {

	@Autowired
	private BlogService blogService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Blog) {
			return ((Blog) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(blogService.getById(text));
			}
		} else {
			super.setAsText(text);
		}
	}

}
