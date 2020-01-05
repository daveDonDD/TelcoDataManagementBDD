package com.ait.callData;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class UE {
	@Id
	private int tac;
	private String marketing_name;
	private String manufacturer;
	private String access_capability;
	private String model;
	private String vendor_name;
	private String ue_type;
	private String os;
	private String input_mode;

	public UE() {

	}

	public UE(final int tac, final String marketing_name, final String manufacturer, final String access_capability,
			final String model, final String vendor_name, final String ue_type, final String os,
			final String input_mode) {
		this.tac = tac;
		this.marketing_name = marketing_name;
		this.manufacturer = manufacturer;
		this.access_capability = access_capability;
		this.model = model;
		this.vendor_name = vendor_name;
		this.ue_type = ue_type;
		this.os = os;
		this.input_mode = input_mode;
	}

	public int getTac() {
		return tac;
	}

	public void setTac(final int tac) {
		this.tac = tac;
	}

	public String getMarketing_name() {
		return marketing_name;
	}

	public void setMarketing_name(final String marketing_name) {
		this.marketing_name = marketing_name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(final String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getAccess_capability() {
		return access_capability;
	}

	public void setAccess_capability(final String access_capability) {
		this.access_capability = access_capability;
	}

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(final String vendor_name) {
		this.vendor_name = vendor_name;
	}

	public String getUe_type() {
		return ue_type;
	}

	public void setUe_type(final String ue_type) {
		this.ue_type = ue_type;
	}

	public String getOs() {
		return os;
	}

	public void setOs(final String os) {
		this.os = os;
	}

	public String getInput_mode() {
		return input_mode;
	}

	public void setInput_mode(final String input_mode) {
		this.input_mode = input_mode;
	}

}
