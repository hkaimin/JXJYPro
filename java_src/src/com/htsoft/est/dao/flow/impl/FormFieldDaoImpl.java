package com.htsoft.est.dao.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.FormFieldDao;
import com.htsoft.est.model.flow.FormField;

@SuppressWarnings("unchecked")
public class FormFieldDaoImpl extends BaseDaoImpl<FormField> implements FormFieldDao{

	public FormFieldDaoImpl() {
		super(FormField.class);
	}
	/**
	 * 取某个表的标题字段
	 * @param tableId
	 * @param isFlowTitle
	 * @return
	 */
	public FormField find(Long tableId,Short isFlowTitle){
		String hql="from FormField ff where ff.formTable.tableId=? and ff.isFlowTitle=? ";
		return (FormField)findUnique(hql, new Object[]{
				tableId,isFlowTitle
		});
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.dao.flow.FormFieldDao#getByForeignTableAndKey(java.lang.String, java.lang.String)
	 */
	public List<FormField> getByForeignTableAndKey(String foreignTable,String foreignKey){
		String hql = "select formField from FormField formField where formField.foreignTable=? and formField.foreignKey=?";
		Object[] objs = { foreignTable,foreignKey };
		List<FormField> setList = findByHql(hql, objs);
		return setList;
	}

}