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

	private Product product;
	private SellingPlace sellingPlace;
	private Concept unitOfMeasure;
	private Double qty, retail, wholeSale;
	private Date date;

	public Price() {
		super();
	}

	public Price(Product product, SellingPlace sellingPlace,
			Concept unitOfMeasure, Double qty, Double retailPrice,
			Double wholeSalePrice, Date date) {
		this.setProduct(product);
		this.setSellingPlace(sellingPlace);
		this.setUnitOfMeasure(unitOfMeasure);
		this.setQty(qty);
		this.setRetail(retailPrice);
		this.setWholeSale(wholeSalePrice);
		this.setDate(date);
	}

	public Price(Product product) {
		super();
		this.setProduct(product);
	}

	@OneToOne
	@JoinColumn(name = "product_id", nullable = false)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "selling_place_id", nullable = false)
	public SellingPlace getSellingPlace() {
		return sellingPlace;
	}

	public void setSellingPlace(SellingPlace sellingPlace) {
		this.sellingPlace = sellingPlace;
	}

	// private Concept sellType;
	//
	// @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	// @JoinColumn(name = "selling_type", nullable = true)
	// public Concept getSellType() {
	// return sellType;
	// }
	//
	// public void setSellType(Concept sellType) {
	// this.sellType = sellType;
	// }

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "unit_of_measure", nullable = true)
	public Concept getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(Concept unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	@Column(name = "price_date", nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "quantity", nullable = false)
	public Double getQty() {
		return qty;
	}

	public void setQty(Double quantity) {
		this.qty = quantity;
	}

	@Column(name = "retail", nullable = true)
	public Double getRetail() {
		return retail;
	}

	public void setRetail(Double retail) {
		this.retail = retail;
	}

	@Column(name = "whole_sale", nullable = true)
	public Double getWholeSale() {
		return wholeSale;
	}

	public void setWholeSale(Double wholeSale) {
		this.wholeSale = wholeSale;
	}

	@Override
	public int compareTo(Price o) {
		return this.getDate().compareTo(o.getDate());
	}

	public static void copy(Price dest, Price source) {
		// product sellingPlace unitOfMeasure;
		// qty, retail, wholeSale date;
		dest.setProduct(source.getProduct());
		dest.setSellingPlace(source.getSellingPlace());
		dest.setUnitOfMeasure(source.getUnitOfMeasure());
		dest.setQty(source.getQty());
		dest.setRetail(source.getRetail());
		dest.setWholeSale(source.getWholeSale());
		dest.setDate(source.getDate());
	}
}
