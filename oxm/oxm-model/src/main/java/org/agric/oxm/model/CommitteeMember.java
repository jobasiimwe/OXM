package org.agric.oxm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "committee_member")
public class CommitteeMember extends BaseData {

	private Committee committee;

	private Producer producer;

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
	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
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

}
