package com.htsoft.est.model.jxjy;

/**
 * JxjyXflbtable entity. @author MyEclipse Persistence Tools
 */

public class JxjyXflbtable implements java.io.Serializable {

	// Fields

	private Long id;
	private Long xflbid;
	private String mc;
	private String xfmc;
	private Long xfmcid;
	private String sfsh;

	// Constructors

	/** default constructor */
	public JxjyXflbtable() {
	}

	/** minimal constructor */
	public JxjyXflbtable(Long id, Long xflbid) {
		this.id = id;
		this.xflbid = xflbid;
	}

	/** full constructor */
	public JxjyXflbtable(Long id, Long xflbid, String mc, String xfmc,
			Long xfmcid, String sfsh) {
		this.id = id;
		this.xflbid = xflbid;
		this.mc = mc;
		this.xfmc = xfmc;
		this.xfmcid = xfmcid;
		this.sfsh = sfsh;
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

	public String getXfmc() {
		return this.xfmc;
	}

	public void setXfmc(String xfmc) {
		this.xfmc = xfmc;
	}

	public Long getXfmcid() {
		return this.xfmcid;
	}

	public void setXfmcid(Long xfmcid) {
		this.xfmcid = xfmcid;
	}

	public String getSfsh() {
		return this.sfsh;
	}

	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}

}