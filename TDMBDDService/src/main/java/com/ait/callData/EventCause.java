package com.ait.callData;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement (name="eventCause")
@IdClass(EventCause.class)
public class EventCause implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@XmlTransient
	private String cause_code;

	@Id
	@XmlTransient
	private int event_id;

	@XmlTransient
	private String description;

	public EventCause() {

	}

	public EventCause(final String cause_code, final int event_id, final String description) {
		this.cause_code = cause_code;
		this.event_id = event_id;
		this.description = description;
	}
	
	

	public String getCause_code() {
		return cause_code;
	}

	public void setCause_code(final String cause_code) {
		this.cause_code = cause_code;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(final int event_id) {
		this.event_id = event_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
