package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "village")
public class Village extends BaseData implements Comparable<Village> {

	private String name;

	private Parish parish;

	public Village() {
		super();
	}

	public Village(String id) {
		this.setId(id);
	}

	public Village(String name, Parish parish) {
		super();
		this.setName(name);
		this.setParish(parish);
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getFullName() {
		String fullName = this.getName();

		fullName += ", " + this.getParish().getName();
		fullName += ", " + this.getParish().getSubCounty().getName();
		fullName += ", "
				+ this.getParish().getSubCounty().getCounty().getName();
		fullName += ", "
				+ this.getParish().getSubCounty().getCounty().getDistrict()
						.getName();
		return fullName;
	}

	@ManyToOne
	@JoinColumn(name = "parish_id", nullable = false)
	public Parish getParish() {
		return parish;
	}

	public void setParish(Parish parish) {
		this.parish = parish;
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
		Village other = (Village) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Village o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

}
