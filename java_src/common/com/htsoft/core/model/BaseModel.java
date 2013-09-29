package com.htsoft.core.model;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.io.Serializable;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import flexjson.JSON;
/**
 * Base model
 * @author 
 *
 */
public class BaseModel implements Serializable{
	protected Log logger=LogFactory.getLog(BaseModel.class);
	private Integer version;
	@JSON(include=false)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
//	public boolean equals(Object obj) {
//		return EqualsBuilder.reflectionEquals(this, obj);
//	}
//
//	public int hashCode() {
//		return HashCodeBuilder.reflectionHashCode(this);
//	}
//
//	public String toString() {
//		return ToStringBuilder.reflectionToString(this);
//	}
	
	/**
	 * 所在主部门Id
	 */
	private Long orgId;
	/**
	 * 所在主部门的路径
	 */
	private String orgPath;
	
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}
	
}
