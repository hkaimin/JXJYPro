package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.est.model.flow.FormTable;
import com.htsoft.est.model.system.AppUser;

public interface FormTableService extends BaseService<FormTable>{
	public List<FormTable> getListFromPro(String typeId,String tableName,AppUser curUser,PagingBean pb); 
	/**
	 * 返回所有表定义及其表的字段
	 * @return
	 */
	public List<FormTable> getAllAndFields();
	/**
	 * 根据key来查找表
	 * @return
	 */
	public List<FormTable> findByTableKey(String tableKey);
}


