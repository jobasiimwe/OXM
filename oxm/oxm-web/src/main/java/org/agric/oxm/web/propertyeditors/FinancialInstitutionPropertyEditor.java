package org.agric.oxm.web.propertyeditors;

import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.server.service.FinancialInstitutionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("financialInstitutionPropertyEditor")
public class FinancialInstitutionPropertyEditor extends BasePropertyEditor {

	@Autowired
	private FinancialInstitutionService financialInstitutionService;

	@Override
	public String getAsText() {
		if (super.getValue() != null && super.getValue() instanceof FinancialInstitution) {
			return ((FinancialInstitution) super.getValue()).getId();
		}

		return super.getAsText();
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isNotBlank(text) && StringUtils.isNotEmpty(text)) {
			if (StringUtils.equalsIgnoreCase("none", text)) {
				super.setValue(null);
			} else {
				super.setValue(financialInstitutionService.getFinancialInstitutionById(text));
			}
		} else {
			super.setAsText(text);
		}
	}
}