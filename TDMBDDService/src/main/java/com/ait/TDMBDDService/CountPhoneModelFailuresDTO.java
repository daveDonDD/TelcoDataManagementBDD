package com.ait.TDMBDDService;



import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CountPhoneModelFailuresDTO  {
	
	private int phoneModel;
	
	private int eventId;
	
	private int causeCode;
	
	private long count;
	
	private String eventDescription;

	public CountPhoneModelFailuresDTO() {	
	}
	
	public CountPhoneModelFailuresDTO(int phoneModel, int eventId, int causeCode, long count,String eventDescription) {
		this.phoneModel = phoneModel;
		this.eventId = eventId;
		this.causeCode = causeCode;
		this.eventDescription = eventDescription;
		this.count = count;
	}

	public int getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(int phoneModel) {
		this.phoneModel = phoneModel;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(int causeCode) {
		this.causeCode = causeCode;
	}


	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	
}
