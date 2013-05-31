package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.agric.oxm.model.enums.RecordStatus;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseData {

    private String id = null;
    private RecordStatus recordStatus = RecordStatus.ACTIVE;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "record_status", nullable = false)
    public RecordStatus getRecordStatus() {
	return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
	this.recordStatus = recordStatus;
    }

}
