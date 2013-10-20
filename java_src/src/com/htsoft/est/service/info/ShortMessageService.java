package com.htsoft.est.service.info;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.Date;
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.info.ShortMessage;
import com.htsoft.est.model.system.AppUser;

public interface ShortMessageService extends BaseService<ShortMessage> {

	public List<ShortMessage> findAll(Long userId,PagingBean pb);
	public List<ShortMessage> findByUser(Long userId);
	public List searchShortMessage(Long userId,ShortMessage shortMessage,Date from,Date to,PagingBean pb,Short readFlag);
	
	public ShortMessage save (Long senderId,String receiveIds,String content,Short msgType);
	
	/**
	 * 发送短信息给人员
	 * @param users
	 * @param content
	 */
	public void sendMesToUser(List<AppUser> users, String content);
}
