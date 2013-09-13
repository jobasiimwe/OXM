package org.agric.oxm.model.search;

import java.util.Date;

import org.agric.oxm.model.User;

public class BlogSearchParams {

	private String text, createdByName;
	private User createdBy;
	private Date fromDate, toDate;

	private Boolean draft, adminView;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
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

	public Boolean getDraft() {
		return draft;
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	public Boolean getAdminView() {
		return adminView;
	}

	public void setAdminView(Boolean adminView) {
		this.adminView = adminView;
	}

}