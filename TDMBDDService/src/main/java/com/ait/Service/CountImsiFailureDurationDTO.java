package com.ait.Service;


public class CountImsiFailureDurationDTO {
	
	
	private long imsi;

	private long failureCount;
	
	private long duration;
	
	
	public CountImsiFailureDurationDTO() {
	}

	public CountImsiFailureDurationDTO( final long imsi, final long failureCount, final long duration){		
		this.imsi = imsi;
		this.duration = duration;
		this.failureCount = failureCount;
	}

	
}	