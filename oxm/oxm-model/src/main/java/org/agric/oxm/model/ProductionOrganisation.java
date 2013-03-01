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
@Table(name = "production_organisation")
public class ProductionOrganisation extends BaseData {

    private String name;

    private List<Producer> producers;

    public ProductionOrganisation() {
	super();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @SuppressWarnings("deprecation")
    @OneToMany(mappedBy = "productionOrganisation", cascade = { CascadeType.ALL })
     @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
	    org.hibernate.annotations.CascadeType.DELETE,
	    org.hibernate.annotations.CascadeType.MERGE,
	    org.hibernate.annotations.CascadeType.PERSIST,
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public List<Producer> getProducers() {
	return producers;
    }

    public void setProducers(List<Producer> producers) {
	this.producers = producers;
    }

    public void addProducer(Producer producer) {
	if (producer == null) {
	    return;
	}

	if (this.getProducers() == null) {
	    this.setProducers(new ArrayList<Producer>());
	}

	this.getProducers().add(producer);
    }

    public void removeProducer(Producer producer) {
	if (producer == null || this.getProducers() == null) {
	    return;
	}

	this.getProducers().remove(producer);
    }

}
