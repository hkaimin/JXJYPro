package com.htsoft.est.model.jxjy;

/**
 * JxjyDddztable entity. @author MyEclipse Persistence Tools
 */

public class JxjyDddztable implements java.io.Serializable {

	// Fields

	private Long id;
	private String tjlx;
	private String mx;

	// Constructors

	/** default constructor */
	public JxjyDddztable() {
	}

	/** minimal constructor */
	public JxjyDddztable(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxjyDddztable(Long id, String tjlx, String mx) {
		this.id = id;
		this.tjlx = tjlx;
		this.mx = mx;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTjlx() {
		return this.tjlx;
	}

	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}

	public String getMx() {
		return this.mx;
	}

	public void setMx(String mx) {
		this.mx = mx;
	}

}