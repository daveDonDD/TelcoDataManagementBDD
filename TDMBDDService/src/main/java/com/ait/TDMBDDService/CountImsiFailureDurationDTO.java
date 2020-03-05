package com.ait.TDMBDDService;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
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
	public long getImsi() {
		return imsi;
	}

	public void setImsi(final long imsi) {
		this.imsi = imsi;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(final long failureCount) {
		this.failureCount = failureCount;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(final long duration) {
		this.duration = duration;
	}
	
}	
