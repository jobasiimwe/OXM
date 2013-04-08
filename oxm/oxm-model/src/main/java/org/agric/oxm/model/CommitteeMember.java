package org.agric.oxm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "committee_member")
public class CommitteeMember extends BaseData implements
		Comparable<CommitteeMember> {

	private Committee committee;

	private User user;

	private Position position;

	private Date fromDate;

	private Date toDate;

	public CommitteeMember() {
		super();
	}

	public CommitteeMember(String id) {
		super();
		this.setId(id);
	}

	public CommitteeMember(Committee committee) {
		super();
		this.setCommittee(committee);
	}

	@ManyToOne
	@JoinColumn(name = "committee_id", nullable = false)
	public Committee getCommittee() {
		return committee;
	}

	public void setCommittee(Committee committee) {
		this.committee = committee;
	}

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "postion_id", nullable = false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Column(name = "from_date", nullable = true)
	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name = "to_date", nullable = true)
	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
		CommitteeMember other = (CommitteeMember) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(CommitteeMember o) {
		// return this.getHolder().getName()
		// .compareToIgnoreCase(o.getHolder().getName());
		return 0;
	}
}
