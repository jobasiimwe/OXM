package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.Product;
import org.agric.oxm.server.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("productPropertyEditor")
public class ProductPropertyEditor extends BasePropertyEditor {
	@Autowired
	private ProductService productService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof Product) {
			return ((Product) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(productService.getById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}
