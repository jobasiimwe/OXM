package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Job
 * 
 */
@Entity
@Table(name = "crop")
public class Crop extends BaseData implements Comparable<Crop> {

	private String name;

	private List<Concept> inputs;
	private List<Concept> seedVarieties;
	private List<Concept> ploughingMethods;
	private List<Concept> interCroppingTypes;
	private List<Concept> unitsOfMeasure;

	private List<Product> products;

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

	@OneToMany(mappedBy = "crop", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<Product> getProducts() {
		return products;
	}

	@Transient
	public String getProductsNames() {
		String productNames = "";
		if (products == null || products.size() == 0) {
			productNames = "none";
		} else {
			if (products.size() == 1)
				productNames = products.get(0).getName();
			else if (products.size() == 2)
				productNames = products.get(0).getName() + ", "
						+ products.get(1).getName();
			else if (products.size() > 2)
				productNames = products.get(0).getName() + ", "
						+ products.get(1).getName() + "...";
		}
		return productNames;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void addProduct(Product unit) {
		if (unit == null) {
			return;
		}

		if (getProducts() == null) {
			setProducts(new ArrayList<Product>());
		}

		getProducts().add(unit);
	}

	public void removeProduct(Product product) {
		if (product == null || getProducts() == null) {
			return;
		}

		getProducts().remove(product);
	}

	// ================================================================================

	@Transient
	public String getInputNames() {
		String returnString = "";

		if (inputs != null) {
			if (inputs.size() > 0) {
				for (int i = 0; i < inputs.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += inputs.get(i).getName();
					else
						returnString += ", "
								+ inputs.get(i).getName();
				}
				if (inputs.size() > 3) {
					int x = inputs.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		return StringUtils.isBlank(returnString) ? "..." : returnString;
	}
	
	@Transient
	public String getSdvNames() {
		String returnString = "";

		if (seedVarieties != null) {
			if (seedVarieties.size() > 0) {
				for (int i = 0; i < seedVarieties.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += seedVarieties.get(i).getName();
					else
						returnString += ", "
								+ seedVarieties.get(i).getName();
				}
				if (seedVarieties.size() > 3) {
					int x = seedVarieties.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		return StringUtils.isBlank(returnString) ? "..." : returnString;
	}

	@Transient
	public String getPmNames() {
		String returnString = "";

		if (ploughingMethods != null) {
			if (ploughingMethods.size() > 0) {
				for (int i = 0; i < ploughingMethods.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += ploughingMethods.get(i).getName();
					else
						returnString += ", "
								+ ploughingMethods.get(i).getName();
				}
				if (ploughingMethods.size() > 3) {
					int x = ploughingMethods.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		return StringUtils.isBlank(returnString) ? "..." : returnString;
	}

	@Transient
	public String getIctNames() {
		String returnString = "";

		if (interCroppingTypes != null) {
			if (interCroppingTypes.size() > 0) {
				for (int i = 0; i < interCroppingTypes.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += interCroppingTypes.get(i).getName();
					else
						returnString += ", "
								+ interCroppingTypes.get(i).getName();
				}
				if (interCroppingTypes.size() > 3) {
					int x = interCroppingTypes.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		return StringUtils.isBlank(returnString) ? "..." : returnString;
	}

	@Transient
	public String getUomNames() {

		String returnString = "";

		if (unitsOfMeasure != null) {
			if (unitsOfMeasure.size() > 0) {
				for (int i = 0; i < unitsOfMeasure.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += unitsOfMeasure.get(i).getName();
					else
						returnString += ", " + unitsOfMeasure.get(i).getName();
				}
				if (unitsOfMeasure.size() > 3) {
					int x = unitsOfMeasure.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		return StringUtils.isBlank(returnString) ? "..." : returnString;
	}

	// ================================================================================

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
