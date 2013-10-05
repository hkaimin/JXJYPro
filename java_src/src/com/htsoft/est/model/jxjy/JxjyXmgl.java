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
	private String xmbh;
	
	//vo
	private String xflbName;
	private String jbsj_start;
	private String jbsj_end;
	
	//--vo
	private Long ktidVo;
	private String ktmcVo;
	private String xfVo;
	private String xsVo;
	private String skddVo;
	private String sksjVo;
	private String bmqkVo;//报名情况

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
			Long zbbwid, Long yysh, String xmbh) {
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

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}



	public Long getKtidVo() {
		return ktidVo;
	}

	public void setKtidVo(Long ktidVo) {
		this.ktidVo = ktidVo;
	}

	public String getKtmcVo() {
		return ktmcVo;
	}

	public void setKtmcVo(String ktmcVo) {
		this.ktmcVo = ktmcVo;
	}

	public String getXfVo() {
		return xfVo;
	}

	public void setXfVo(String xfVo) {
		this.xfVo = xfVo;
	}

	public String getXsVo() {
		return xsVo;
	}

	public void setXsVo(String xsVo) {
		this.xsVo = xsVo;
	}

	public String getSkddVo() {
		return skddVo;
	}

	public void setSkddVo(String skddVo) {
		this.skddVo = skddVo;
	}

	public String getSksjVo() {
		return sksjVo;
	}

	public void setSksjVo(String sksjVo) {
		this.sksjVo = sksjVo;
	}

	public String getBmqkVo() {
		return bmqkVo;
	}

	public void setBmqkVo(String bmqkVo) {
		this.bmqkVo = bmqkVo;
	}

	public String getXflbName() {
		return xflbName;
	}

	public void setXflbName(String xflbName) {
		this.xflbName = xflbName;
	}

	public String getJbsj_start() {
		return jbsj_start;
	}

	public void setJbsj_start(String jbsjStart) {
		jbsj_start = jbsjStart;
	}

	public String getJbsj_end() {
		return jbsj_end;
	}

	public void setJbsj_end(String jbsjEnd) {
		jbsj_end = jbsjEnd;
	}
	

}