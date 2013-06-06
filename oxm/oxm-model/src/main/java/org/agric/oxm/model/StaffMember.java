package org.agric.oxm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "staff_member")
public class StaffMember extends BaseData implements Comparable<StaffMember> {

	private ProducerOrg producerOrg;

	private User user;

	private Position position;

	private Date appointmentDate;

	private Date dateLeft;

	public StaffMember() {

	}

	public StaffMember(String id) {
		this.setId(id);
	}

	public StaffMember(ProducerOrg pOrg) {
		this.setProducerOrg(pOrg);
	}

	@ManyToOne
	@JoinColumn(name = "producer_organisation_id", nullable = false)
	public ProducerOrg getProducerOrg() {
		return producerOrg;
	}

	public void setProducerOrg(ProducerOrg producerOrg) {
		this.producerOrg = producerOrg;
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

	@Column(name = "appointment_date", nullable = false)
	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	@Column(name = "date_left", nullable = true)
	public Date getDateLeft() {
		return dateLeft;
	}

	public void setDateLeft(Date dateLeft) {
		this.dateLeft = dateLeft;
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
		StaffMember other = (StaffMember) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(StaffMember o) {
		return this.getPosition().compareTo(o.getPosition());
	}
}
