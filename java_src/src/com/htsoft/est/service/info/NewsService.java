package com.htsoft.est.service.info;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.util.Date;
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.info.News;

public interface NewsService extends BaseService<News>{
	public List<News> findByTypeId(Long typeId,PagingBean pb);

	public List<News> findBySearch(Short isNotice,String searchContent,PagingBean pb);
	/**
	 * 查找图片新闻
	 * @param pb
	 * @return
	 */
	public List<News> findImageNews(Long sectionId,PagingBean pb);
}
