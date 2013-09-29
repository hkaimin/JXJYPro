package com.htsoft.est.dao.system;

/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
 */

import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.system.RelativeJob;

/**
 * @description 相对岗位管理
 * @author YHZ
 * @company www.jee-soft.cn
 * @data 2010-12-13AM
 */
public interface RelativeJobDao extends BaseDao<RelativeJob> {

	/**
	 * @description 根据parentId查询对应的相对岗位信息
	 * @param parentId
	 *            父节点Id
	 * @return List<RelativeJob>
	 */
	List<RelativeJob> findByParentId(Long parentId);
}