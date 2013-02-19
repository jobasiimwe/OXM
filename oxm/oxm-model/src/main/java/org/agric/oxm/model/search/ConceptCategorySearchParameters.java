package org.agric.oxm.model.search;

public class ConceptCategorySearchParameters {

	private String name;

	public ConceptCategorySearchParameters() {
	}

	public ConceptCategorySearchParameters(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
