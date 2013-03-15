package org.agric.oxm.model;

public enum Gender {
    MALE("Male"),

    FEMALE("Female");

    Gender(String name) {
	this.name = name;
    }

    private String name;

    public String getName() {
	return this.name;
    }
};
