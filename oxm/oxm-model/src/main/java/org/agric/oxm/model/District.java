package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "district")
public class District extends BaseData implements Comparable<District> {

	private String name;
	private List<County> counties;

	public District() {
		super();
	}

	public District(String name) {
		super();
		this.name = name;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "district", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<County> getCounties() {
		return counties;
	}

	@Transient
	public String getCountiesString() {
		String returnString = "";

		if (counties != null) {
			if (counties.size() > 0) {
				for (int i = 0; i < counties.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += counties.get(i).getName();
					else
						returnString += ", " + counties.get(i).getName();
				}
				if (counties.size() > 3) {
					int x = counties.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		if (StringUtils.isBlank(returnString))
			returnString = "--";
		return returnString;
	}

	public void setCounties(List<County> counties) {
		this.counties = counties;
	}

	public void addCounty(County county) {
		if (county == null) {
			return;
		}

		if (this.getCounties() == null) {
			this.setCounties(new ArrayList<County>());
		}

		this.getCounties().add(county);
	}

	public void removeCounty(County county) {
		if (county == null || this.getCounties() == null) {
			return;
		}

		this.getCounties().remove(county);
	}

	public void removeCountiesByIds(String[] countyIdzToDelete) {
		for (String id : countyIdzToDelete) {

			County county = new County(id);
			if (this.getCounties().contains(county))
				this.getCounties().remove(county);
		}
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
		District other = (District) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(District o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

}
