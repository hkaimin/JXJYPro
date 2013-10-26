package com.htsoft.est.service.ryxf;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.command.QueryFilter;
import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.jxjy.JxjyRyxfgl;


public interface JxjyRyxfglService extends BaseService<JxjyRyxfgl>{
	
	public List<JxjyRyxfgl> getRyxfBM(QueryFilter filter);  //人社厅学分列表
	
}


