package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "crop")
public class Crop extends BaseData implements Comparable<Crop> {

	private String name;

	private List<Concept> inputs;
	private List<Concept> seedVarieties;
	private List<Concept> ploughingMethods;
	private List<Concept> interCroppingTypes;
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

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_crop_seedvirieties", columnNames = {
			"crop_id", "concept_id" }), name = "crop_seed_varieties", joinColumns = @JoinColumn(name = "crop_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getSeedVarieties() {
		return seedVarieties;
	}

	public void setSeedVarieties(List<Concept> seedVarieties) {
		this.seedVarieties = seedVarieties;
	}

	public void addSeedVariety(Concept unit) {
		if (unit == null) {
			return;
		}

		if (getSeedVarieties() == null) {
			setSeedVarieties(new ArrayList<Concept>());
		}
		getSeedVarieties().add(unit);
	}

	public void removeSeedVariety(Concept seedVariety) {
		if (seedVariety == null || getSeedVarieties() == null) {
			return;
		}
		getSeedVarieties().remove(seedVariety);
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_crop_ploughing_methods", columnNames = {
			"crop_id", "concept_id" }), name = "crop_ploughing_methods", joinColumns = @JoinColumn(name = "crop_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getPloughingMethods() {
		return ploughingMethods;
	}

	public void setPloughingMethods(List<Concept> ploughingMethods) {
		this.ploughingMethods = ploughingMethods;
	}

	public void addPloghingMethod(Concept unit) {
		if (unit == null) {
			return;
		}

		if (getPloughingMethods() == null) {
			setPloughingMethods(new ArrayList<Concept>());
		}
		getPloughingMethods().add(unit);
	}

	public void removePloghingMethod(Concept ploughingMethod) {
		if (ploughingMethod == null || getPloughingMethods() == null) {
			return;
		}
		getPloughingMethods().remove(ploughingMethod);
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_inter_cropping_methods", columnNames = {
			"crop_id", "concept_id" }), name = "crop_inter_cropping_types", joinColumns = @JoinColumn(name = "crop_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getInterCroppingTypes() {
		return interCroppingTypes;
	}

	public void setInterCroppingTypes(List<Concept> interCroppingTypes) {
		this.interCroppingTypes = interCroppingTypes;
	}

	public void addInterCroppingType(Concept interCroppingType) {
		if (interCroppingType == null) {
			return;
		}

		if (getInterCroppingTypes() == null) {
			setInterCroppingTypes(new ArrayList<Concept>());
		}

		getInterCroppingTypes().add(interCroppingType);
	}

	public void removeInterCroppingType(Concept unit) {
		if (unit == null || getInterCroppingTypes() == null) {
			return;
		}

		getInterCroppingTypes().remove(unit);
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_crop_inputs", columnNames = {
			"crop_id", "concept_id" }), name = "crop_inputs", joinColumns = @JoinColumn(name = "crop_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getInputs() {
		return inputs;
	}

	public void setInputs(List<Concept> inputs) {
		this.inputs = inputs;
	}

	public void addInput(Concept unit) {
		if (unit == null) {
			return;
		}

		if (getInputs() == null) {
			setInputs(new ArrayList<Concept>());
		}
		getInputs().add(unit);
	}

	public void removeInput(Concept input) {
		if (input == null || getInputs() == null) {
			return;
		}
		getInputs().remove(input);
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_crop_sunitsofmeasure", columnNames = {
			"crop_id", "concept_id" }), name = "crop_units_of_measure", joinColumns = @JoinColumn(name = "crop_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	@Transient
	public String getUnitsOfMeasureNames() {
		String units = "";
		if (unitsOfMeasure == null || unitsOfMeasure.size() == 0) {
			units = "none";
		} else {
			if (unitsOfMeasure.size() == 1)
				units = unitsOfMeasure.get(0).getName();
			else if (unitsOfMeasure.size() == 2)
				units = unitsOfMeasure.get(0).getName() + ", "
						+ unitsOfMeasure.get(1).getName();
			else if (unitsOfMeasure.size() > 2)
				units = unitsOfMeasure.get(0).getName() + ", "
						+ unitsOfMeasure.get(1).getName() + "...";
		}
		return units;
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

	public void removeUnitOfMeasure(Concept unitOfMeasure) {
		if (unitOfMeasure == null || getUnitsOfMeasure() == null) {
			return;
		}

		getUnitsOfMeasure().remove(unitOfMeasure);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Crop other = (Crop) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Crop o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
