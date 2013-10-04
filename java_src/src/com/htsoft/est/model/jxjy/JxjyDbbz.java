package com.htsoft.est.model.jxjy;

/**
 * JxjyDbbz entity. @author MyEclipse Persistence Tools
 */

public class JxjyDbbz implements java.io.Serializable {

	// Fields

	private Long id;
	private String yylx;
	private String zc;
	private String khnr;
	private Long xfz;
	private Long zgf;
	private String tjms;
	private Long khnrId;
	private String tjlx;
	private String nf;

	// Constructors

	/** default constructor */
	public JxjyDbbz() {
	}

	/** minimal constructor */
	public JxjyDbbz(Long id) {
		this.id = id;
	}

	/** full constructor */
	public JxjyDbbz(Long id, String yylx, String zc, String khnr, Long xfz,
			Long zgf, String tjms) {
		this.id = id;
		this.yylx = yylx;
		this.zc = zc;
		this.khnr = khnr;
		this.xfz = xfz;
		this.zgf = zgf;
		this.tjms = tjms;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYylx() {
		return this.yylx;
	}

	public void setYylx(String yylx) {
		this.yylx = yylx;
	}

	public String getZc() {
		return this.zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public String getKhnr() {
		return this.khnr;
	}

	public void setKhnr(String khnr) {
		this.khnr = khnr;
	}

	public Long getXfz() {
		return this.xfz;
	}

	public void setXfz(Long xfz) {
		this.xfz = xfz;
	}

	public Long getZgf() {
		return this.zgf;
	}

	public void setZgf(Long zgf) {
		this.zgf = zgf;
	}

	public String getTjms() {
		return this.tjms;
	}

	public void setTjms(String tjms) {
		this.tjms = tjms;
	}

	public Long getKhnrId() {
		return khnrId;
	}

	public void setKhnrId(Long khnrId) {
		this.khnrId = khnrId;
	}

	public String getTjlx() {
		return tjlx;
	}

	public void setTjlx(String tjlx) {
		this.tjlx = tjlx;
	}

	public String getNf() {
		return nf;
	}

	public void setNf(String nf) {
		this.nf = nf;
	}
	

}