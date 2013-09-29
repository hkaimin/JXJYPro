package com.htsoft.est.dao.system.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.ArrayList;
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.system.UserSubDao;
import com.htsoft.est.model.system.UserSub;

public class UserSubDaoImpl extends BaseDaoImpl<UserSub> implements UserSubDao{

	public UserSubDaoImpl() {
		super(UserSub.class);
	}

	@Override
	public List<Long> upUser(Long userId) {
		String hql="from UserSub vo where vo.subAppUser.userId=?";
		Object[] objs={userId};
		List<UserSub> list=findByHql(hql, objs);
		List<Long> idList=new ArrayList<Long>();
		for(UserSub sb:list){
			idList.add(sb.getUserId());
		}
		return idList;
	}

	@Override
	public List<Long> subUsers(Long userId) {
		String hql="from UserSub vo where vo.userId=?";
		Object[] objs={userId};
		List<UserSub> list=findByHql(hql, objs);
		List<Long> idList=new ArrayList<Long>();
		for(UserSub sb:list){
			idList.add(sb.getSubAppUser().getUserId());
		}
		return idList;
	}

}