package com.htsoft.est.model.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * PositionSub Base Java Bean, base class for the.oa.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 
 */
public class PositionSub extends com.htsoft.core.model.BaseModel {

	protected Long mainPositionId;
	protected Long subPositionId;
	protected com.htsoft.est.model.system.Position position;


	/**
	 * Default Empty Constructor for class PositionSub
	 */
	public PositionSub () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PositionSub
	 */
	public PositionSub (
		 Long in_mainpositionid
		,Long in_subpositionid
        ) {
		this.setMainPositionId(in_mainpositionid);
		this.setSubPositionId(in_subpositionid);
    }
	
	public Long getMainPositionId() {
		return mainPositionId;
	}

	public void setMainPositionId(Long mainPositionId) {
	    if (mainPositionId==null) {
	    	position = null;
	    } else {
	        position = new com.htsoft.est.model.system.Position(mainPositionId);
	        position.setVersion(new Integer(0));//set a version to cheat hibernate only
	    }
		this.mainPositionId = mainPositionId;
	}

	public Long getSubPositionId() {
		return subPositionId;
	}

	public void setSubPositionId(Long subPositionId) {
		this.subPositionId = subPositionId;
	}
	
	public com.htsoft.est.model.system.Position getPosition () {
		return position;
	}	
	
	public void setPosition (com.htsoft.est.model.system.Position in_position) {
		this.position = in_position;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PositionSub)) {
			return false;
		}
		PositionSub rhs = (PositionSub) object;
		return new EqualsBuilder()
				.append(this.mainPositionId, rhs.mainPositionId)
				.append(this.subPositionId, rhs.subPositionId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.mainPositionId) 
				.append(this.subPositionId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("mainpositionid", this.mainPositionId) 
				.append("subpositionid", this.subPositionId) 
				.toString();
	}



	/**
	 * Return the primary key as a hashmap
	 */
	public Map getPrimaryKeyMap() {
		
		Map pkMap = new HashMap();
	    pkMap.put("mainpositionid",   this.getMainPositionId());
	    pkMap.put("subpositionid",   this.getSubPositionId());
		return pkMap;
	}

	/**
	 * Return the primary key String as key value pairs
	 */
	public String getPrimaryKeyString() {
		
		java.lang.StringBuffer pkeyString = new java.lang.StringBuffer("[");
	    pkeyString.append("mainpositionid=");
	    pkeyString.append(this.getMainPositionId());
		pkeyString.append(",");
	    pkeyString.append("subpositionid=");
	    pkeyString.append(this.getSubPositionId());
	    pkeyString.append("]");
		return pkeyString.toString();
	}
}
