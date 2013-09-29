package com.htsoft.est.service.communicate;

/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Limited company.
 */
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.communicate.OutMailUserSeting;

/**
 * @description 外部邮箱管理
 * @class OutMailUserSetingService
 * 
 */
public interface OutMailUserSetingService extends BaseService<OutMailUserSeting> {

	OutMailUserSeting getByLoginId(Long loginid);
	
    public List findByUserAll();
	
    public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb);
}
