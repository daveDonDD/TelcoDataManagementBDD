package com.ait.TDMBDDService;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class EventCauseDTO implements Serializable {


	
	private int cause_code;

	
	private int event_id;

	
	private String description;

	public EventCauseDTO() {

	}

	public EventCauseDTO(final int cause_code, final int event_id, final String description) {
		this.cause_code = cause_code;
		this.event_id = event_id;
		this.description = description;
	}
	
	

	public int getCause_code() {
		return cause_code;
	}

	public void setCause_code(final int cause_code) {
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
