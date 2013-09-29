package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.UserPosition;

public interface UserPositionService extends BaseService<UserPosition>{
	/**
	 * 查找某一用户的所有职位
	 * @param userId
	 * @return
	 */
	public List<UserPosition> getByUserPos(Long userId);
	/**
	 * 取得某个职位的所有用户IDs
	 * @param posId
	 * @return
	 */
	public List<Long> getUserIdsByPosId(Long posId);
}


