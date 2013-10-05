package com.htsoft.est.model.jxjy;

/**
 * JxjySfbzmx entity. @author MyEclipse Persistence Tools
 */

public class JxjySfbzmx implements java.io.Serializable {

	// Fields  

	private Long id;
	private Long sfbzszid;
	private Long xmId;
	private String xmmc;
	private Long sz;
	private String dw;
	private Long xf;
	private Long zgz;

	// Constructors

	/** default constructor */
	public JxjySfbzmx() {
	}

	/** minimal constructor */
	public JxjySfbzmx(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxjySfbzmx(Long id, Long xmId, String xmmc, Long sz, String dw,
			Long xf, Long zgz) {
		this.id = id;
		this.xmId = xmId;
		this.xmmc = xmmc;
		this.sz = sz;
		this.dw = dw;
		this.xf = xf;
		this.zgz = zgz;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getXmId() {
		return this.xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
	}

	public String getXmmc() {
		return this.xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public Long getSz() {
		return this.sz;
	}

	public void setSz(Long sz) {
		this.sz = sz;
	}

	public String getDw() {
		return this.dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public Long getXf() {
		return this.xf;
	}

	public void setXf(Long xf) {
		this.xf = xf;
	}

	public Long getZgz() {
		return this.zgz;
	}

	public void setZgz(Long zgz) {
		this.zgz = zgz;
	}

	public Long getSfbzszid() {
		return sfbzszid;
	}

	public void setSfbzszid(Long sfbzszid) {
		this.sfbzszid = sfbzszid;
	}

}