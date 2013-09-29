package com.htsoft.est.model.jxjy;

/**
 * JxjyXfmctable entity. @author MyEclipse Persistence Tools
 */

public class JxjyXfmctable implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xflbid;
	private String mc;

	// Constructors

	/** default constructor */
	public JxjyXfmctable() {
	}

	/** minimal constructor */
	public JxjyXfmctable(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxjyXfmctable(Long id, Long xflbid, String mc) {
		this.id = id;
		this.xflbid = xflbid;
		this.mc = mc;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getXflbid() {
		return this.xflbid;
	}

	public void setXflbid(Long xflbid) {
		this.xflbid = xflbid;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

}