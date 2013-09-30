package com.htsoft.est.model.jxjy;

/**
 * JxjyXfmc entity. @author MyEclipse Persistence Tools
 */

public class JxjyXfmc implements java.io.Serializable {

	// Fields  

	private Long id;
	private String mc;

	// Constructors

	/** default constructor */
	public JxjyXfmc() {
	}

	/** minimal constructor */
	public JxjyXfmc(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxjyXfmc(Long id, String mc) {
		this.id = id;
		this.mc = mc;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

}