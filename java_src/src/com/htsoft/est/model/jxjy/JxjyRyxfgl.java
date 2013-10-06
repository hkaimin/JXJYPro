package com.htsoft.est.model.jxjy;

/**
 * JxjyRyxfgl entity. @author MyEclipse Persistence Tools
 */

public class JxjyRyxfgl implements java.io.Serializable {

	// Fields  
    private Long is_commit;
	private Long id;
	private Long rybh;
	private Long ktId;
	private String xm;
	private String rq;
	private String kt;
	private String xflx;
	private String xflb;
	private String xk;
	private String hdxs;
	private String xf;
	private String xs;
	private String sfdw;
	private String shzt;
	private String bz;
	private String rsjsh;
	private String zc;
	private String xw;
	
	private Long xflbid;

	// Constructors

	/** default constructor */
	public JxjyRyxfgl() {
	}

	/** minimal constructor */
	public JxjyRyxfgl(Long id, Long rybh) {
		this.id = id;
		this.rybh = rybh;
	}

	/** full constructor */
	public JxjyRyxfgl(Long id, Long rybh, Long ktId, String xm, String rq,
			String kt, String xflx, String xflb, String xk, String hdxs,
			String xf, String xs, String sfdw, String shzt, String bz,
			String rsjsh, String zc, String xw) {
		this.id = id;
		this.rybh = rybh;
		this.ktId = ktId;
		this.xm = xm;
		this.rq = rq;
		this.kt = kt;
		this.xflx = xflx;
		this.xflb = xflb;
		this.xk = xk;
		this.hdxs = hdxs;
		this.xf = xf;
		this.xs = xs;
		this.sfdw = sfdw;
		this.shzt = shzt;
		this.bz = bz;
		this.rsjsh = rsjsh;
		this.zc = zc;
		this.xw = xw;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRybh() {
		return this.rybh;
	}

	public void setRybh(Long rybh) {
		this.rybh = rybh;
	}

	public Long getKtId() {
		return this.ktId;
	}

	public void setKtId(Long ktId) {
		this.ktId = ktId;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getRq() {
		return this.rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getKt() {
		return this.kt;
	}

	public void setKt(String kt) {
		this.kt = kt;
	}

	public String getXflx() {
		return this.xflx;
	}

	public void setXflx(String xflx) {
		this.xflx = xflx;
	}

	public String getXflb() {
		return this.xflb;
	}

	public void setXflb(String xflb) {
		this.xflb = xflb;
	}

	public String getXk() {
		return this.xk;
	}

	public void setXk(String xk) {
		this.xk = xk;
	}

	public String getHdxs() {
		return this.hdxs;
	}

	public void setHdxs(String hdxs) {
		this.hdxs = hdxs;
	}

	public String getXf() {
		return this.xf;
	}

	public void setXf(String xf) {
		this.xf = xf;
	}

	public String getXs() {
		return this.xs;
	}

	public void setXs(String xs) {
		this.xs = xs;
	}

	public String getSfdw() {
		return this.sfdw;
	}

	public void setSfdw(String sfdw) {
		this.sfdw = sfdw;
	}

	public String getShzt() {
		return this.shzt;
	}

	public void setShzt(String shzt) {
		this.shzt = shzt;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getRsjsh() {
		return this.rsjsh;
	}

	public void setRsjsh(String rsjsh) {
		this.rsjsh = rsjsh;
	}

	public String getZc() {
		return this.zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public String getXw() {
		return this.xw;
	}

	public void setXw(String xw) {
		this.xw = xw;
	}

	public Long getIs_commit() {
		return is_commit;
	}

	public void setIs_commit(Long isCommit) {
		is_commit = isCommit;
	}

	public Long getXflbid() {
		return xflbid;
	}

	public void setXflbid(Long xflbid) {
		this.xflbid = xflbid;
	}
	
}