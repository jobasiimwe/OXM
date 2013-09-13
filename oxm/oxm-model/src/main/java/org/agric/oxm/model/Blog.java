package org.agric.oxm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "blog")
public class Blog extends BaseData implements Comparable<Blog> {

	private String title;
	private String text;
	private User createdBy;
	private Date dateCreated;

	private Boolean draft = true;

	private List<BlogComment> blogComments;

	public Blog() {

	}

	public Blog(User createdBy) {
		this.setCreatedBy(createdBy);
	}

	@Column(name = "title", nullable = true)
	public String getTitle() {
		if (StringUtils.isNotBlank(title))
			return title.trim();
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "text", nullable = false, length = 32768)
	public String getText() {
		if (StringUtils.isNotBlank(text))
			return text.trim();
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Transient
	public String getPreText() {
		if (this.getText().length() > 300)
			return this.getText().substring(0, 300).trim();
		else
			return this.getText().trim();
	}

	@ManyToOne
	@JoinColumn(name = "created_by_id", nullable = false)
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "date_created", nullable = false)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@OneToMany(mappedBy = "blog", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<BlogComment> getBlogComments() {
		return blogComments;
	}

	public void setBlogComments(List<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}

	@Column(name = "draft", nullable = true)
	public Boolean getDraft() {
		return draft;
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	// ====================================================================

	@Override
	public int compareTo(Blog o) {
		return this.getDateCreated().compareTo(o.getDateCreated());
	}
}
