package com.htsoft.core.web.listener;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.context.ContextLoaderListener;

import com.htsoft.core.util.AppUtil;

public class StartupListener extends ContextLoaderListener {
	
	private static Log logger=LogFactory.getLog(StartupListener.class);
	
	public void contextInitialized(ServletContextEvent event) {

		super.contextInitialized(event);
		//初始化应用程序工具类
		AppUtil.init(event.getServletContext());
		
		//是否同步菜单
		boolean isAynMenu=AppUtil.getIsSynMenu();
		
		if(isAynMenu){
			AppUtil.synMenu();
		}
	}
}
