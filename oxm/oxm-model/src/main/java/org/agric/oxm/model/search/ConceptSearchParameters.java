package org.agric.oxm.model.search;

import org.agric.oxm.model.ConceptCategory;

/**
 * 
 * @author Job
 * 
 */
public class ConceptSearchParameters {

	private String name;
	private ConceptCategory conceptCategory;

	public ConceptSearchParameters() {
	}

	public ConceptSearchParameters(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConceptCategory getConceptCategory() {
		return conceptCategory;
	}

	public void setConceptCategory(ConceptCategory conceptCategory) {
		this.conceptCategory = conceptCategory;
	}

}
