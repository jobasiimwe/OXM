package org.agric.oxm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "selling_place")
public class SellingPlace extends BaseData {

    private String name;

    private Concept type;

    public SellingPlace() {
	super();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "type_id", nullable = false)
    public Concept getType() {
        return type;
    }

    public void setType(Concept type) {
        this.type = type;
    }
    
    
}
