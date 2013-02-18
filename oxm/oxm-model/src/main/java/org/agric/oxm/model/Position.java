package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "position")
public class Position extends BaseData {

    private String name;

    private int index;

    public Position() {
	super();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Column(name = "index", nullable = false)
    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
    }

}
