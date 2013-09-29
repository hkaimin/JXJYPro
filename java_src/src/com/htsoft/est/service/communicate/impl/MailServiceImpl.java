package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.communicate.MailDao;
import com.htsoft.est.model.communicate.Mail;
import com.htsoft.est.service.communicate.MailService;

public class MailServiceImpl extends BaseServiceImpl<Mail> implements MailService{
	private MailDao dao;
	
	public MailServiceImpl(MailDao dao) {
		super(dao);
		this.dao=dao;
	}

}