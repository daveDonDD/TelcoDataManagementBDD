package com.ait.TDMBDDService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImsiWithinDatesDTO {
	
	
	private long imsi;

	
	public ImsiWithinDatesDTO() {
	}

	public ImsiWithinDatesDTO( final long imsi) {
		
		this.imsi = imsi;
		
	}
	public long getImsi() {
		return imsi;
	}

	public void setImsi(final long imsi) {
		this.imsi = imsi;
	}


	
}	
