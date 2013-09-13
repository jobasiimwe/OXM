package org.agric.oxm.model.enums;

/**
 * Conditions greater that, less than e.t.c
 * 
 * @author Job
 * 
 */
public enum Condition {

	/**
	 * greater than or equal to - ordinal 0
	 */
	GREATER_OR_EQUAL_TO(">="),

	/**
	 * Less than or equal to - ordinal 1
	 */
	LESS_OR_EQUAL_TO("<="),

	/**
	 * greater than - ordinal 2
	 */
	GREATER_THAN(">"),

	/**
	 * Less than - ordinal 3
	 */
	LESS_THAN("<"),

	/**
	 * Equal 4
	 */
	EQUAL_TO("="),

	NOT_EQUAL_TO("!=");

	/**
	 * constructor with initial specified value
	 * 
	 * @param name
	 */
	Condition(String name) {
		this.name = name;
	}

	private String name;

	/**
	 * gets the title of the enumerated value
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

};
