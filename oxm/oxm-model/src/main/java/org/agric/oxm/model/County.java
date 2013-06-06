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
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "county")
public class County extends BaseData implements Comparable<County> {

	private String name;

	private District district;

	private List<SubCounty> subCounties;

	/**
	 * default constructor
	 */
	public County() {

	}

	public County(District district) {
		this.setDistrict(district);
	}

	public County(String id) {
		this.setId(id);
	}

	/**
	 * Creates a new object for this entity.
	 */
	public County(String name, District district) {
		super();
		this.setName(name);
		this.setDistrict(district);
	}

	/**
	 * 
	 * @return
	 */
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

		fullName += ", " + this.getDistrict().getName();
		return fullName;

	}

	@ManyToOne
	@JoinColumn(name = "district_id", nullable = false)
	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	@OneToMany(mappedBy = "county", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<SubCounty> getSubCounties() {
		return subCounties;
	}

	@Transient
	public String getSubCountiesString() {
		String returnString = "";

		if (subCounties != null) {
			if (subCounties.size() > 0) {
				for (int i = 0; i < subCounties.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += subCounties.get(i).getName();
					else
						returnString += ", " + subCounties.get(i).getName();
				}
				if (subCounties.size() > 3) {
					int x = subCounties.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		if (StringUtils.isBlank(returnString))
			returnString = "--";
		return returnString;
	}

	public void setSubCounties(List<SubCounty> subCounties) {
		this.subCounties = subCounties;
	}

	public void addSubCounty(SubCounty subCounty) {
		if (subCounty == null) {
			return;
		}

		if (this.getSubCounties() == null) {
			this.setSubCounties(new ArrayList<SubCounty>());
		}

		this.getSubCounties().add(subCounty);
	}

	public void removeSubCounty(SubCounty subCounty) {
		if (subCounty == null || this.getSubCounties() == null) {
			return;
		}

		this.getSubCounties().remove(subCounty);
	}
	
	public void removeSubCountiesByIds(String[] subCountyIdzToDelete) {
		for (String id : subCountyIdzToDelete) {

			SubCounty subCounty = new SubCounty(id);
			if (this.getSubCounties().contains(subCounty)) {
				this.getSubCounties().remove(subCounty);
			}
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
		County other = (County) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}


	@Override
	public int compareTo(County o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

}
