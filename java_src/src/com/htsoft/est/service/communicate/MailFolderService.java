package com.htsoft.est.service.communicate;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.communicate.MailFolder;
import com.htsoft.est.model.document.DocFolder;

public interface MailFolderService extends BaseService<MailFolder>{

	public List<MailFolder> getUserFolderByParentId(Long curUserId, Long parentId);

	public List<MailFolder> getAllUserFolderByParentId(Long userId, Long parentId);
	
	public List<MailFolder> getFolderLikePath(String path);
}


