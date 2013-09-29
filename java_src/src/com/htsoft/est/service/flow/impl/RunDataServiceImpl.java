package com.htsoft.est.service.flow.impl;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.est.dao.flow.RunDataDao;
import com.htsoft.est.model.flow.ProcessRun;
import com.htsoft.est.model.flow.RunData;
import com.htsoft.est.service.flow.ProcessRunService;
import com.htsoft.est.service.flow.RunDataService;

public class RunDataServiceImpl extends BaseServiceImpl<RunData> implements RunDataService{
	private RunDataDao dao;
	@Resource
	private ProcessRunService processRunService;
	
	public RunDataServiceImpl(RunDataDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个运行实例中某个字段的详细信息
	 * @param runId
	 * @param fieldName
	 * @return
	 */
	public RunData getByRunIdFieldName(Long runId,String fieldName){
		return dao.getByRunIdFieldName(runId, fieldName);
	}
	
	/**
	 * 保存流程实例对应的变量
	 * @param runId
	 * @param vars
	 */
	public void saveFlowVars(Long runId,Map<String,Object> vars){
		  ProcessRun processRun=processRunService.get(runId);
		  Iterator<Entry<String, Object>> it=vars.entrySet().iterator();
		  while(it.hasNext()){
			  Entry<String, Object> entity=it.next();
			  String fieldName=entity.getKey();
			  Object val=entity.getValue();
			  //查找是否已经存在该变量，若存在，则更新
			  RunData runData=dao.getByRunIdFieldName(runId, fieldName);
			  if(runData==null){
				  runData=new RunData();
			  }
			  runData.setFieldName(fieldName);
			  runData.setFieldLabel(fieldName);
			  runData.setProcessRun(processRun);
			  runData.setRawValue(val);
			  
			  save(runData);
		  }
	}
	
	@Override
	public Map<String, Object> getMapByRunId(Long runId) {
    		Map<String,Object> dataMap=new HashMap<String, Object>();
    		List<RunData> list=dao.getByRunId(runId);
    		for(RunData data:list){
    		    dataMap.put(data.getFieldName(), data.getRawValue());
    		}
    		return dataMap;
	}
	
}