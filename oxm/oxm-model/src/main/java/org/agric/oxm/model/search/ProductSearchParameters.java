package org.agric.oxm.model.search;

import org.agric.oxm.model.Crop;

public class ProductSearchParameters {

	private String nameOrDescription;
	private Crop crop;

	public ProductSearchParameters() {

	}

	public String getNameOrDescription() {
		return nameOrDescription;
	}

	public void setNameOrDescription(String nameOrDescription) {
		this.nameOrDescription = nameOrDescription;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

}
