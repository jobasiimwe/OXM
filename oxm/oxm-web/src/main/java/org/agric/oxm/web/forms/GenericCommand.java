package org.agric.oxm.web.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.agric.oxm.model.BaseData;
import org.agric.oxm.model.enums.Condition;
import org.agric.oxm.model.util.MyDateUtil;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * represents a command object that can be used in a spring form to hold
 * properties in a map. Internally, the settings in a map and are represented by
 * another class {@link GenericCommandValue}
 * 
 */
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

	/**
	 * gets the value of the given key from this command object
	 * 
	 * @param key
	 * @return
	 */
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

	public Double getDoubleValue(String key) {
		if (this.propertiesMap != null) {
			GenericCommandValue commandValue = this.propertiesMap.get(key);
			if (commandValue != null)
				return commandValue.getDoubleValue();
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

	// =======================================================================
	// =======================================================================
	// =======================================================================

	public void put(String key, String value) {
		this.getPropertiesMap().put(key, new GenericCommandValue(value));
	}

	/**
	 * puts a given value into the command object with the given key
	 * 
	 * @param key
	 * @param value
	 */
	public void checkAndPut(String key, String value) {
		if (StringUtils.isNotBlank(value))
			this.getPropertiesMap().put(key, new GenericCommandValue(value));
	}

	public void checkAndPut(String key, Double value) {
		if (value != null)
			this.getPropertiesMap().put(key,
					new GenericCommandValue(value.toString()));
	}
	
	
	public void checkAndPut(String key, Boolean value) {
		if (value != null)
			this.getPropertiesMap().put(key,
					new GenericCommandValue(value.toString()));
	}

	public void checkAndPut(String key, BaseData value) {
		if (value != null)
			this.getPropertiesMap().put(key,
					new GenericCommandValue(value.getId()));
	}

	public void checkAndPut(String key, Condition c) {
		if (c != null)
			this.getPropertiesMap().put(key,
					new GenericCommandValue(c.toString()));
	}

	public void checkAndPut(String key, Date value) {
		if (value != null)
			this.getPropertiesMap().put(
					key,
					new GenericCommandValue(
							MyDateUtil.dateFormat_stroke_dd_MM_yyyy
									.format(value)));
	}

	// =======================================================================

	/**
	 * puts a given value into the command object with the given key
	 * 
	 * @param key
	 * @param value
	 */
	public static void checkAndAppend(String key, String value,
			StringBuffer buffer) {
		if (StringUtils.isNotBlank(value))
			buffer.append("&").append(key).append("=").append(value);
	}

	public static void checkAndAppend(String key, Boolean value,
			StringBuffer buffer) {
		if (value != null)
			buffer.append("&").append(key).append("=").append(value.toString());
	}
	
	public static void checkAndAppend(String key, Double value,
			StringBuffer buffer) {
		if (value != null)
			buffer.append("&").append(key).append("=").append(value.toString());
	}

	public static void checkAndAppend(String key, BaseData value,
			StringBuffer buffer) {
		if (value != null)
			buffer.append("&").append(key).append("=").append(value.getId());
	}

	public static void checkAndAppend(String key, Condition c,
			StringBuffer buffer) {
		if (c != null)
			buffer.append("&").append(key).append("=").append(c.toString());
	}

	public static void checkAndAppend(String key, Date value,
			StringBuffer buffer) {
		if (value != null)
			buffer.append("&")
					.append(key)
					.append("=")
					.append(MyDateUtil.dateFormat_stroke_dd_MM_yyyy
							.format(value));
	}

	// =======================================================================

		public Boolean isNotBlank(String key) {
			if (StringUtils.isNotBlank(this.getValue(key)))
				return true;

			return false;
		}
}
