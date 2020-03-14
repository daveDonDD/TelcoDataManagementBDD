package com.ait.TDMBDDService;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class EventCauseDTO {


	
	private String cause_code;

	
	private int event_id;

	
	private String description;

	public EventCauseDTO() {

	}

	public EventCauseDTO(final String cause_code, final int event_id, final String description) {
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
