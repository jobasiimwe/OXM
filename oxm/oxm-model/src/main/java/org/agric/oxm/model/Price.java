package org.agric.oxm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class Price extends BaseData {

	private Crop crop;

	private SellingPlace sellingPlace;

	private Concept sellType;

	private Concept unitOfMeasure;

	private Double price;

	public Price() {
		super();
	}

	@OneToOne
	@JoinColumn(name = "crop_id", nullable = false)
	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	@ManyToOne
	@JoinColumn(name = "selling_price_id", nullable = false)
	public SellingPlace getSellingPlace() {
		return sellingPlace;
	}

	public void setSellingPlace(SellingPlace sellingPlace) {
		this.sellingPlace = sellingPlace;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "selling_type", nullable = true)
	public Concept getSellType() {
		return sellType;
	}

	public void setSellType(Concept sellType) {
		this.sellType = sellType;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "unit_of_measure", nullable = true)
	public Concept getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(Concept unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@Column(name = "price", nullable = true)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
