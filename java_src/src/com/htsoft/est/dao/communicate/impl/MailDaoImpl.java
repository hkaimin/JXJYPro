package com.htsoft.est.dao.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.communicate.MailDao;
import com.htsoft.est.model.communicate.Mail;

public class MailDaoImpl extends BaseDaoImpl<Mail> implements MailDao{

	public MailDaoImpl() {
		super(Mail.class);
	}

}