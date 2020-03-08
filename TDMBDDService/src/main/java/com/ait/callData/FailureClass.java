package com.ait.callData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class FailureClass {
	@Id
	private String failure_class;
	private String description;

	public FailureClass() {
	}

	public FailureClass(final String failure_class, final String description) {
		this.failure_class = failure_class;
		this.description = description;
	}

	public String getFailure_class() {
		return failure_class;
	}

	public void setFailure_class(final String failure_class) {
		this.failure_class = failure_class;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
}
