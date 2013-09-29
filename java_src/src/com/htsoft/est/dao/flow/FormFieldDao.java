package com.htsoft.est.dao.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.est.model.flow.FormField;

/**
 * 
 * @author 
 *
 */
public interface FormFieldDao extends BaseDao<FormField>{
	/**
	 * 取某个表的标题字段
	 * @param tableId
	 * @param isFlowTitle
	 * @return
	 */
	public FormField find(Long tableId,Short isFlowTitle);
	/**
	 * 按外键表名及外键取得字段列表
	 * @param foreignTable
	 * @param foreignKey
	 * @return
	 */
	public List<FormField> getByForeignTableAndKey(String foreignTable,String foreignKey);
}