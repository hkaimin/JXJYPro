package com.htsoft.est.service.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.communicate.SmsMobile;

public interface SmsMobileService extends BaseService<SmsMobile>{
	public List<SmsMobile> getNeedToSend();
	public void saveSms(String userIds,String content);
	public void sendSms();
	public void sendOneSms(SmsMobile smsMobile);
}


