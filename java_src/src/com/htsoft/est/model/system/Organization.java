package com.htsoft.est.model.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * Organization Base Java Bean, base class for the.est.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class Organization extends com.htsoft.core.model.BaseModel {
	
	public final static Long PUBLIC_COMPANY_ID=new Long(-1);
	
	/**
	 * 集团
	 */
	public final static Short ORG_TYPE_GROUP=0;
	/**
	 * 公司
	 */
	public final static Short ORG_TYPE_COMPANY=1;
	/**
	 * 部门
	 */
	public final static Short ORG_TYPE_DEPARTMENT=2;
	/**
	 * 其他组织
	 */
	public final static Short ORG_TYPE_ORTHER_ORG=3;
	
	@Expose
    protected Long orgId;
	@Expose
	protected String orgName;
	@Expose
	protected String orgDesc;
	@Expose
	protected Long orgSupId;
	@Expose
	protected String path;
	@Expose
	protected Long depth;
	@Expose
	protected Short orgType;
	@Expose
	protected Long creatorId;
	@Expose
	protected java.util.Date createtime;
	@Expose
	protected Long updateId;
	@Expose
	protected java.util.Date updatetime;
	@Expose
	protected com.htsoft.est.model.system.Demension demension;
	
	protected java.util.Set appUsers = new java.util.HashSet();
	protected java.util.Set userOrgs = new java.util.HashSet();

	/**
	 * Default Empty Constructor for class Organization
	 */
	public Organization () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class Organization
	 */
	public Organization (
		 Long in_orgId
        ) {
		this.setOrgId(in_orgId);
    }

	
	public com.htsoft.est.model.system.Demension getDemension () {
		return demension;
	}	
	
	public void setDemension (com.htsoft.est.model.system.Demension in_demension) {
		this.demension = in_demension;
	}

	public java.util.Set getAppUsers () {
		return appUsers;
	}	
	
	public void setAppUsers (java.util.Set in_appUsers) {
		this.appUsers = in_appUsers;
	}

	public java.util.Set getUserOrgs () {
		return userOrgs;
	}	
	
	public void setUserOrgs (java.util.Set in_userOrgs) {
		this.userOrgs = in_userOrgs;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="ORG_ID" type="java.lang.Long" generator-class="native"
	 */
	public Long getOrgId() {
		return this.orgId;
	}
	
	/**
	 * Set the orgId
	 */	
	public void setOrgId(Long aValue) {
		this.orgId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getDemId() {
		return this.getDemension()==null?null:this.getDemension().getDemId();
	}
	
	/**
	 * Set the demId
	 */	
	public void setDemId(Long aValue) {
	    if (aValue==null) {
	    	demension = null;
	    } else if (demension == null) {
	        demension = new com.htsoft.est.model.system.Demension(aValue);
	        demension.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			demension.setDemId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="ORG_NAME" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getOrgName() {
		return this.orgName;
	}
	
	/**
	 * Set the orgName
	 * @spring.validator type="required"
	 */	
	public void setOrgName(String aValue) {
		this.orgName = aValue;
	}	
	
	

	public String getOrgDesc() {
		return orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	/**
	 * 	 * @return Long
	 * @hibernate.property column="ORG_SUP_ID" type="java.lang.Long" length="18" not-null="false" unique="false"
	 */
	public Long getOrgSupId() {
		return this.orgSupId;
	}
	
	/**
	 * Set the orgSupId
	 */	
	public void setOrgSupId(Long aValue) {
		this.orgSupId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="PATH" type="java.lang.String" length="128" not-null="false" unique="false"
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set the path
	 */	
	public void setPath(String aValue) {
		this.path = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="DEPTH" type="java.lang.Long" length="22" not-null="false" unique="false"
	 */
	public Long getDepth() {
		return this.depth;
	}
	
	/**
	 * Set the depth
	 */	
	public void setDepth(Long aValue) {
		this.depth = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="ORG_TYPE" type="java.lang.Long" length="22" not-null="false" unique="false"
	 */
	public Short getOrgType() {
		return this.orgType;
	}
	
	/**
	 * Set the orgType
	 */	
	public void setOrgType(Short aValue) {
		this.orgType = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="CREATOR_ID" type="java.lang.Long" length="18" not-null="false" unique="false"
	 */
	public Long getCreatorId() {
		return this.creatorId;
	}
	
	/**
	 * Set the creatorId
	 */	
	public void setCreatorId(Long aValue) {
		this.creatorId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="CREATETIME" type="java.util.Date" length="7" not-null="false" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="UPDATE_ID" type="java.lang.Long" length="18" not-null="false" unique="false"
	 */
	public Long getUpdateId() {
		return this.updateId;
	}
	
	/**
	 * Set the updateId
	 */	
	public void setUpdateId(Long aValue) {
		this.updateId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="UPDATETIME" type="java.util.Date" length="7" not-null="false" unique="false"
	 */
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	/**
	 * Set the updatetime
	 */	
	public void setUpdatetime(java.util.Date aValue) {
		this.updatetime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Organization)) {
			return false;
		}
		Organization rhs = (Organization) object;
		return new EqualsBuilder()
				.append(this.orgId, rhs.orgId)
						.append(this.orgName, rhs.orgName)
				.append(this.orgSupId, rhs.orgSupId)
				.append(this.path, rhs.path)
				.append(this.depth, rhs.depth)
				.append(this.orgType, rhs.orgType)
				.append(this.creatorId, rhs.creatorId)
				.append(this.createtime, rhs.createtime)
				.append(this.updateId, rhs.updateId)
				.append(this.updatetime, rhs.updatetime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orgId) 
						.append(this.orgName) 
				.append(this.orgSupId) 
				.append(this.path) 
				.append(this.depth) 
				.append(this.orgType) 
				.append(this.creatorId) 
				.append(this.createtime) 
				.append(this.updateId) 
				.append(this.updatetime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orgId", this.orgId) 
						.append("orgName", this.orgName) 
				.append("orgSupId", this.orgSupId) 
				.append("path", this.path) 
				.append("depth", this.depth) 
				.append("orgType", this.orgType) 
				.append("creatorId", this.creatorId) 
				.append("createtime", this.createtime) 
				.append("updateId", this.updateId) 
				.append("updatetime", this.updatetime) 
				.toString();
	}



}
