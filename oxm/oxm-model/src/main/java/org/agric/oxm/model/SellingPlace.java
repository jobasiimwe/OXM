package org.agric.oxm.model;

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

@Entity
@Table(name = "selling_place")
public class SellingPlace extends BaseData implements Comparable<SellingPlace> {

	private String name;

	private District district;

	private List<Concept> sellingTypes;

	public SellingPlace() {
		super();
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "district_id", nullable = true)
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(name = "uk_place_selling_types", columnNames = {
			"selling_place_id", "concept_id" }), name = "place_selling_types", joinColumns = @JoinColumn(name = "selling_place_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	public List<Concept> getSellingTypes() {
		return sellingTypes;
	}

	public void setSellingTypes(List<Concept> types) {
		this.sellingTypes = types;
	}

	@Transient
	public String getSellingTypesString() {
		String sellingTypesString = "";
		for (Concept concept : getSellingTypes()) {
			if (StringUtils.isEmpty(sellingTypesString))
				sellingTypesString = concept.getName();
			else
				sellingTypesString += ", " + concept.getName();
		}

		return sellingTypesString;
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
		SellingPlace other = (SellingPlace) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(SellingPlace o) {
		return this.getName().compareTo(o.getName());
	}

}
