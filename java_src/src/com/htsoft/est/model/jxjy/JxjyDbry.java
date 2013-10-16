package com.htsoft.est.model.jxjy;

/**
 * JxjyDbry entity. @author MyEclipse Persistence Tools
 */

public class JxjyDbry implements java.io.Serializable {

	// Fields  

	private String rybh;
	private String xm;
	private String db;
	private String nf;
	
	private Long dbId;
	
	private String type;

	// Constructors

	/** default constructor */
	public JxjyDbry() {
	}

	/** full constructor */
	public JxjyDbry(String rybh, String xm, String db, String nf) {
		this.rybh = rybh;
		this.xm = xm;
		this.db = db;
		this.nf = nf;
	}

	// Property accessors
	
	public String getXm() {
		return this.xm;
	}

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
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

	public Long getDbId() {
		return dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}