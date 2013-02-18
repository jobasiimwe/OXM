package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "villages")
public class Village extends BaseData implements Comparable<Village> {

    private String name;

    private Parish parish;

    public Village() {
	super();
    }

    public Village(String name, Parish parish) {
	super();
	this.setName(name);
	this.setParish(parish);
    }

    @Column(name = "village_name", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
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
    public int compareTo(Village o) {
	return this.getName().compareToIgnoreCase(o.getName());
    }

    @Override
    public String toString() {
	if (this.getId() != null && this.getId().trim().length() > 0)
	    return this.getId();

	return super.toString();
    }

}
