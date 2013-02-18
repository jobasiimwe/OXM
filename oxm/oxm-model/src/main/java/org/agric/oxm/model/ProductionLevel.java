package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "production_level")
public class ProductionLevel extends BaseData {

    private Producer producer;

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
    public Producer getProducer() {
	return producer;
    }

    public void setProducer(Producer producer) {
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

}
