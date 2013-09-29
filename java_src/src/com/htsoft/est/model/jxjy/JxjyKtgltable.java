package com.htsoft.est.model.jxjy;

/**
 * JxjyKtgltable entity. @author MyEclipse Persistence Tools
 */

public class JxjyKtgltable implements java.io.Serializable {

	// Fields

	private Long ktId;
	private String ktmc;
	private String jsmc;
	private String jszc;
	private String xf;
	private String xs;
	private String skdd;
	private String sksj;

	// Constructors

	/** default constructor */
	public JxjyKtgltable() {
	}

	/** minimal constructor */
	public JxjyKtgltable(Long ktId) {
		this.ktId = ktId;
	}

	/** full constructor */
	public JxjyKtgltable(Long ktId, String ktmc, String jsmc, String jszc,
			String xf, String xs, String skdd, String sksj) {
		this.ktId = ktId;
		this.ktmc = ktmc;
		this.jsmc = jsmc;
		this.jszc = jszc;
		this.xf = xf;
		this.xs = xs;
		this.skdd = skdd;
		this.sksj = sksj;
	}

	// Property accessors

	public Long getKtId() {
		return this.ktId;
	}

	public void setKtId(Long ktId) {
		this.ktId = ktId;
	}

	public String getKtmc() {
		return this.ktmc;
	}

	public void setKtmc(String ktmc) {
		this.ktmc = ktmc;
	}

	public String getJsmc() {
		return this.jsmc;
	}

	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}

	public String getJszc() {
		return this.jszc;
	}

	public void setJszc(String jszc) {
		this.jszc = jszc;
	}

	public String getXf() {
		return this.xf;
	}

	public void setXf(String xf) {
		this.xf = xf;
	}

	public String getXs() {
		return this.xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getSkdd() {
		return this.skdd;
	}

	public void setSkdd(String skdd) {
		this.skdd = skdd;
	}

	public String getSksj() {
		return this.sksj;
	}

	public void setSksj(String sksj) {
		this.sksj = sksj;
	}

}