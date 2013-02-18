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

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "sub_counties")
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
    @Column(name = "subcounty_name", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
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
    @SuppressWarnings("deprecation")
    @OneToMany(mappedBy = "subCounty", cascade = { CascadeType.ALL })
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
	    org.hibernate.annotations.CascadeType.DELETE,
	    org.hibernate.annotations.CascadeType.MERGE,
	    org.hibernate.annotations.CascadeType.PERSIST,
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public List<Parish> getParishes() {
	return parishes;
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
