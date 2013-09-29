package com.htsoft.est.service.communicate.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.communicate.PhoneBookDao;
import com.htsoft.est.model.communicate.PhoneBook;
import com.htsoft.est.service.communicate.PhoneBookService;

public class PhoneBookServiceImpl extends BaseServiceImpl<PhoneBook> implements PhoneBookService{
	private PhoneBookDao dao;
	
	public PhoneBookServiceImpl(PhoneBookDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PhoneBook> sharedPhoneBooks(String fullname, String ownerName,
			PagingBean pb) {
		return dao.sharedPhoneBooks(fullname, ownerName, pb);
	}

}