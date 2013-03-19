package org.agric.oxm.model.search;

import java.util.Date;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.SellingPlace;

public class PriceSearchParameters {

	private Crop crop;
	private SellingPlace sellingPlace;
	private Concept sellType;
	private Date fromDate;
	private Date toDate;
	private Boolean adminView;

	public PriceSearchParameters() {

	}

	public PriceSearchParameters(Boolean adminView) {
		this.setAdminView(adminView);
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public SellingPlace getSellingPlace() {
		return sellingPlace;
	}

	public void setSellingPlace(SellingPlace sellingPlace) {
		this.sellingPlace = sellingPlace;
	}

	public Concept getSellType() {
		return sellType;
	}

	public void setSellType(Concept sellType) {
		this.sellType = sellType;
	}

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
