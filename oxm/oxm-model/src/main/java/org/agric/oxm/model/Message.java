package org.agric.oxm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "message")
public class Message extends BaseData implements Comparable<Message> {

	private User from;

	private User to;

	private String message;

	private Date datePosted;

	private boolean fromCleared = false;

	private boolean toCleared = false;

	private boolean isSeen = false;

	public Message() {
		super();
	}

	@ManyToOne
	@JoinColumn(name = "from", nullable = false)
	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	@ManyToOne
	@JoinColumn(name = "to", nullable = false)
	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	@Column(name = "message", nullable = false, length = 1000)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_posted", nullable = false)
	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	@Type(type = "true_false")
	@Column(name = "is_sender_cleared", nullable = true)
	public boolean isFromCleared() {
		return fromCleared;
	}

	public void setFromCleared(boolean senderCleared) {
		this.fromCleared = senderCleared;
	}

	@Type(type = "true_false")
	@Column(name = "is_to_cleared", nullable = true)
	public boolean isToCleared() {
		return toCleared;
	}

	public void setToCleared(boolean toCleared) {
		this.toCleared = toCleared;
	}

	@Type(type = "true_false")
	@Column(name = "is_seen", nullable = true)
	public boolean isSeen() {
		return isSeen;
	}

	public void setSeen(boolean isSeen) {
		this.isSeen = isSeen;
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
		Message other = (Message) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Message o) {
		return this.getDatePosted().compareTo(o.getDatePosted());
	}
}
