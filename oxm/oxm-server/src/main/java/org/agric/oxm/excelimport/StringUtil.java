package org.agric.oxm.excelimport;

import java.util.ArrayList;
import java.util.List;

import org.agric.oxm.excelimport.model.Value;


/**
 * Contains <b>Utility methods</b> for <b>string</b> manipulation
 * 
 * @author Job
 * 
 */
public class StringUtil {

	/**
	 * takes a String "long string" and a separator, splits the string and
	 * returns the parts in a string List
	 * 
	 * @param longString
	 * @param separator
	 */
	public static List<String> convertStringToList(String longString,
			String separator) {
		String[] stringArray;
		if (separator == null)
			separator = ",";
		if (separator == "")
			separator = ",";
		stringArray = longString.split(separator);

		List<String> list = new ArrayList<String>();
		for (int i = 0; i < stringArray.length; i++)
			list.add(i, stringArray[i]);

		return list;
	}

	public static List<Value> convertStringToValues(Integer columnIndex,
			String longString, String separator) {
		String[] stringArray;
		if (separator == null)
			separator = ",";
		if (separator == "")
			separator = ",";
		stringArray = longString.split(separator);

		List<Value> list = new ArrayList<Value>();
		for (int i = 0; i < stringArray.length; i++)
			list.add(i, new Value(columnIndex, stringArray[i]));

		return list;
	}

	public static String convertListToString(List<String> stringList,
			String separator) {
		String longString = "";
		for (String string : stringList) {
			if (longString == "")
				longString = string;
			else
				longString += separator + string;
		}
		return longString;
	}
}
