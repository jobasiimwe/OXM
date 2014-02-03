package org.agric.oxm.model.feed;

public class FeedPrice {

	String market;
	String product;
	String unit;
	String dateStr;
	String retailStr;
	String wholeSaleStr;

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getRetailStr() {
		return retailStr;
	}

	public void setRetailStr(String retailStr) {
		this.retailStr = retailStr;
	}

	public String getWholeSaleStr() {
		return wholeSaleStr;
	}

	public void setWholeSaleStr(String wholeSaleStr) {
		this.wholeSaleStr = wholeSaleStr;
	}

	// market product unit;
	// dateStr retailStr wholeSaleStr;
	@Override
	public String toString() {
		return " [" + dateStr + " => " + market + ", " + product
				+ ", retail=" + retailStr + ", wholeSale=" + wholeSaleStr + "]";
	}

	public String toString2() {
		return "FeedPrice [market=" + market + ", product=" + product
				+ ", unit=" + unit + ", retailStr=" + retailStr
				+ ", wholeSaleStr=" + wholeSaleStr + " dateStr=" + dateStr
				+ "]";
	}

}
