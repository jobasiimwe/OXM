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

@Entity
@Table(name = "producer")
public class Producer extends User {

	private User user;
	private Double landSize;
	private List<LandUse> landUses;
	private GisCordinate gisCordinates;
	private ProductionOrganisation productionOrganisation;
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

	@Column(name = "land_size", nullable = true)
	public Double getLandSize() {
		return landSize;
	}

	public void setLandSize(Double landSize) {
		this.landSize = landSize;
	}

	@OneToMany(mappedBy = "producer", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<LandUse> getLandUses() {
		return landUses;
	}

	public void setLandUses(List<LandUse> landUses) {
		this.landUses = landUses;
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
	public ProductionOrganisation getProductionOrganisation() {
		return productionOrganisation;
	}

	public void setProductionOrganisation(
			ProductionOrganisation productionOrganisation) {
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

	public void addLand(LandUse landUse) {
		if (landUse == null) {
			return;
		}

		if (this.getLandUses() == null) {
			this.setLandUses(new ArrayList<LandUse>());
		}

		this.getLandUses().add(landUse);
		landUse.setProducer(this);
	}

	public void removeLand(LandUse landUse) {
		if (landUse == null || this.getLandUses() == null) {
			return;
		}

		this.getLandUses().remove(landUse);
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
		Producer other = (Producer) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}

}
