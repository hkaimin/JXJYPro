package com.htsoft.est.service.system.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.dao.system.PositionDao;
import com.htsoft.est.model.system.Position;
import com.htsoft.est.service.system.PositionService;

public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService{

	private PositionDao dao;
	
	public PositionServiceImpl(PositionDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Position> getByParent(Long parentId){
		return dao.getByParent(parentId);
	}
	
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Position> getByPath(String path){
		return dao.getByPath(path);
	}
	
	/**
	 * 删除某个岗位及其下属岗位
	 * @param posId
	 */
	public void delPositionCascade(Long posId){
		Position position=get(posId);
		evict(position);
		List<Position> listPos=getByPath(position.getPath());
		for(Position pos:listPos){
			remove(pos);
		}
	}
	
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
	public List<Position> getUnderling(Long posId, Long posSupId, int lvFlag, PagingBean pb) {
		return dao.getUnderling(posId, posSupId, lvFlag, pb);
	}

	@Override
	public List<Position> getUnderling(Long posId, PagingBean pb) {
		return dao.getUnderLing(posId, pb);
	}
	                                    
}