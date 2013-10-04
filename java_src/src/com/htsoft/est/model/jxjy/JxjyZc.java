package com.htsoft.est.model.jxjy;

/**
 * JxjyZc entity. @author MyEclipse Persistence Tools
 */

public class JxjyZc implements java.io.Serializable {

	// Fields

	private Long zcId;
	private String zcm;
	private Long fqZcId;
	private Long sd;

	// Constructors

	/** default constructor */
	public JxjyZc() {
	}

	/** minimal constructor */
	public JxjyZc(Long zcId) {
		this.zcId = zcId;
	}

	/** full constructor */
	public JxjyZc(Long zcId, String zcm, Long fqZcId, Long sd) {
		this.zcId = zcId;
		this.zcm = zcm;
		this.fqZcId = fqZcId;
		this.sd = sd;
	}

	// Property accessors

	public Long getZcId() {
		return this.zcId;
	}

	public void setZcId(Long zcId) {
		this.zcId = zcId;
	}

	public String getZcm() {
		return this.zcm;
	}

	public void setZcm(String zcm) {
		this.zcm = zcm;
	}

	public Long getFqZcId() {
		return this.fqZcId;
	}

	public void setFqZcId(Long fqZcId) {
		this.fqZcId = fqZcId;
	}

	public Long getSd() {
		return this.sd;
	}

	public void setSd(Long sd) {
		this.sd = sd;
	}

}