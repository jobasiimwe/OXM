package org.agric.oxm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "committee_member")
public class CommitteeMember extends BaseData implements
		Comparable<CommitteeMember> {

	private Committee committee;

	private User producer;

	private Position position;

	public CommitteeMember() {
		super();
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
	@JoinColumn(name = "producer_id", nullable = false)
	public User getProducer() {
		return producer;
	}

	public void setProducer(User producer) {
		this.producer = producer;
	}

	@ManyToOne
	@JoinColumn(name = "postion_id", nullable = false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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
		return this.getProducer().getName()
				.compareToIgnoreCase(o.getProducer().getName());
	}
}
