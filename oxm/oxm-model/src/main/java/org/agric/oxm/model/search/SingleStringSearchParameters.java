package org.agric.oxm.model.search;

public class SingleStringSearchParameters {

	private String name;

	public SingleStringSearchParameters() {
	}

	public SingleStringSearchParameters(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
