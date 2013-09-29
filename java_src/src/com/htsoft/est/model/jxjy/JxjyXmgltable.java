package com.htsoft.est.model.jxjy;

/**
 * JxjyXmgltable entity. @author MyEclipse Persistence Tools
 */

public class JxjyXmgltable implements java.io.Serializable {

	// Fields

	private Long xmId;
	private Long ktId;
	private String mc;
	private String xmmc;
	private String hdfs;
	private String shfs;
	private Long xmlb;
	private String zxf;
	private String zxs;
	private String jbsj;
	private String tjsj;
	private String xflb;
	private String zbdw;
	private String zt;

	// Constructors

	/** default constructor */
	public JxjyXmgltable() {
	}

	/** minimal constructor */
	public JxjyXmgltable(Long xmId) {
		this.xmId = xmId;
	}

	/** full constructor */
	public JxjyXmgltable(Long xmId, Long ktId, String mc, String xmmc,
			String hdfs, String shfs, Long xmlb, String zxf, String zxs,
			String jbsj, String tjsj, String xflb, String zbdw, String zt) {
		this.xmId = xmId;
		this.ktId = ktId;
		this.mc = mc;
		this.xmmc = xmmc;
		this.hdfs = hdfs;
		this.shfs = shfs;
		this.xmlb = xmlb;
		this.zxf = zxf;
		this.zxs = zxs;
		this.jbsj = jbsj;
		this.tjsj = tjsj;
		this.xflb = xflb;
		this.zbdw = zbdw;
		this.zt = zt;
	}

	// Property accessors

	public Long getXmId() {
		return this.xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
	}

	public Long getKtId() {
		return this.ktId;
	}

	public void setKtId(Long ktId) {
		this.ktId = ktId;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getXmmc() {
		return this.xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getHdfs() {
		return this.hdfs;
	}

	public void setHdfs(String hdfs) {
		this.hdfs = hdfs;
	}

	public String getShfs() {
		return this.shfs;
	}

	public void setShfs(String shfs) {
		this.shfs = shfs;
	}

	public Long getXmlb() {
		return this.xmlb;
	}

	public void setXmlb(Long xmlb) {
		this.xmlb = xmlb;
	}

	public String getZxf() {
		return this.zxf;
	}

	public void setZxf(String zxf) {
		this.zxf = zxf;
	}

	public String getZxs() {
		return this.zxs;
	}

	public void setZxs(String zxs) {
		this.zxs = zxs;
	}

	public String getJbsj() {
		return this.jbsj;
	}

	public void setJbsj(String jbsj) {
		this.jbsj = jbsj;
	}

	public String getTjsj() {
		return this.tjsj;
	}

	public void setTjsj(String tjsj) {
		this.tjsj = tjsj;
	}

	public String getXflb() {
		return this.xflb;
	}

	public void setXflb(String xflb) {
		this.xflb = xflb;
	}

	public String getZbdw() {
		return this.zbdw;
	}

	public void setZbdw(String zbdw) {
		this.zbdw = zbdw;
	}

	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

}