package org.agric.oxm.model.search;

import java.util.Date;

import org.agric.oxm.model.Product;
import org.agric.oxm.model.SellingPlace;

public class PriceSearchParams {

	private Product product;
	private SellingPlace sellingPlace;
	// private Concept sellType;
	private Date fromDate, toDate;
	private Boolean adminView;

	public PriceSearchParams() {

	}

	public PriceSearchParams(Product product, SellingPlace sellingPlace,
			Date date) {
		this.setProduct(product);
		this.setSellingPlace(sellingPlace);
		this.setFromDate(date);
		this.setToDate(date);
	}

	public PriceSearchParams(Boolean adminView) {
		this.setAdminView(adminView);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public SellingPlace getSellingPlace() {
		return sellingPlace;
	}

	public void setSellingPlace(SellingPlace sellingPlace) {
		this.sellingPlace = sellingPlace;
	}

	// public Concept getSellType() {
	// return sellType;
	// }
	//
	// public void setSellType(Concept sellType) {
	// this.sellType = sellType;
	// }

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Boolean getAdminView() {
		return adminView;
	}

	public void setAdminView(Boolean adminView) {
		this.adminView = adminView;
	}
}
