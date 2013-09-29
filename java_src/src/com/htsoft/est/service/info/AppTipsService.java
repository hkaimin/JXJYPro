package com.htsoft.est.service.info;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.info.AppTips;

public interface AppTipsService extends BaseService<AppTips>{
	/**
	 * 根据名称去查找TIP
	 */
	public AppTips findByName(String name);
}


