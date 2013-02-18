package org.agric.oxm.model;

public enum UserStatus {

    DISABLED("Disabled"), ENABLED("Enabled");

    UserStatus(String name) {
	this.name = name;
    }

    private String name;

    public String getName() {
	return name;
    }
}
