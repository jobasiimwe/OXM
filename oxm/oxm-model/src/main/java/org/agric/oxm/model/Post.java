package org.agric.oxm.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

@Entity
@Table(name = "post")
public class Post extends BaseData implements Comparable<Post> {

	private SimpleDateFormat monthName_Year = new SimpleDateFormat(
			"MMMMM - yyyy");

	private SimpleDateFormat dayNum_month_Time = new SimpleDateFormat(
			"dd - MMMMM, HH:mm a");

	private SimpleDateFormat dayName_Time = new SimpleDateFormat(
			"EEEEE - hh:MM a");

	private SimpleDateFormat time = new SimpleDateFormat("hh:MM a");

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

	// @SuppressWarnings("static-access")
	@Transient
	public String getDisplayDate() {

		SimpleDateFormat fmtToUse = new SimpleDateFormat("dd/MMM/yyyy");
		String displayDate = "";

		Calendar caNow = Calendar.getInstance();
		Calendar calDatePosted = Calendar.getInstance();

		caNow.setTime(new Date());
		calDatePosted.setTime(datePosted);

		if (caNow.get(Calendar.YEAR) == calDatePosted.get(Calendar.YEAR)) {
			if (caNow.get(Calendar.MONTH) == calDatePosted.get(Calendar.MONTH)) {
				if (caNow.get(Calendar.DAY_OF_MONTH) == calDatePosted
						.get(Calendar.DAY_OF_MONTH)) {
					Integer minutes = minuteDifference(caNow, calDatePosted);

					if (minutes < 5)
						return "Just Now";
					if (minutes <= 50)
						return minutes + " minutes ago";

					if (minutes > 50 && minutes < 90)
						return "1 hour ago";

					int hours = Math.round(minutes / 60);

					return hours + " Hours ago";

				} else {

					Integer daysDiff = caNow.get(Calendar.DAY_OF_MONTH)
							- calDatePosted.get(Calendar.DAY_OF_MONTH);

					if (daysDiff == 1)
						return "Yesterday " + time.format(datePosted);
					else if (daysDiff < 7)
						fmtToUse = dayName_Time;
					else
						fmtToUse = dayNum_month_Time;
				}

			} else {
				fmtToUse = dayNum_month_Time;
			}
		} else {
			fmtToUse = monthName_Year;
		}

		displayDate = fmtToUse.format(datePosted);

		return displayDate;
	}

	/**
	 * takes times on the same day and returns minute difference
	 * 
	 * @param now
	 * @param then
	 * @return
	 */
	private Integer minuteDifference(Calendar now, Calendar then) {

		if (now.get(Calendar.HOUR_OF_DAY) == then.get(Calendar.HOUR_OF_DAY))
			return now.get(Calendar.MINUTE) - then.get(Calendar.MINUTE);
		else
			return now.get(Calendar.MINUTE)
					+ (60 - then.get(Calendar.MINUTE))
					+ ((now.get(Calendar.HOUR_OF_DAY)
							- then.get(Calendar.HOUR_OF_DAY) - 1) * 60);

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
