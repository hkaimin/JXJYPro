package com.htsoft.est.dao.info.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.info.NewsCommentDao;
import com.htsoft.est.model.info.NewsComment;

public class NewsCommentDaoImpl extends BaseDaoImpl<NewsComment> implements NewsCommentDao{

	public NewsCommentDaoImpl() {
		super(NewsComment.class);
	}

}