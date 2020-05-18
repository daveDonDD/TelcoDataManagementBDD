package com.ait.callData;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
@IdClass(BaseData.class)
public class BaseData implements Serializable{
  	private static final long serialVersionUID = 1L;
	/*@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="basedata_ID")
	private Long row_id;*/
	
	@Id
	private int event_id;
	@Id
	private LocalDateTime date_time;
	@Id
	private String failure_class;
	@Id
	private int ue_type;
	@Id
	private int market;
	@Id
	private int operator;
	@Id
	private int cell_id;
	@Id
	private int duration;
	@Id
	private String cause_code;
	@Id
	private String ne_version;
	@Id
	private long imsi;
	@Id
	private long hier3_id;
	@Id
	private long hier32_id;
	@Id
	private long hier321_id;

	public BaseData() {
	}

	public BaseData(final LocalDateTime date_time, final int event_id, final String failure_class, final int ue_type,
			final int market, final int operator, final int cell_id, final int duration, final String cause_code,
			final String ne_version, final long imsi, final long hier3_id, final long hier32_id,
			final long hier321_id) {
		this.date_time = date_time;
		this.event_id = event_id;
		this.failure_class = failure_class;
		this.ue_type = ue_type;
		this.market = market;
		this.operator = operator;
		this.cell_id = cell_id;
		this.duration = duration;
		this.cause_code = cause_code;
		this.ne_version = ne_version;
		this.imsi = imsi;
		this.hier3_id = hier3_id;
		this.hier32_id = hier32_id;
		this.hier321_id = hier321_id;
	}

	public LocalDateTime getDate_time() {
		return date_time;
	}

	public void setDate_time(final LocalDateTime date_time) {
		this.date_time = date_time;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(final int event_id) {
		this.event_id = event_id;
	}

	public String getFailure_class() {
		return failure_class;
	}

	public void setFailure_class(final String failure_class) {
		this.failure_class = failure_class;
	}

	public int getUe_type() {
		return ue_type;
	}

	public void setUe_type(final int ue_type) {
		this.ue_type = ue_type;
	}

	public int getMarket() {
		return market;
	}

	public void setMarket(final int market) {
		this.market = market;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(final int operator) {
		this.operator = operator;
	}

	public int getCell_id() {
		return cell_id;
	}

	public void setCell_id(final int cell_id) {
		this.cell_id = cell_id;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(final int duration) {
		this.duration = duration;
	}

	public String getCause_code() {
		return cause_code;
	}

	public void setCause_code(final String cause_code) {
		this.cause_code = cause_code;
	}

	public String getNe_version() {
		return ne_version;
	}

	public void setNe_version(final String ne_version) {
		this.ne_version = ne_version;
	}

	public long getImsi() {
		return imsi;
	}

	public void setImsi(final long imsi) {
		this.imsi = imsi;
	}

	public long getHier3_id() {
		return hier3_id;
	}

	public void setHier3_id(final long hier3_id) {
		this.hier3_id = hier3_id;
	}

	public long getHier32_id() {
		return hier32_id;
	}

	public void setHier32_id(final long hier32_id) {
		this.hier32_id = hier32_id;
	}

	public long getHier321_id() {
		return hier321_id;
	}

	public void setHier321_id(final long hier321_id) {
		this.hier321_id = hier321_id;
	}

	public static class Builder {
		private int event_id;
		private LocalDateTime date_time;
		private String failure_class;
		private int ue_type;
		private int market;
		private int operator;
		private int cell_id;
		private int duration;
		private String cause_code;
		private String ne_version;
		private long imsi;
		private long hier3_id;
		private long hier32_id;
		private long hier321_id;

		public Builder event_id(final int event_id) {
			this.event_id = event_id;
			return this;
		}

		public Builder date_time(final LocalDateTime date_time) {
			this.date_time = date_time;
			return this;
		}

		public Builder failure_class(final String failure_class) {
			this.failure_class = failure_class;
			return this;
		}

		public Builder ue_type(final int ue_type) {
			this.ue_type = ue_type;
			return this;
		}

		public Builder market(final int market) {
			this.market = market;
			return this;
		}

		public Builder operator(final int operator) {
			this.operator = operator;
			return this;
		}

		public Builder cell_id(final int cell_id) {
			this.cell_id = cell_id;
			return this;
		}

		public Builder duration(final int duration) {
			this.duration = duration;
			return this;
		}

		public Builder cause_code(final String cause_code) {
			this.cause_code = cause_code;
			return this;
		}

		public Builder ne_version(final String ne_version) {
			this.ne_version = ne_version;
			return this;
		}

		public Builder imsi(final long imsi) {
			this.imsi = imsi;
			return this;
		}

		public Builder hier3_id(final long hier3_id) {
			this.hier3_id = hier3_id;
			return this;
		}

		public Builder hier32_id(final long hier32_id) {
			this.hier32_id = hier32_id;
			return this;
		}

		public Builder hier321_id(final long hier321_id) {
			this.hier321_id = hier321_id;
			return this;
		}

		public BaseData build() {
			return new BaseData(date_time, event_id, failure_class, ue_type, market, operator, cell_id, duration,
					cause_code, ne_version, imsi, hier3_id, hier32_id, hier321_id);
		}
	}
}
