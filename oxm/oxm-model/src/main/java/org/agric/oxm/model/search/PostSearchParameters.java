package org.agric.oxm.model.search;

import java.util.Date;
import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.User;

public class PostSearchParameters {

	private String ownerName;
	private User owner;
	private List<Crop> crops;
	private Concept postType;
	private Date after;
	private Date before;

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

	public List<Crop> getCrops() {
		return crops;
	}

	public void setCrops(List<Crop> crops) {
		this.crops = crops;
	}

	public Concept getPostType() {
		return postType;
	}

	public void setPostType(Concept postType) {
		this.postType = postType;
	}

	public Date getAfter() {
		return after;
	}

	public void setAfter(Date after) {
		this.after = after;
	}

	public Date getBefore() {
		return before;
	}

	public void setBefore(Date before) {
		this.before = before;
	}

}
