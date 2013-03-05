package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "production_level")
public class ProductionLevel extends BaseData implements
		Comparable<ProductionLevel> {

	private User producer;

	private Season season;

	private Crop crop;

	private int acrage;

	private int projectedAmount;

	private int actualAmount;

	public ProductionLevel() {
		super();
	}

	@ManyToOne
	@JoinColumn(name = "producer", nullable = false)
	public User getProducer() {
		return producer;
	}

	public void setProducer(User producer) {
		this.producer = producer;
	}

	@ManyToOne
	@JoinColumn(name = "season", nullable = false)
	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	@ManyToOne
	@JoinColumn(name = "crop", nullable = false)
	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	@Column(name = "acrage")
	public int getAcrage() {
		return acrage;
	}

	public void setAcrage(int acrage) {
		this.acrage = acrage;
	}

	@Column(name = "projected_Amount")
	public int getProjectedAmount() {
		return projectedAmount;
	}

	public void setProjectedAmount(int projectedAmount) {
		this.projectedAmount = projectedAmount;
	}

	@Column(name = "actual_amount")
	public int getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(int actualAmount) {
		this.actualAmount = actualAmount;
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
		ProductionLevel other = (ProductionLevel) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(ProductionLevel o) {
		return this.getSeason().compareTo(o.getSeason());
	}

}
