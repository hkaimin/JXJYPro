package com.htsoft.est.dao.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.communicate.SmsHistoryDao;
import com.htsoft.est.model.communicate.SmsHistory;

public class SmsHistoryDaoImpl extends BaseDaoImpl<SmsHistory> implements SmsHistoryDao{

	public SmsHistoryDaoImpl() {
		super(SmsHistory.class);
	}

}