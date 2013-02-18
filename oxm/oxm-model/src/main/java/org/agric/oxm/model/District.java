package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "districts")
public class District extends BaseData implements Comparable<District> {

    private String name;
    private List<SubCounty> subCounties;

    public District() {
	super();
    }

    public District(String name) {
	super();
	this.name = name;
    }

    @Column(name = "district_name", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @SuppressWarnings("deprecation")
    @OneToMany(mappedBy = "district", cascade = { CascadeType.ALL })
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
	    org.hibernate.annotations.CascadeType.DELETE,
	    org.hibernate.annotations.CascadeType.MERGE,
	    org.hibernate.annotations.CascadeType.PERSIST,
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public List<SubCounty> getSubCounties() {
	return subCounties;
    }

    public void setCounties(List<SubCounty> subCounties) {
	this.subCounties = subCounties;
    }
    
    public void addSubCounty(SubCounty subCounty) {
	if (subCounty == null) {
	    return;
	}

	if (this.getSubCounties() == null) {
	    this.setCounties(new ArrayList<SubCounty>());
	}

	this.getSubCounties().add(subCounty);
    }

    public void removeSubCounty(SubCounty subCounty) {
	if (subCounty == null || this.getSubCounties() == null) {
	    return;
	}

	this.getSubCounties().remove(subCounty);
    }

    @Override
    public int compareTo(District o) {
	return this.getName().compareToIgnoreCase(o.getName());
    }

}
