package com.htsoft.est.model.jxjy;

/**
 * JxjyDbry entity. @author MyEclipse Persistence Tools
 */

public class JxjyDbry implements java.io.Serializable {

	// Fields  

	private Long rybh;
	private String xm;
	private String db;
	private String nf;

	// Constructors

	/** default constructor */
	public JxjyDbry() {
	}

	/** minimal constructor */
	public JxjyDbry(Long rybh) {
		this.rybh = rybh;
	}

	/** full constructor */
	public JxjyDbry(Long rybh, String xm, String db, String nf) {
		this.rybh = rybh;
		this.xm = xm;
		this.db = db;
		this.nf = nf;
	}

	// Property accessors

	public Long getRybh() {
		return this.rybh;
	}

	public void setRybh(Long rybh) {
		this.rybh = rybh;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getDb() {
		return this.db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getNf() {
		return this.nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}

}