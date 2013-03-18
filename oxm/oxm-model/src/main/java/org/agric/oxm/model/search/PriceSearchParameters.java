package org.agric.oxm.model.search;

import java.util.Date;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.SellingPlace;

public class PriceSearchParameters {

	private Crop crop;
	private SellingPlace sellingPlace;
	private Date fromDate;
	private Date toDate;
	private Date onDate;

	public PriceSearchParameters() {

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

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}

}
