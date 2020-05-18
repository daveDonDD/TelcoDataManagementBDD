package com.ait.callData;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@IdClass(MccMnc.class)
public class MccMnc implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	private int mcc;
	
	@Id
	private int mnc;
	
	private String country;
	private String operator;

	public MccMnc() {}

	public MccMnc(final int mcc, final int mnc, final String country, final String operator) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.country = country;
		this.operator = operator;
	}

	public int getMcc() {
		return mcc;
	}

	public void setMcc(final int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(final int mnc) {
		this.mnc = mnc;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(final String operator) {
		this.operator = operator;
	}
}
