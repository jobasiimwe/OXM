package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "crop")
public class Crop extends BaseData {

	private String name;

	private ConceptCategory input;

	private ConceptCategory seedVariation;

	private ConceptCategory ploughingMethod;

	private ConceptCategory interCropingType;

	private List<Concept> unitsOfMeasure;

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
	@JoinColumn(name = "input_concept_category_id", nullable = true)
	public ConceptCategory getInput() {
		return input;
	}

	public void setInput(ConceptCategory input) {
		this.input = input;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "seed_variation_concept_category_id", nullable = true)
	public ConceptCategory getSeedVariation() {
		return seedVariation;
	}

	public void setSeedVariation(ConceptCategory seedVariation) {
		this.seedVariation = seedVariation;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "plouging_method_concept_category_id", nullable = true)
	public ConceptCategory getPloughingMethod() {
		return ploughingMethod;
	}

	public void setPloughingMethod(ConceptCategory ploughingMethod) {
		this.ploughingMethod = ploughingMethod;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "inter_cropping_type_concept_category_id", nullable = true)
	public ConceptCategory getInterCropingType() {
		return interCropingType;
	}

	public void setInterCropingType(ConceptCategory interCropingType) {
		this.interCropingType = interCropingType;
	}

	// @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	// @JoinColumn(name = "unit_of_measure_id", nullable = true)
	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = { "crop_id",
			"concept_id" }), name = "crop_units_of_measure", joinColumns = @JoinColumn(name = "crop_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	public void setUnitsOfMeasure(List<Concept> unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	public void addUnitOfMeasure(Concept unit) {
		if (unit == null) {
			return;
		}

		if (getUnitsOfMeasure() == null) {
			setUnitsOfMeasure(new ArrayList<Concept>());
		}

		getUnitsOfMeasure().add(unit);
	}

	public void removeUnitOfMeasure(Concept unit) {
		if (unit == null || getUnitsOfMeasure() == null) {
			return;
		}

		getUnitsOfMeasure().remove(unit);
	}

}
