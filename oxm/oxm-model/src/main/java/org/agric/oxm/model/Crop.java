package org.agric.oxm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "crop")
public class Crop extends BaseData {

	private String name;

	private ConceptCategory input;

	private ConceptCategory seedVariation;

	private ConceptCategory ploughingMethod;

	private ConceptCategory interCropingType;

	private Concept unitOfMeasure;

	public Crop() {
		super();
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "input_concept_category_id", nullable = false)
	public ConceptCategory getInput() {
		return input;
	}

	public void setInput(ConceptCategory input) {
		this.input = input;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "seed_variation_concept_category_id", nullable = false)
	public ConceptCategory getSeedVariation() {
		return seedVariation;
	}

	public void setSeedVariation(ConceptCategory seedVariation) {
		this.seedVariation = seedVariation;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "plouging_method_concept_category_id", nullable = false)
	public ConceptCategory getPloughingMethod() {
		return ploughingMethod;
	}

	public void setPloughingMethod(ConceptCategory ploughingMethod) {
		this.ploughingMethod = ploughingMethod;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "inter_cropping_type_concept_category_id", nullable = false)
	public ConceptCategory getInterCropingType() {
		return interCropingType;
	}

	public void setInterCropingType(ConceptCategory interCropingType) {
		this.interCropingType = interCropingType;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "unit_of_measure_id", nullable = false)
	public Concept getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(Concept unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

}
