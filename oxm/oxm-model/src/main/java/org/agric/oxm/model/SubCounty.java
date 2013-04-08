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

	private District district;

	private List<Parish> parishes;

	/**
	 * default constructor
	 */
	public SubCounty() {

	}

	/**
	 * Creates a new object for this entity.
	 */
	public SubCounty(String name, District district) {
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
	 * @param district
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
	 * @param district
	 */
	public void removeParish(Parish parish) {
		if (parish == null || this.getParishes() == null) {
			return;
		}

		this.getParishes().remove(parish);
	}

	@Override
	public int compareTo(SubCounty o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}

}
