package com.ait.Service;

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
