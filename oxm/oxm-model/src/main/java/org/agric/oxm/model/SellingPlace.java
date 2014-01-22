package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "selling_place")
public class SellingPlace extends BaseData implements Comparable<SellingPlace> {

	private String name;

	private District district;

	// private List<Concept> sellingTypes;

	public SellingPlace() {
		super();
	}

	public SellingPlace(String name) {
		this.setName(name);
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

	// @ManyToMany
	// @JoinTable(uniqueConstraints = @UniqueConstraint(name =
	// "uk_place_selling_types", columnNames = {
	// "selling_place_id", "concept_id" }), name = "place_selling_types",
	// joinColumns = @JoinColumn(name = "selling_place_id", unique = false),
	// inverseJoinColumns = @JoinColumn(name = "concept_id", unique = false))
	// public List<Concept> getSellingTypes() {
	// return sellingTypes;
	// }
	//
	// public void setSellingTypes(List<Concept> types) {
	// this.sellingTypes = types;
	// }

	// @Transient
	// public String getSellingTypesString() {
	// String sellingTypesString = "";
	// for (Concept concept : getSellingTypes()) {
	// if (StringUtils.isEmpty(sellingTypesString))
	// sellingTypesString = concept.getName();
	// else
	// sellingTypesString += ", " + concept.getName();
	// }
	//
	// if (StringUtils.isBlank(sellingTypesString))
	// sellingTypesString = "--";
	// return sellingTypesString;
	// }

	@Override
	public int compareTo(SellingPlace o) {
		return this.getName().compareTo(o.getName());
	}

}
