package com.htsoft.est.dao.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.communicate.OutMailDao;
import com.htsoft.est.model.communicate.MailBox;
import com.htsoft.est.model.communicate.OutMail;

public class OutMailDaoImpl extends BaseDaoImpl<OutMail> implements OutMailDao{

	public OutMailDaoImpl() {
		super(OutMail.class);
	}
	public List<OutMail> findByFolderId(Long folderId){
		String hql = "from OutMail where folderId = ?";
		return findByHql(hql, new Object[]{folderId});
	}
	
	@Override
	public Long CountByFolderId(Long folderId) {
		String hql = "select count(*) from OutMail where folderId ="+folderId;
		return (Long)getHibernateTemplate().find(hql).get(0);
	}
	@Override
	public Map getUidByUserId(Long userId){
		String hql = "select om.uid from OutMail om where om.userId ="+userId;
		List<String> uidList= getHibernateTemplate().find(hql);
		Map uidMap = new HashMap();
		for(String uid:uidList){
			uidMap.put(uid, "Y");
		}
		return uidMap;
	}
	
}