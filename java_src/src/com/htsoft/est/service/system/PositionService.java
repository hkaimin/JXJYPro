package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.system.Position;

public interface PositionService extends BaseService<Position>{
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Position> getByParent(Long parentId);
	
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Position> getByPath(String path);
	/**
	 * 
	 * @param posId
	 */
	public void delPositionCascade(Long posId);
	
	/**
	 * 根据某岗位取得相应岗位
	 * @param 
	 * posId 当前岗位
	 * posSupId 上级岗位
	 * lvFlag
	 * 		1 同级岗位 
	 * 		2 同级及下属
	 * 		3 下属
	 * @return
	 */
	public List<Position> getUnderling(Long posId, Long posSupId, int lvFlag, PagingBean pb);

	List<Position> getUnderling(Long posId, PagingBean pb);
}


