package com.htsoft.est.model.jxjy;

/**
 * JxjySfbzsz entity. @author MyEclipse Persistence Tools
 */

public class JxjySfbzsz implements java.io.Serializable {

	// Fields  

	private Long id;
	private Long xflbid;
	private Long xmId;
	private String mx;
	private String xmmc;

	private String lbmcVo;
	
	// Constructors

	/** default constructor */
	public JxjySfbzsz() {
	}

	/** minimal constructor */
	public JxjySfbzsz(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxjySfbzsz(Long id, Long xmId, String mx) {
		this.id = id;
		this.xmId = xmId;
		this.mx = mx;
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

	public String getMx() {
		return this.mx;
	}

	public void setMx(String mx) {
		this.mx = mx;
	}


	public String getLbmcVo() {
		return lbmcVo;
	}

	public void setLbmcVo(String lbmcVo) {
		this.lbmcVo = lbmcVo;
	}

	public Long getXflbid() {
		return xflbid;
	}

	public void setXflbid(Long xflbid) {
		this.xflbid = xflbid;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}


	
	

}