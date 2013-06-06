/**
 * 
 */
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
@Table(name = "sub_county")
public class SubCounty extends BaseData implements Comparable<SubCounty> {

	private String name;

	private County county;

	private List<Parish> parishes;

	/**
	 * default constructor
	 */
	public SubCounty() {

	}

	public SubCounty(String id) {
		this.setId(id);
	}

	/**
	 * Creates a new object for this entity.
	 */
	public SubCounty(String name, County county) {
		super();
		this.setName(name);
		this.setCounty(county);
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

		fullName += ", " + this.getCounty().getName();
		fullName += ", " + this.getCounty().getDistrict().getName();
		return fullName;

	}

	@ManyToOne
	@JoinColumn(name = "county_id", nullable = false)
	public County getCounty() {
		return county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	/**
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "subCounty", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<Parish> getParishes() {
		return parishes;
	}

	@Transient
	public String getParishesString() {
		String returnString = "";

		if (parishes != null) {
			if (parishes.size() > 0) {
				for (int i = 0; i < parishes.size() && i < 3; i++) {
					if (StringUtils.isBlank(returnString))
						returnString += parishes.get(i).getName();
					else
						returnString += ", " + parishes.get(i).getName();
				}
				if (parishes.size() > 3) {
					int x = parishes.size() - 3;
					returnString += ", and " + x + " more";
				}
			}
		}

		if (StringUtils.isBlank(returnString))
			returnString = "--";
		return returnString;
	}

	/**
	 * 
	 * @param parishes
	 */
	public void setParishes(List<Parish> parishes) {
		this.parishes = parishes;
	}

	/**
	 * adds a given parish
	 * 
	 * @param county
	 */
	public void addParish(Parish parish) {
		if (parish == null) {
			return;
		}

		if (this.getParishes() == null) {
			this.setParishes(new ArrayList<Parish>());
		}

		this.getParishes().add(parish);
		parish.setSubCounty(this);
	}

	/**
	 * removes the given parish
	 * 
	 * @param county
	 */
	public void removeParish(Parish parish) {
		if (parish == null || this.getParishes() == null) {
			return;
		}

		this.getParishes().remove(parish);
	}

	public void removeParishesByIds(String[] parishIdzToDelete) {
		for (String id : parishIdzToDelete) {

			Parish parish = new Parish(id);
			if (this.getParishes().contains(parish)) {
				getParishes().remove(parish);
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
		SubCounty other = (SubCounty) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(SubCounty o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

}
