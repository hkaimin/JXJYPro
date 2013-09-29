package com.htsoft.est.dao.task.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.task.PlanTypeDao;
import com.htsoft.est.model.task.PlanType;

public class PlanTypeDaoImpl extends BaseDaoImpl<PlanType> implements PlanTypeDao{

	public PlanTypeDaoImpl() {
		super(PlanType.class);
	}

}