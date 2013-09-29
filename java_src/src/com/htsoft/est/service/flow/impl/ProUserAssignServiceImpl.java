package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.flow.ProUserAssignDao;
import com.htsoft.est.model.flow.ProUserAssign;
import com.htsoft.est.service.flow.ProUserAssignService;

public class ProUserAssignServiceImpl extends BaseServiceImpl<ProUserAssign> implements ProUserAssignService{
	private ProUserAssignDao dao;
	
	public ProUserAssignServiceImpl(ProUserAssignDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public List<ProUserAssign> getByDeployId(String deployId){
		return dao.getByDeployId(deployId);
	}
	/*
	 * (non-Javadoc)
	 * @see com.htsoft.est.service.flow.ProUserAssignService#getByDeployIdActivityName(java.lang.String, java.lang.String)
	 */
	public ProUserAssign getByDeployIdActivityName(String deployId,String activityName){
		return dao.getByDeployIdActivityName(deployId, activityName);
	}
	/**
	 * 把旧版本的流程的人员配置复制至新版本上去
	 * @param oldDeplyId
	 * @param newDeployId
	 */
	public void copyNewConfig(String oldDeployId,String newDeployId){
		List<ProUserAssign> list=getByDeployId(oldDeployId);
		for(ProUserAssign assign:list){
			try{
				ProUserAssign temp=new ProUserAssign();
				BeanUtil.copyNotNullProperties(temp, assign);
				temp.setAssignId(null);
				temp.setDeployId(newDeployId);
				dao.save(temp);
			}catch(Exception ex){
				
			}
		}
	}

}