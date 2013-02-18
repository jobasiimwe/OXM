package org.agric.oxm.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "position_holder")
public class PositionHolder extends BaseData {

    private Producer producer;

    private Position position;

    public PositionHolder() {
	super();
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
