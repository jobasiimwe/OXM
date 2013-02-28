package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.server.service.SellingPlaceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sellingPlacePropertyEditor")
public class SellingPlacePropertyEditor extends BasePropertyEditor{
    @Autowired
    private SellingPlaceService sellingPlaceService;
    
    public void setAsText(String text) throws IllegalArgumentException {
	if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
	    if (StringUtils.equalsIgnoreCase("none", text)) {
		super.setValue(null);
	    } else {
		super.setValue(sellingPlaceService.getSellingPlaceById(text));
	    }
	} else {
	    super.setAsText(text);
	}
    }
}
