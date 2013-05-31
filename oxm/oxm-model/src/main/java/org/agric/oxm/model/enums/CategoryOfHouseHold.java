package org.agric.oxm.model.enums;

public enum CategoryOfHouseHold {

	MHH("Male House Hold"), // ordinal 0

	FHH("Female House Hold"), // ordinal 1

	BLANK("Blank");// ordinal 2

	CategoryOfHouseHold(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return this.name;
	}
};
