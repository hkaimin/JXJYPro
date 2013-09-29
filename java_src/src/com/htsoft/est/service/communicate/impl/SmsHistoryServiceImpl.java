package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.communicate.SmsHistoryDao;
import com.htsoft.est.model.communicate.SmsHistory;
import com.htsoft.est.service.communicate.SmsHistoryService;

public class SmsHistoryServiceImpl extends BaseServiceImpl<SmsHistory> implements SmsHistoryService{
	private SmsHistoryDao dao;
	
	public SmsHistoryServiceImpl(SmsHistoryDao dao) {
		super(dao);
		this.dao=dao;
	}

}