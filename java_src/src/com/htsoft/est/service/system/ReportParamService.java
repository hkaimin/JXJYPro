package com.htsoft.est.service.system;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.BaseService;
import com.htsoft.est.model.system.ReportParam;

public interface ReportParamService extends BaseService<ReportParam>{
	/**
	 * 根据ID来查找参数
	 * @param reportId
	 * @return
	 */
	public List<ReportParam> findByRepTemp(Long reportId);
}


