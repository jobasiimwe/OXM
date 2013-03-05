package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parish")
public class Parish extends BaseData implements Comparable<Parish> {

	private String name;

	private SubCounty subCounty;

	private List<Village> villages;

	public Parish() {
		super();
	}

	public Parish(String name, SubCounty subCounty) {
		super();
		this.setName(name);
		this.setSubCounty(subCounty);
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "sub_county_id", nullable = false)
	public SubCounty getSubCounty() {
		return subCounty;
	}

	public void setSubCounty(SubCounty subCounty) {
		this.subCounty = subCounty;
	}

	@OneToMany(mappedBy = "parish", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<Village> getVillages() {
		return villages;
	}

	public void setVillages(List<Village> villages) {
		this.villages = villages;
	}

	public void addVillage(Village village) {
		if (village == null) {
			return;
		}

		if (this.getVillages() == null) {
			this.setVillages(new ArrayList<Village>());
		}

		this.getVillages().add(village);
		village.setParish(this);
	}

	public void removeVillage(Village village) {
		if (village == null || this.getVillages() == null) {
			return;
		}

		this.getVillages().remove(village);
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
		Parish other = (Parish) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Parish o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
