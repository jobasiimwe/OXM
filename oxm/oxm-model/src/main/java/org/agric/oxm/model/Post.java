package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "post")
public class Post extends BaseData {

    private User owner;

    private Date datePosted;

    private Crop crop;

    private String otherItem;

    private List<Comment> comments;
    
    private String text;

    public Post() {
	super();
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

    public void setDate(Date datePosted) {
        this.datePosted = datePosted;
    }

    @ManyToOne
    @JoinColumn(name = "crop", nullable = true)
    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    @Column(name = "text", nullable = true)
    public String getOtherItem() {
        return otherItem;
    }

    public void setOtherItem(String otherItem) {
        this.otherItem = otherItem;
    }

    @OneToMany(mappedBy = "comment")
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

    
    
}
