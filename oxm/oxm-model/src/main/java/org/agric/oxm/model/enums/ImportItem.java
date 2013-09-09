package org.agric.oxm.model.enums;

public enum ImportItem {
	ALL("All"),DISTRICT_DETAILS("District Details"), PRODUCER_ORGz("Producer Orgs"), PRODUCERS(
			"Producers");

	ImportItem(String name) {
		this.name = name;
	}

	private String name;

	public String getName() {
		return name;
	}
};
