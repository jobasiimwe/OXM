package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "producer")
public class Producer extends BaseData {

    private User user;
    private List<Land> lands;
    private GisCordinate gisCordinates;
    private ProductionOrganisations productionOrganisation;
    private SubCounty subCounty;
    private Parish parish;
    private Village village;

    public Producer() {
	super();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    @SuppressWarnings("deprecation")
    @OneToMany(mappedBy = "producer", cascade = { CascadeType.ALL })
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE,
	    org.hibernate.annotations.CascadeType.DELETE,
	    org.hibernate.annotations.CascadeType.MERGE,
	    org.hibernate.annotations.CascadeType.PERSIST,
	    org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public List<Land> getLands() {
	return lands;
    }

    public void setLands(List<Land> lands) {
	this.lands = lands;
    }

    @ManyToOne
    @JoinColumn(name = "gis_cordinates_id", nullable = false)
    public GisCordinate getGisCordinates() {
	return gisCordinates;
    }

    public void setGisCordinates(GisCordinate gisCordinates) {
	this.gisCordinates = gisCordinates;
    }

    @ManyToOne
    @JoinColumn(name = "production_organisation_id", nullable = false)
    public ProductionOrganisations getProductionOrganisation() {
	return productionOrganisation;
    }

    public void setProductionOrganisation(ProductionOrganisations productionOrganisation) {
	this.productionOrganisation = productionOrganisation;
    }

    @ManyToOne
    @JoinColumn(name = "subcounty_id", nullable = false)
    public SubCounty getSubCounty() {
	return subCounty;
    }

    public void setSubCounty(SubCounty subCounty) {
	this.subCounty = subCounty;
    }

    @ManyToOne
    @JoinColumn(name = "parish_id", nullable = false)
    public Parish getParish() {
	return parish;
    }

    public void setParish(Parish parish) {
	this.parish = parish;
    }

    @ManyToOne
    @JoinColumn(name = "village_id", nullable = false)
    public Village getVillage() {
	return village;
    }

    public void setVillage(Village village) {
	this.village = village;
    }

    public void addLand(Land land) {
	if (land == null) {
	    return;
	}

	if (this.getLands() == null) {
	    this.setLands(new ArrayList<Land>());
	}

	this.getLands().add(land);
	land.setProducer(this);
    }

    public void removeLand(Land land) {
	if (land == null || this.getLands() == null) {
	    return;
	}

	this.getLands().remove(land);
    }
}
