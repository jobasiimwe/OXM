package org.agric.oxm.model.enums;

public enum MaritalStatus {

	MARRIED("Married"), // ordinal 0

	SINGLE("Single"), // ordinal 1

	WIDOW("Widow"), // ordinal 2

	WIDOWER("Widower"), // ordinal 3

	BLANK("Blank");// ordinal 4

	MaritalStatus(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return this.name;
	}
};
