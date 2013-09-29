package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.UserPositionDao;
import com.htsoft.est.model.system.UserPosition;

@SuppressWarnings("unchecked")
public class UserPositionDaoImpl extends BaseDaoImpl<UserPosition> implements UserPositionDao{

	public UserPositionDaoImpl() {
		super(UserPosition.class);
	}
	/**
	 * 查找某一用户的所有职位
	 * @param userId
	 * @return
	 */
	public List<UserPosition> getByUserPos(Long userId){
		String hql="from UserPosition up where up.appUser.userId=?";
		return (List<UserPosition>)findByHql(hql,new Object[]{userId});
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.system.UserPositionDao#getByPosId(java.lang.Long)
	 */
	public List getByPosId(Long posId){
		String hql="from UserPosition up where up.position.posId=?";
		return findByHql(hql,new Object[]{posId});
	}

}