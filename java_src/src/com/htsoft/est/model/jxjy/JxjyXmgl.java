package com.htsoft.est.model.jxjy;

/**
 * JxjyXmgl entity. @author MyEclipse Persistence Tools
 */

public class JxjyXmgl implements java.io.Serializable {

	// Fields

	private Long xmId;
	private Long xflbid;
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
	private Long zbbwid;
	private Long yysh;
	private Long xmbh;

	// Constructors

	/** default constructor */
	public JxjyXmgl() {
	}

	/** minimal constructor */
	public JxjyXmgl(Long xmId) {
		this.xmId = xmId;
	}

	/** full constructor */
	public JxjyXmgl(Long xmId, Long xflbid, String mc, String xmmc,
			String hdfs, String shfs, Long xmlb, String zxf, String zxs,
			String jbsj, String tjsj, String xflb, String zbdw, String zt,
			Long zbbwid, Long yysh, Long xmbh) {
		this.xmId = xmId;
		this.xflbid = xflbid;
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
		this.zbbwid = zbbwid;
		this.yysh = yysh;
		this.xmbh = xmbh;
	}

	// Property accessors

	public Long getXmId() {
		return this.xmId;
	}

	public void setXmId(Long xmId) {
		this.xmId = xmId;
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

	public Long getZbbwid() {
		return this.zbbwid;
	}

	public void setZbbwid(Long zbbwid) {
		this.zbbwid = zbbwid;
	}

	public Long getYysh() {
		return this.yysh;
	}

	public void setYysh(Long yysh) {
		this.yysh = yysh;
	}

	public Long getXmbh() {
		return this.xmbh;
	}

	public void setXmbh(Long xmbh) {
		this.xmbh = xmbh;
	}

}