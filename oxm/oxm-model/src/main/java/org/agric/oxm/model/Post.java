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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.agric.oxm.model.util.MyDateUtil;

@Entity
@Table(name = "post")
public class Post extends BaseData implements Comparable<Post> {

	private User owner;

	private Date datePosted;

	private Crop crop;

	private List<Comment> comments;

	private Concept type;

	private String text;

	public Post() {

	}

	@ManyToOne
	@JoinColumn(name = "owner", nullable = true)
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_posted", nullable = false)
	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	@Transient
	public String getDisplayDate() {
		return MyDateUtil.getDisplayDate(this.getDatePosted());
	}

	@ManyToOne
	@JoinColumn(name = "crop_id", nullable = true)
	public Crop getCrop() {
		return crop;
	}

	public void setCrop(Crop crop) {
		this.crop = crop;
	}

	@OneToMany(mappedBy = "post", cascade = { CascadeType.ALL }, orphanRemoval = true)
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Column(name = "text", nullable = false, length = 1000)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@ManyToOne
	@JoinColumn(name = "type_id", nullable = true)
	public Concept getType() {
		return type;
	}

	public void setType(Concept type) {
		this.type = type;
	}

	public void addComment(Comment comment) {
		if (comment == null) {
			return;
		}

		if (this.getComments() == null) {
			this.setComments(new ArrayList<Comment>());
		}

		this.getComments().add(comment);
		comment.setPost(this);
	}

	public void removeComment(Comment comment) {
		if (comment == null || this.getComments() == null) {
			return;
		}

		this.getComments().remove(comment);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Post o) {
		return this.getDatePosted().compareTo(o.getDatePosted());
	}

}
