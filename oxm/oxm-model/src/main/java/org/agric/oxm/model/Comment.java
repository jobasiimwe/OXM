package org.agric.oxm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "season")
public class Comment extends BaseData {

    private User owner;

    private String text;

    private Date datePosted;

    private Post post;

    public Comment() {
	super();
    }

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User getOwner() {
	return owner;
    }

    public void setOwner(User owner) {
	this.owner = owner;
    }

    @Column(name = "text", nullable = false, length = 1000)
    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_posted", nullable = false)
    public Date getDatePosted() {
	return datePosted;
    }

    public void setDatePosted(Date datePosted) {
	this.datePosted = datePosted;
    }

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
	return post;
    }

    public void setPost(Post post) {
	this.post = post;
    }

}
