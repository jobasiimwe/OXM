package org.agric.oxm.model.enums;

public enum HouseHoldCategory {

	MHH("Male House Hold"), // ordinal 0

	FHH("Female House Hold"); // ordinal 1

	HouseHoldCategory(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return this.name;
	}
};
