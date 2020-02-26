package com.ait.TDMBDDService;


public class CountImsiFailureDTO {
	
	
	private int failureCount;
	
	private int duration;
	
	private long imsi;
	
	public CountImsiFailureDTO() {
	}

	public CountImsiFailureDTO( final long imsi, final int failureCount, final int duration) {
		
		this.imsi = imsi;
		this.duration = duration;
		this.failureCount = failureCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(final int failureCount) {
		this.failureCount = failureCount;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}
	public long getImsi() {
		return imsi;
	}

	public void setImsi(final long imsi) {
		this.imsi = imsi;
	}

}	
