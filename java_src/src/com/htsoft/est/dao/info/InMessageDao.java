package com.htsoft.est.dao.info;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.Date;
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.info.InMessage;
import com.htsoft.est.model.info.ShortMessage;

public interface InMessageDao extends BaseDao<InMessage> {

	public InMessage findByRead(Long userId);
	public Integer findByReadFlag(Long userId);
	public List<InMessage> findAll(Long userId,PagingBean pb);
	public List<InMessage> findByShortMessage(ShortMessage shortMessage,PagingBean pb);
	public List findByUser(Long userId,PagingBean pb);
	public List findByUser(Long userId);
    /**
     * 查询发送出去的信息
     * @param userId
     * @param inMessage
     * @param shortMessage
     * @param from
     * @param to
     * @param pb
     * @return
     */
	public List searchInMessage(Long userId,InMessage inMessage,ShortMessage shortMessage,Date from,Date to,PagingBean pb);
	/**
	 * 查找最新的一条信息
	 * @param userId
	 * @return
	 */
	public InMessage findLatest(Long userId);
}
