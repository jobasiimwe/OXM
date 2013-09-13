package org.agric.oxm.model.search;

import java.util.Date;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.User;

public class PostSearchParameters {

	private String ownerName;
	private User owner;
	private Crop crop;
	private Concept postType;
	private Date fromDate;
	private Date toDate;

	public PostSearchParameters() {

	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	public Concept getPostType() {
		return postType;
	}

	public void setPostType(Concept postType) {
		this.postType = postType;
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

}
