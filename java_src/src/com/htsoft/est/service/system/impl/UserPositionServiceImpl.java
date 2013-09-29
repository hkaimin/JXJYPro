package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.ArrayList;
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.system.UserPositionDao;
import com.htsoft.est.model.system.UserPosition;
import com.htsoft.est.service.system.UserPositionService;

public class UserPositionServiceImpl extends BaseServiceImpl<UserPosition> implements UserPositionService{
	@SuppressWarnings("unused")
	private UserPositionDao dao;
	
	public UserPositionServiceImpl(UserPositionDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 查找某一用户的所有职位
	 * @param userId
	 * @return
	 */
	public List<UserPosition> getByUserPos(Long userId){
		return dao.getByUserPos(userId);
	}
	
	@Override
	public List<Long> getUserIdsByPosId(Long posId) {
		List<UserPosition> list= dao.getByPosId(posId);
		List<Long> userIds=new ArrayList<Long>();
		for(UserPosition up:list){
			userIds.add(up.getAppUser().getUserId());
		}
		return userIds;
	}

}