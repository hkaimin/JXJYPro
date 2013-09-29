package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 J.Office协同办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2010 GuangZhou HongTian Software Limited Company.
*/
import java.util.List;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.util.BeanUtil;
import com.htsoft.est.dao.flow.ProHandleCompDao;
import com.htsoft.est.model.flow.ProHandleComp;
import com.htsoft.est.service.flow.ProHandleCompService;

public class ProHandleCompServiceImpl extends BaseServiceImpl<ProHandleComp> implements ProHandleCompService{
	@SuppressWarnings("unused")
	private ProHandleCompDao dao;
	
	public ProHandleCompServiceImpl(ProHandleCompDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Override
	public List<ProHandleComp> getByDeployIdActivityName(String deployId,
			String activityName) {
		return dao.getByDeployIdActivityName(deployId, activityName);
	}
	
	@Override
	public List<ProHandleComp> getByDeployIdActivityNameHandleType(
			String deployId, String activityName, Short handleType) {
		return dao.getByDeployIdActivityNameHandleType(deployId, activityName, handleType);
	}
	
	/**
	 * 
	 * @param deployId
	 * @param activityName
	 * @param eventName
	 * @return
	 */
	public ProHandleComp getProHandleComp(String deployId,String activityName,String eventName){
		return dao.getProHandleComp(deployId, activityName, eventName);
	}
	
	public List<ProHandleComp> getByDeployId(String deployId){
		return dao.getByDeployId(deployId);
	}
	
	/**
	 * 拷贝原流程的配置至新流程中
	 * @param oldDeployId
	 * @param newDeployId
	 */
	public void copyNewConfig(String oldDeployId,String newDeployId){
		List<ProHandleComp> list=getByDeployId(oldDeployId);
		for(ProHandleComp cmp:list){
			ProHandleComp temp=new ProHandleComp();
			try{
				BeanUtil.copyNotNullProperties(temp, cmp);
				temp.setHandleId(null);
				temp.setDeployId(newDeployId);
				dao.save(temp);
			}catch(Exception ex){
				
			}
			
		}
	}

}