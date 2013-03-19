package org.agric.oxm.web.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.MapUtils;

public class GenericCommand {

	@SuppressWarnings("unchecked")
	private Map<String, GenericCommandValue> propertiesMap = MapUtils.lazyMap(
			new HashMap<String, GenericCommandValue>(),
			FactoryUtils.instantiateFactory(GenericCommandValue.class));

	public Map<String, GenericCommandValue> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, GenericCommandValue> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	public String getValue(String key) {
		if (this.propertiesMap != null) {
			GenericCommandValue commandValue = this.propertiesMap.get(key);
			if (commandValue != null)
				return commandValue.getValue();
		}

		return null;
	}

	public Boolean getBooleanValue(String key) {
		if (this.propertiesMap != null) {
			GenericCommandValue commandValue = this.propertiesMap.get(key);
			if (commandValue != null)
				return commandValue.getBooleanValue();
		}

		return null;
	}

	public Date getAsDate(String key) {
		if (this.propertiesMap != null) {
			GenericCommandValue commandValue = this.propertiesMap.get(key);
			if (commandValue != null)
				return commandValue.getDateValue();
		}

		return null;
	}
	
//	public String getDateText(String key) {
//		if (this.propertiesMap != null) {
//			GenericCommandValue commandValue = this.propertiesMap.get(key);
//			if (commandValue != null)
//				return commandValue.getDateText();
//		}
//
//		return "";
//	}
}
