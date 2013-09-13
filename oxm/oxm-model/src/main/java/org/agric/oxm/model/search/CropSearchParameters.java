package org.agric.oxm.model.search;

import org.agric.oxm.model.Concept;

public class CropSearchParameters {

	private String name;
	private Concept input;
	private Concept seedVariation;
	private Concept ploughingMethod;
	private Concept interCropingType;

	public CropSearchParameters() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Concept getInput() {
		return input;
	}

	public void setInput(Concept input) {
		this.input = input;
	}

	public Concept getSeedVariation() {
		return seedVariation;
	}

	public void setSeedVariation(Concept seedVariation) {
		this.seedVariation = seedVariation;
	}

	public Concept getPloughingMethod() {
		return ploughingMethod;
	}

	public void setPloughingMethod(Concept ploughingMethod) {
		this.ploughingMethod = ploughingMethod;
	}

	public Concept getInterCropingType() {
		return interCropingType;
	}

	public void setInterCropingType(Concept interCropingType) {
		this.interCropingType = interCropingType;
	}

}
