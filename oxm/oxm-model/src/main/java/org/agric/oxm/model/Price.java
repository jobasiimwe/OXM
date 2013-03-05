package org.agric.oxm.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "price")
public class Price extends BaseData implements Comparable<Price> {

	private Crop crop;

	private SellingPlace sellingPlace;

	private Concept sellType;

	private Concept unitOfMeasure;

	private Double price;
	
	//date .
	// quanity.

	private Date date;

	private Double quantity;

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
	@JoinColumn(name = "selling_place_id", nullable = false)
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

	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "quantity", nullable = false)
	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
		Price other = (Price) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Price o) {
		return this.getDate().compareTo(o.getDate());
	}

}
