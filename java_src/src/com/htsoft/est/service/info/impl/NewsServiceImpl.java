package com.htsoft.est.service.info.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.Date;
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.info.NewsDao;
import com.htsoft.est.model.info.News;
import com.htsoft.est.service.info.NewsService;

public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService{
	private NewsDao newsDao;
	
	public NewsServiceImpl(NewsDao dao) {
		super(dao);
		this.newsDao=dao;
	}

	@Override
	public List<News> findByTypeId(Long typeId,PagingBean pb) {
		return newsDao.findByTypeId(typeId,pb);
	}

	@Override
	public List<News> findBySearch(Short isNotice,String searchContent,PagingBean pb) {
		return newsDao.findBySearch(isNotice,searchContent,pb);
	}

	@Override
	public List<News> findImageNews(Long sectionId,PagingBean pb) {
		return newsDao.findImageNews(sectionId,pb);
	}


}
