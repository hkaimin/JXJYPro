package com.htsoft.est.service.info.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.info.NewsCommentDao;
import com.htsoft.est.model.info.NewsComment;
import com.htsoft.est.service.info.NewsCommentService;

public class NewsCommentServiceImpl extends BaseServiceImpl<NewsComment> implements NewsCommentService{
	private NewsCommentDao dao;
	
	public NewsCommentServiceImpl(NewsCommentDao dao) {
		super(dao);
		this.dao=dao;
	}

}