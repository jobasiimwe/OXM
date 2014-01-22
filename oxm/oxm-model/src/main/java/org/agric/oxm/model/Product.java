package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "product")
public class Product extends BaseData implements Comparable<Product> {

	private String name, description;
	private Crop crop;
	private List<Concept> unitsOfMeasure;

	public Product() {

	}

	public Product(String id) {
		this.setId(id);
	}

	public Product(String name, List<Concept> unitsOfMeasure) {
		this.setName(name);
		this.setDescription(name + " - auto generated");
		this.setUnitsOfMeasure(unitsOfMeasure);
	}

	public void generateDefaultProduct(Crop crop) {
		this.setCrop(crop);
		this.setName(crop.getName());
		this.setDescription(crop.getName() + " (default product)");
		this.setUnitsOfMeasure(new ArrayList<Concept>());

		for (Concept c : crop.getUnitsOfMeasure())
			this.getUnitsOfMeasure().add(c);
	}

	@Column(name = "name", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", nullable = true)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "crop_id", nullable = true)
	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_product_unitsofmeasure", columnNames = {
			"product_id", "concept_id" }), name = "product_units_of_measure", joinColumns = @JoinColumn(name = "product_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
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

	// ================================================================================

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
		Product other = (Product) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Product o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
