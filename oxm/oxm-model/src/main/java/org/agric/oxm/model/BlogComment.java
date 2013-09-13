package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "blog_comment")
public class BlogComment extends BaseData implements Comparable<BlogComment> {

	private List<BlogComment> replies;

	private BlogComment parent;

	private Blog blog;

	private String name;
	private User createdBy;
	private Date dateCreated;
	private String text;

	private Boolean moderated, show;
	private String moderationComment;

	public BlogComment() {

	}

	public BlogComment(String id) {
		this.setId(id);
	}

	@OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<BlogComment> getReplies() {
		return replies;
	}

	public void setReplies(List<BlogComment> replies) {
		this.replies = replies;
	}

	public void addReply(BlogComment reply) {
		if (reply == null) {
			return;
		}

		if (this.getReplies() == null) {
			this.setReplies(new ArrayList<BlogComment>());
		}

		this.getReplies().add(reply);
	}

	public void removeReply(BlogComment reply) {
		if (reply == null || this.getReplies() == null) {
			return;
		}

		this.getReplies().remove(reply);
	}

	public void removeRepliesByIds(String[] replyIdzToDelete) {
		for (String id : replyIdzToDelete) {

			BlogComment reply = new BlogComment(id);
			if (this.getReplies().contains(reply))
				this.getReplies().remove(reply);
		}
	}

	@ManyToOne
	@JoinColumn(name = "parent_blogcomment_id", nullable = true)
	public BlogComment getParent() {
		return parent;
	}

	public void setParent(BlogComment parent) {
		this.parent = parent;
	}

	@ManyToOne
	@JoinColumn(name = "blog_id", nullable = true)
	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@ManyToOne
	@JoinColumn(name = "created_by_id", nullable = true)
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	// for anonymous comments
	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "date_created", nullable = false)
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Column(name = "text", nullable = false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	// =================================================================
	// =================================================================

	@Column(name = "moderated", nullable = true)
	public Boolean getModerated() {
		return moderated;
	}

	public void setModerated(Boolean moderated) {
		this.moderated = moderated;
	}

	@Column(name = "show", nullable = true)
	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

	@Column(name = "moderation_comment", nullable = true)
	public String getModerationComment() {
		return moderationComment;
	}

	public void setModerationComment(String moderationComment) {
		this.moderationComment = moderationComment;
	}

	// =================================================================
	// =================================================================
	@Override
	public int compareTo(BlogComment o) {
		return this.getDateCreated().compareTo(o.getDateCreated());
	}

}
