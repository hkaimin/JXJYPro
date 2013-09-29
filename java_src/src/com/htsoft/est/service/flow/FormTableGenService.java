package com.htsoft.est.service.flow;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.flow.FormTable;

public interface FormTableGenService extends BaseService<FormTable>{
	public boolean genBean(FormTable[] formTables);
}


