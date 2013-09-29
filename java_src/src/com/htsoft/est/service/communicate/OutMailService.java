package com.htsoft.est.service.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
*/
import java.util.List;
import java.util.Map;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.communicate.MailBox;
import com.htsoft.est.model.communicate.OutMail;

public interface OutMailService extends BaseService<OutMail>{
	public List<OutMail> findByFolderId(Long folderId);
	public Long CountByFolderId(Long folderId);
	public Map getUidByUserId(Long userId);
	
}


