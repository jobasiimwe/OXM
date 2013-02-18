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
@Table(name = "parishes")
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

    @Column(name = "parish_name", nullable = false)
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

    @SuppressWarnings("deprecation")
    @OneToMany(mappedBy = "parish", cascade = { CascadeType.ALL })
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
	    org.hibernate.annotations.CascadeType.DELETE,
	    org.hibernate.annotations.CascadeType.MERGE,
	    org.hibernate.annotations.CascadeType.PERSIST,
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
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
    public int compareTo(Parish o) {
	return this.getName().compareToIgnoreCase(o.getName());
    }

}
