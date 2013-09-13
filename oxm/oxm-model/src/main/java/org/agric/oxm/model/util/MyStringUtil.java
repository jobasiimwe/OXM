package org.agric.oxm.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Contains <b>Utility methods</b> for <b>string</b> manipulation
 * 
 * 
 */
public class MyStringUtil {

	/**
	 * Takes a string s and capitalizes first letter of each word <br>
	 * would be similar to <b>capitalizeFirstLettersTokenizer()</b> which uses
	 * tokens but GWT doesn't implement it
	 * 
	 * @param s
	 * @return string with 1st letter of each word capitalized
	 */
	public static String capitalizeFirstLetters(String s) {

		// return capitalizeFirstLettersTokenizer(s);
		s = s.toLowerCase();
		for (int i = 0; i < s.length(); i++) {

			if (i == 0) {
				// Capitalize the first letter of the string.
				// s = String.format("%s%s", Character.toUpperCase(s.charAt(0)),
				// s
				// .substring(1));
				s = Character.toUpperCase(s.charAt(0)) + s.substring(1);
			}

			// Is this character a non-letter or non-digit? If so
			// then this is probably a word boundary so let's capitalize
			// the next character in the sequence.
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					// s = String.format("%s%s%s", s.subSequence(0, i + 1),
					// Character.toUpperCase(s.charAt(i + 1)), s
					// .substring(i + 2));
					s = s.subSequence(0, i + 1).toString()
							+ Character.toUpperCase(s.charAt(i + 1))
							+ s.substring(i + 2);
				}
			}

		}

		return s;

	}

	/**
	 * Takes a string s and capitalizes first letter of each word <br>
	 * similar to <b>StringUtil.capitalizeFirstLetters()</b>
	 * 
	 * @param s
	 * @return string with 1st letter of each word capitalized
	 */
	public static String capitalizeFirstLettersTokenizer(String s) {

		final StringTokenizer st = new StringTokenizer(s.toLowerCase(), " ",
				true);
		final StringBuilder sb = new StringBuilder();

		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			token = String.format("%s%s",
					Character.toUpperCase(token.charAt(0)), token.substring(1));
			sb.append(token);
		}

		return sb.toString();

	}

	/**
	 * checks whether an email address is valid format
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailAddressIsvalid(String email) {
		// allow any text for now
		if (email.contains("@") && email.contains("."))
			return true;

		return false;
	}

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

	/**
	 * converts the given string to title case. escapes words like a, of, and
	 * 
	 * @param str
	 * @return empty string if the given parameter is null or empty
	 */
	public static String convertToTitleCase(String str) {
		if (str == null || str.trim().length() == 0)
			return "";

		/*
		 * words that will be ignored for first letter capitalization
		 */
		String[] escapeWords = new String[] { "a", "an", "the", "for", "and",
				"nor", "but", "or", "yet", "so", "to", "as", "by", "through",
				"in", "of", "I", "II", "III", "IV", "V", "VI", "VII", "VIII" };

		str = str.toLowerCase();
		String[] tokens = str.split(" ");
		StringBuilder builder = new StringBuilder();

		for (int index = 0; index < tokens.length; index++) {
			String token = tokens[index];
			boolean isEscapeWord = false;

			/*
			 * check if word is an escape word.
			 */
			for (String escapeWord : escapeWords) {
				if (token.equalsIgnoreCase(escapeWord)) {
					isEscapeWord = true;
					break;
				}
			}

			if (index > 0)
				builder.append(" ");

			if (!isEscapeWord) {
				token = String.format("%s%s",
						Character.toUpperCase(token.charAt(0)),
						token.substring(1));
				builder.append(token);
			} else {
				builder.append(token);
			}
		}

		return builder.toString();
	}

	// =====================================================

	public static String singleQuote(String str) {
		str = "'" + str + "'";
		return str;
	}

	public static String bracketed(String str) {
		str = " (" + str + ")";
		return str;
	}

	public static String percent(String str) {
		str = "%" + str + "%";
		return str;
	}

	// ====================================================
}
